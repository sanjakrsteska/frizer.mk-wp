package mk.frizer.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.frizer.model.*;
import mk.frizer.model.dto.AppointmentAddDTO;
import mk.frizer.model.exceptions.AppointmentNotFoundException;
import mk.frizer.model.exceptions.TreatmentNotFoundException;
import mk.frizer.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    private final TreatmentService treatmentService;
    private final AppointmentService appointmentService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final BusinessOwnerService businessOwnerService;

    public AppointmentController(TreatmentService treatmentService, AppointmentService appointmentService, CustomerService customerService, EmployeeService employeeService, BusinessOwnerService businessOwnerService) {
        this.treatmentService = treatmentService;
        this.appointmentService = appointmentService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.businessOwnerService = businessOwnerService;
    }

    @GetMapping("")
    public String getAppointments(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser user = (BaseUser) userDetails;

        Optional<Employee> employee = employeeService
                .getEmployeeByBaseUserId(user.getId());

        Optional<Customer> customer = customerService
                .getCustomerByBaseUserId(user.getId());

        employee.ifPresent(emp -> {
            model.addAttribute("employee", employee.get());
        });

        customer.ifPresent(cus -> {
            model.addAttribute("customer", customer.get());
        });

        model.addAttribute("bodyContent", "appointments");

        return "master-template";
    }

    @PostMapping("/create")
    public String createAppointment(@RequestParam Long salon,
                                    @RequestParam Long treatment,
                                    @RequestParam Long employee,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime time,
                                    RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Treatment chosenTreatment = null;
        try {
            Optional<Treatment> optionalOfTreatment = treatmentService.getTreatmentById(treatment);
            chosenTreatment = optionalOfTreatment.get();
        } catch (TreatmentNotFoundException e) {
            return "redirect:/app-error?message=" + "Treatment not found";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("message", "You need to be logged in to create an appointment.");
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Customer loggedInCustomer = customerService.getCustomerByEmail(email)
                .orElse(null);
        Long customerId = null;

        if (loggedInCustomer != null) {
            customerId = loggedInCustomer.getId();
        }

        LocalDateTime dateTo = time.plusMinutes(20L * chosenTreatment.getDurationMultiplier());
        AppointmentAddDTO appointmentAddDTO =
                new AppointmentAddDTO(time, dateTo, treatment, salon, employee, customerId);

        appointmentService.createAppointment(appointmentAddDTO);
        return "redirect:/home";
    }

    @PostMapping("/mark-as-done/{id}")
    public String markAppointmentAsDone(@PathVariable Long id) {
        Appointment appointment = appointmentService
                .getAppointmentById(id).orElseThrow(AppointmentNotFoundException::new);
        employeeService.addHistoryAppointmentForEmployee(appointment);
        customerService.addHistoryAppointmentForCustomer(appointment);
        return "redirect:/appointments";
    }

    @PostMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointmentById(id);
        return "redirect:/appointments";
    }

}
