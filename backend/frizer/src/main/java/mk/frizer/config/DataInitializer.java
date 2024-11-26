package mk.frizer.config;

import jakarta.annotation.PostConstruct;

import mk.frizer.model.*;
import mk.frizer.model.dto.*;
import mk.frizer.repository.CityRepository;
import mk.frizer.service.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component

public class DataInitializer {
    private final BaseUserService baseUserService;
    private final BusinessOwnerService businessOwnerService;
    private final SalonService salonService;
    private final TreatmentService treatmentService;
    private final TagService tagService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final ReviewService reviewService;
    private final CityRepository cityRepository;

    public DataInitializer(BaseUserService baseUserService, BusinessOwnerService businessOwnerService, SalonService salonService, TreatmentService treatmentService, TagService tagService, EmployeeService employeeService, CustomerService customerService, ReviewService reviewService, CityRepository cityRepository){
        this.baseUserService = baseUserService;
        this.businessOwnerService = businessOwnerService;
        this.salonService = salonService;
        this.treatmentService = treatmentService;
        this.tagService = tagService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.reviewService = reviewService;
        this.cityRepository = cityRepository;
    }

    @PostConstruct
    public void init(){
        List<String> all_cities = Arrays.asList(
                "Цела Македонија", "Берово", "Битола", "Богданци", "Валандово", "Велес", "Виница", "Гевгелија",
                "Гостивар", "Дебар", "Делчево", "Демир Капија", "Демир Хисар", "Кавадарци",
                "Кичево", "Кочани", "Кратово", "Крива Паланка", "Крушево", "Куманово",
                "Македонски Брод", "Македонска Каменица", "Неготино", "Охрид", "Пехчево",
                "Прилеп", "Пробиштип", "Радовиш", "Ресен", "Свети Николе", "Скопје",
                "Струга", "Струмица", "Тетово", "Штип");

        if(cityRepository.findAll().isEmpty()) {
            for (String city : all_cities) {
                cityRepository.save(new City(city));
            }
        }


        boolean init = false;
        if(init){
            baseUserService.createBaseUser(new BaseUserAddDTO("dario@email.com", "password", "Дарио","Мартиновски","072333222"));
            baseUserService.createBaseUser(new BaseUserAddDTO("sanja@email.com", "password", "Сања","Крстеска","077334433"));
            baseUserService.createBaseUser(new BaseUserAddDTO("denis@email.com", "password", "Денис","Ибраими","079933222"));
            baseUserService.createBaseUser(new BaseUserAddDTO("maksim@email.com", "password", "Максим","Горки","073344422"));
            baseUserService.createBaseUser(new BaseUserAddDTO("petar@email.com", "password", "Петар","Цветанов","071111222"));
            baseUserService.createBaseUser(new BaseUserAddDTO("toni@email.com", "password", "Тони","Тарабанов","078999888"));
            BaseUser baseUser1 = baseUserService.getBaseUsers().get(0);
            BaseUser baseUser2 = baseUserService.getBaseUsers().get(1);
            BaseUser baseUser3 = baseUserService.getBaseUsers().get(2);
            BaseUser baseUser4 = baseUserService.getBaseUsers().get(3);
            BaseUser baseUser5 = baseUserService.getBaseUsers().get(4);
            BaseUser baseUser6 = baseUserService.getBaseUsers().get(5);


            List<String> firstNames = Arrays.asList(
                    "Александар", "Бојан", "Виктор", "Горан", "Даниел",
                    "Елена", "Жарко", "Зоран", "Иван", "Јасмина",
                    "Кристина", "Леон", "Марија", "Наташа", "Огнен",
                    "Петар", "Ристо", "Стефан", "Тамара", "Христина"
            );

            // Macedonian last names
            List<String> lastNames = Arrays.asList(
                    "Јовановски", "Петров", "Стојанов", "Ристов", "Тодоров",
                    "Милевски", "Атанасов", "Георгиев", "Димов", "Костовски",
                    "Младенов", "Николов", "Павлов", "Симеонов", "Филипов",
                    "Глигоров", "Шалев", "Велковски", "Кузманов", "Савевски"
            );

            for (int i = 0; i < 20; i++) {
                String email = "baseuser" + (i + 1) + "@frizer.mk";
                String firstName = firstNames.get(i);
                String lastName = lastNames.get(i);
                String phoneNumber = "07" + String.format("%02d", i + 1) + "123456";
                baseUserService.createBaseUser(new BaseUserAddDTO(email, "password", firstName, lastName, phoneNumber));
            }

            List<BaseUser> createdBaseUsers = baseUserService.getBaseUsers().stream().skip(6).toList();

            businessOwnerService.createBusinessOwner(baseUser1.getId());
            businessOwnerService.createBusinessOwner(baseUser2.getId());
            businessOwnerService.createBusinessOwner(baseUser3.getId());
            BusinessOwner businessOwner1 = businessOwnerService.getBusinessOwners().get(0);
            BusinessOwner businessOwner2 = businessOwnerService.getBusinessOwners().get(1);
            BusinessOwner businessOwner3 = businessOwnerService.getBusinessOwners().get(2);

//            salonService.createSalon(new SalonAddDTO("Krc krc","Berber","doma", "Скопје", "broj", businessOwner1.getId(), (float) 4.2, (float) 42.0037876, (float) 21.9278854));
//            salonService.createSalon(new SalonAddDTO("Nenko","Berber","kaj komsiite","Скопје", "broj1", businessOwner1.getId(), (float) 3.8,(float) 42.0586418, (float) 21.3176565));
//            salonService.createSalon(new SalonAddDTO("Kaj Shekspiro","Frizerski salon za mazhi","Прилеп","Прилеп","broj2", businessOwner2.getId(), (float) 4.7,(float) 41.4360468, (float) 22.0048696));
//            salonService.createSalon(new SalonAddDTO("Frizerski salon Asim","Frizerski salon za mazhi","Велес","Велес", "broj3", businessOwner1.getId(), (float) 4.2,(float) 41.4676689, (float) 22.0844239));
            List<SalonAddDTO> salons = Arrays.asList(
                    new SalonAddDTO("Фризерски салон Александра", "Фризерски салон", "Улица 1", "Скопје", "070123456", businessOwner1.getId(), 41.9981f, 21.4254f),
                    new SalonAddDTO("Салон за убавина Ана", "Козметички салон", "Улица 2", "Битола", "070654321", businessOwner1.getId(), 41.0300f, 21.3400f),
                    new SalonAddDTO("Фризерница Катерина", "Фризерски салон", "Улица 3", "Прилеп", "071234567", businessOwner2.getId(), 41.3464f, 21.5542f),
                    new SalonAddDTO("Бјути Центар Вера", "Козметички салон", "Улица 4", "Струмица", "071987654", businessOwner2.getId(), 41.4378f, 22.6431f),
                    new SalonAddDTO("Салон Роза", "Фризерски салон", "Улица 5", "Куманово", "072345678", businessOwner3.getId(), 42.1350f, 21.7147f),
                    new SalonAddDTO("Спа Центар Елена", "Спа центар", "Улица 6", "Охрид", "072876543", businessOwner3.getId(), 41.1167f, 20.8017f),
                    new SalonAddDTO("Фризерски салон Мими", "Фризерски салон", "Улица 7", "Велес", "073123456", businessOwner1.getId(), 41.7156f, 21.7722f),
                    new SalonAddDTO("Салон за убавина Софија", "Козметички салон", "Улица 8", "Кавадарци", "073654321", businessOwner1.getId(), 41.4333f, 22.0111f),
                    new SalonAddDTO("Фризерница Сузана", "Фризерски салон", "Улица 9", "Штип", "074234567", businessOwner2.getId(), 41.7450f, 22.1994f),
                    new SalonAddDTO("Салон за убавина Јована", "Козметички салон", "Улица 10", "Тетово", "074987654", businessOwner2.getId(), 42.0106f, 20.9716f),
                    new SalonAddDTO("Фризерски салон Тамара", "Фризерски салон", "Улица 11", "Гостивар", "075123456", businessOwner3.getId(), 41.7970f, 20.9083f),
                    new SalonAddDTO("Спа Центар Нина", "Спа центар", "Улица 12", "Дебар", "075876543", businessOwner3.getId(), 41.5242f, 20.5242f),
                    new SalonAddDTO("Фризерница Марија", "Фризерски салон", "Улица 13", "Гевгелија", "076123456", businessOwner1.getId(), 41.1392f, 22.5022f),
                    new SalonAddDTO("Салон за убавина Драгана", "Козметички салон", "Улица 14", "Кочани", "076654321", businessOwner1.getId(), 41.9167f, 22.4167f),
                    new SalonAddDTO("Фризерски салон Снежана", "Фризерски салон", "Улица 15", "Радовиш", "077234567", businessOwner2.getId(), 41.6389f, 22.4642f),
                    new SalonAddDTO("Салон за убавина Ивана", "Козметички салон", "Улица 16", "Ресен", "077987654", businessOwner2.getId(),  41.0897f, 21.0125f),
                    new SalonAddDTO("Фризерски салон Драган", "Фризерски салон", "Улица 17", "Кичево", "078123456", businessOwner3.getId(), 41.5122f, 20.9586f),
                    new SalonAddDTO("Спа Центар Бојана", "Спа центар", "Улица 18", "Крушево", "078876543", businessOwner3.getId(), 41.3683f, 21.2483f),
                    new SalonAddDTO("Фризерница Борис", "Фризерски салон", "Улица 19", "Виница", "079123456", businessOwner1.getId(),  41.8825f, 22.5097f),
                    new SalonAddDTO("Салон за убавина Гордана", "Козметички салон", "Улица 20", "Делчево", "079654321", businessOwner1.getId(), 41.9675f, 22.7747f)
            );

            salons.forEach(salonService::createSalon);

            List<Salon> createdSalons = salonService.getSalons();

            // Create Employees and assign them to salons
            for(int i = 3; i < createdBaseUsers.size(); i++) {
                int salonIndex = i % createdSalons.size(); // Distribute employees across salons
                employeeService.createEmployee(new EmployeeAddDTO(createdBaseUsers.get(i).getId(), createdSalons.get(salonIndex).getId()));
            }

            Salon salon1 = salonService.getSalons().get(1);
            Salon salon2 = salonService.getSalons().get(2);

            treatmentService.createTreatment(new TreatmentAddDTO("Миење коса", salon1.getId(), 50.0, 1));
            treatmentService.createTreatment(new TreatmentAddDTO("Педикир", salon2.getId(), 500.0, 2));

            Treatment treatment = treatmentService.getTreatments().get(1);

            tagService.createTag("Миење");
            tagService.createTag("Шишање");

            Tag tag1 = tagService.getTags().get(0);
            Tag tag2 = tagService.getTags().get(1);

            salonService.addTagToSalon(new TagAddDTO(tag1.getId(), salon1.getId()));
            salonService.addTagToSalon(new TagAddDTO(tag2.getId(), salon1.getId()));
            salonService.addTagToSalon(new TagAddDTO(tag2.getId(), salon2.getId()));

            employeeService.createEmployee(new EmployeeAddDTO(baseUser4.getId(), salon2.getId()));
            employeeService.createEmployee(new EmployeeAddDTO(baseUser5.getId(), salon2.getId()));

            Employee employee1 = employeeService.getEmployees().get(0);
            Employee employee2 = employeeService.getEmployees().get(1);

            Customer customer = customerService.getCustomers().get(0);

            reviewService.createReviewForCustomer(new ReviewAddDTO(employee1.getId(), customer.getId(), 4.4, "Многу добар муштерија."));
            reviewService.createReviewForCustomer(new ReviewAddDTO(employee2.getId(), customer.getId(), 5.0, "Најдобар муштерија."));
            reviewService.createReviewForEmployee(new ReviewAddDTO(employee1.getId(), customer.getId(), 4.9, "Многу добро прави фризури."));

//            LocalDateTime dateFrom = LocalDateTime.of(2024, 6,20,11,0,0);
//            LocalDateTime dateTo = LocalDateTime.of(2024, 6,20,11,20,0);
//
//            appointmentService.createAppointment(new AppointmentAddDTO(dateFrom, dateTo, treatment.getId(), salon2.getId(), employee2.getId(), customer.getId()));
        }


    }
}
