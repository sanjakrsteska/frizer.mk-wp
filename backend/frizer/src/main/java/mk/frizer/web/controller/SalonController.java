package mk.frizer.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.frizer.model.*;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.exceptions.EmployeeNotFoundException;
import mk.frizer.model.exceptions.SalonNotFoundException;
import mk.frizer.model.exceptions.TreatmentNotFoundException;
import mk.frizer.service.*;
import mk.frizer.service.impl.CityServiceImpl;
import mk.frizer.utilities.TimeSlotGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;

@Controller
@RequestMapping("/salons")
public class SalonController {
    private final SalonService salonService;
    private final ReviewService reviewService;
    private final CustomerService customerService;
    private final BaseUserService baseUserService;
    private final BusinessOwnerService businessOwnerService;
    private final CityService cityService;
    private final EmployeeService employeeService;
    private final TreatmentService treatmentService;
    private final TimeSlotGenerator timeSlotGenerator;

    public SalonController(SalonService salonService, ReviewService reviewService, CustomerService customerService, BaseUserService baseUserService, BusinessOwnerService businessOwnerService, CityService cityService, EmployeeService employeeService, TreatmentService treatmentService, TimeSlotGenerator timeSlotGenerator) {
        this.salonService = salonService;
        this.reviewService = reviewService;
        this.customerService = customerService;
        this.baseUserService = baseUserService;
        this.businessOwnerService = businessOwnerService;
        this.cityService = cityService;
        this.employeeService = employeeService;
        this.treatmentService = treatmentService;
        this.timeSlotGenerator = timeSlotGenerator;
    }

    @GetMapping("/{id}")
    public String salonDetailsPage(@PathVariable Long id,
                                   @RequestParam(required = false) String error,
                                   Model model, Principal principal) {
        Salon salon = salonService.getSalonById(id).orElseThrow(SalonNotFoundException::new);
        List<Tag> tags = salon.getTags();
        List<Treatment> treatments = salon.getSalonTreatments();
        List<Employee> employees = salon.getEmployees();
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);

        ReviewStats salonStats = employeeMap.values().stream()
                .collect(Collector.of(
                        () -> new double[3],
                        (a, rs) -> {
                            a[0] += rs.getRating();
                            a[1] += rs.getNumberOfReviews();
                            a[2]++;
                        },
                        (a, b) -> { // combiner
                            a[0] += b[0];
                            a[1] += b[1];
                            a[2] += b[2];
                            return a;
                        },
                        a -> new ReviewStats(a[0] / (a[2] == 0 ? 1 : a[2]), (int) a[1]) // finisher
                ));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isBusinessOwnerOrEmployee = false;
        boolean isBusinessOwner = false;

        if (authentication != null && authentication.isAuthenticated() && principal != null) {
            isBusinessOwnerOrEmployee  = salonService.isUserAuthorizedToAddTreatment(id, principal.getName());
            isBusinessOwner = salonService.isUserAuthorizedToAddSalon(id, principal.getName());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            model.addAttribute("employees", employees.stream()
                    .filter(e -> !e.getBaseUser().getEmail().equals(userDetails.getUsername())).toList());
        }
        else{
            model.addAttribute("employees", employees);
        }

        model.addAttribute("isBusinessOwnerOrEmployee", isBusinessOwnerOrEmployee);
        model.addAttribute("isBusinessOwner", isBusinessOwner);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("treatments", treatments);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salon", salon);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("reviews", reviews);
        model.addAttribute("tags", tags);
        model.addAttribute("salonAsString", salonService.getSalonAsString(salon));
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("error", error);

        model.addAttribute("baseUsers", baseUserService.getBaseUsers().stream()
                .filter(e -> !salon.getOwner().getBaseUser().equals(e) &&
                        salon.getEmployees().stream().map(Employee::getBaseUser).noneMatch(emp -> emp.equals(e))));

        model.addAttribute("bodyContent", "salon");
        return "master-template";
    }

    private List<Salon> setSearchAttributes(String name, Float rating, Float distance, String city, String sortingMethod, Model model, HttpSession session) {
        model.addAttribute("searchName", name);
        model.addAttribute("searchRating", rating);
        model.addAttribute("searchDistance", distance);
        model.addAttribute("searchCity", city);

        if (name == null) {
            name = "";
        }
        if (rating == null || rating < 0) {
            rating = (float) 0.0;
        }
        if (distance == null || distance < 0) {
            distance = (float) 300;
        }
        if (city == null || city.isEmpty()) {
            city = "Цела Македонија";
        }

        // Saved in another controller on the start
        String userLocation = (String) session.getAttribute("userGeolocation");
        List<Salon> filteredSalons = salonService.filterSalons(name, city, distance, rating, userLocation);

        model.addAttribute("salons", filteredSalons);
        model.addAttribute("count", filteredSalons.size());
        return filteredSalons;
    }

    @GetMapping()
    public String getSalons(@RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "rating", required = false) Float rating,
                            @RequestParam(name = "distance", required = false) Float distance,
                            @RequestParam(name = "city", required = false) String city,
                            @RequestParam(name = "sort", required = false) String sortingMethod,
                            Model model, HttpSession session) {
        setSearchAttributes(name, rating, distance, city, sortingMethod, model, session);
        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("salonRatings", reviewService.getStatisticsForSalon(salonService.getSalons()));
        model.addAttribute("bodyContent", "salons-list");
        return "master-template";
    }


    @GetMapping("/map")
    public String showSalonsMap(@RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "rating", required = false) Float rating,
                                @RequestParam(name = "distance", required = false) Float distance,
                                @RequestParam(name = "city", required = false) String city,
                                @RequestParam(name = "sort", required = false) String sortingMethod,
                                Model model, HttpSession session){
        List<Salon> filteredSalons = setSearchAttributes(name, rating, distance, city, sortingMethod, model, session);

        List<String> salons = salonService.getSalonsAsString(filteredSalons);
        model.addAttribute("salonsAsString", salons);

        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("bodyContent", "salons-map");
        return "master-template";
    }

    @PostMapping("/create")
    public String createSalon(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam String location,
                              @RequestParam String city,
                              @RequestParam String phoneNumber,
                              @RequestParam(required = false) Long businessOwner,
                              @RequestParam(required = false) Long baseUserId,
                              @RequestParam Float latitude,
                              @RequestParam Float longitude){

        if (businessOwner == null && baseUserId != null){
            BusinessOwner owner = businessOwnerService.createBusinessOwner(baseUserId)
                    .orElse(null);
            if (owner != null) {
                businessOwner = owner.getId();
            }
        }
        salonService.createSalon(new SalonAddDTO(name, description, location, city, phoneNumber, businessOwner, latitude, longitude));
        return "redirect:/profile";
    }

    @GetMapping("/appointment")
    public String getAppointments(@RequestParam Long salon, Model model, @RequestParam Long treatment) {
        Salon chosenSalon = null;
        Treatment chosenTreatment = null;
        try {
            Optional<Salon> optionalOfSalon = salonService.getSalonById(salon);
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            chosenSalon = optionalOfSalon.get();
            chosenTreatment = optionalOfTreatment.get();
        } catch (SalonNotFoundException e) {
            return "redirect:/app-error?message=" + "Salon not found";
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        }
        List<Employee> employees = employeeService.getEmployeesForSalon(salon);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(chosenSalon);
        Map<Long, ReviewStats> employeeMap = reviewService.getStatisticsForEmployee(employees);

        model.addAttribute("treatment", chosenTreatment);
        model.addAttribute("employeeMap", employeeMap);
        model.addAttribute("salon", chosenSalon);
        model.addAttribute("employees", employees);
        model.addAttribute("reviews", reviews);
        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("bodyContent", "appointment-employees");
        return "master-template";
    }

    @GetMapping("/appointment/reserve")
    public String getAvailableAppointments(@RequestParam Long salon,
                                           @RequestParam Long treatment,
                                           @RequestParam Long employee, Model model) {
        Salon chosenSalon = null;
        Treatment chosenTreatment = null;
        Employee chosenEmployee = null;
        try {
            Optional<Salon> optionalOfSalon = salonService.getSalonById(salon);
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            Optional<Employee> employeeOptional = employeeService.getEmployeeById(employee);
            chosenSalon = optionalOfSalon.get();
            chosenTreatment = optionalOfTreatment.get();
            chosenEmployee =  employeeOptional.get();
        } catch (SalonNotFoundException e) {
            return "redirect:/app-error?message=" + "Salon not found";
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        } catch (EmployeeNotFoundException e) {
            return "redirect:/app-error?message=" + "Employee not found";
        }
        List<Employee> employees = employeeService.getEmployeesForSalon(salon);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(chosenSalon);

        List<List<AppointmentTimeSlot>> availableTimeSlots = timeSlotGenerator.generateAvailableTimeSlots(salon, employee, chosenTreatment.getDurationMultiplier());

        List<String> days = new ArrayList<>();
        availableTimeSlots.forEach(list -> {
            if (!list.isEmpty()){
                days.add(list.get(0).getFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            else{
                days.add(null);
            }
        });

        ReviewStats employeeStats = reviewService.getStatisticsForEmployee(chosenEmployee);

        model.addAttribute("salon", chosenSalon);
        model.addAttribute("employee", chosenEmployee);
        model.addAttribute("treatment", chosenTreatment);


        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("employeeStats", employeeStats);
        model.addAttribute("tracker", List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        model.addAttribute("availableTimeSlots", availableTimeSlots);
        model.addAttribute("days", days);
        model.addAttribute("bodyContent", "appointment-choose-app");

        return "master-template";
    }

    @GetMapping("/appointment/confirm")
    public String confirmAppointment(@RequestParam Long salon,
                                     @RequestParam Long treatment,
                                     @RequestParam Long employee,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime time,
                                     Model model) {
        Salon chosenSalon = null;
        Treatment chosenTreatment = null;
        Employee chosenEmployee = null;
        try {
            Optional<Salon> optionalOfSalon = salonService.getSalonById(salon);
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            Optional<Employee> employeeOptional = employeeService.getEmployeeById(employee);
            chosenSalon = optionalOfSalon.get();
            chosenTreatment = optionalOfTreatment.get();
            chosenEmployee =  employeeOptional.get();
        } catch (SalonNotFoundException e) {
            return "redirect:/app-error?message=" + "Salon not found";
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        } catch (EmployeeNotFoundException e) {
            return "redirect:/app-error?message=" + "Employee not found";
        }

        List<Employee> employees = employeeService.getEmployeesForSalon(salon);
        List<Review> reviews = reviewService.getReviewsForEmployees(employees);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ReviewStats salonStats = reviewService.getStatisticsForSalon(chosenSalon);
        ReviewStats employeeStats = reviewService.getStatisticsForEmployee(chosenEmployee);

        model.addAttribute("salon", chosenSalon);
        model.addAttribute("treatment", chosenTreatment);
        model.addAttribute("employee", chosenEmployee);
        model.addAttribute("startAppointmentTime", time);
        model.addAttribute("endAppointmentTime", time.plusMinutes(20L * chosenTreatment.getDurationMultiplier()));

        model.addAttribute("formatter", formatter);
        model.addAttribute("salonStats", salonStats);
        model.addAttribute("employeeStats", employeeStats);

        model.addAttribute("bodyContent", "appointment-confirm");
        return "master-template";
    }


    @PostMapping("/{id}/image/add")
    public String addSalonImage(@PathVariable Long id,
                                @RequestParam("image") MultipartFile image,
                                @RequestParam Integer imageNo) throws IOException{
        if (!image.isEmpty()) {
            salonService.saveImageWithId(id, imageNo, image);
        }
        return "redirect:/salons/" + id.toString();
    }

    @PostMapping("/delete/{id}")
    public String removeSalon(@PathVariable Long id){
        salonService.deleteSalonById(id);
        return "redirect:/profile";
    }
}