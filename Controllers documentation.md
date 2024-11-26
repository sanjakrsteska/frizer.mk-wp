# "Frizer.mk"

#### Web application by: Dario Martinovski and Sanja Krsteska
---

## 1. Project description
Introducing "Frizer.mk",  your one-stop destination for all your hair and beauty needs. Whether you're looking for a fresh haircut, a rejuvenating facial, or a glamorous makeover, "Frizer.mk" connects you with the best salons and professionals in your area, ensuring you look and feel your best every time.

With "Frizer.mk", booking your next appointment has never been easier. Simply browse through a curated list of top-rated salons, and choose the one that suits your preferences. Want a specific stylist or beauty therapist? No problem! "Frizer.mk" allows you to select your preferred professional, ensuring you receive personalized service tailored to your unique style and preferences.

Say goodbye to long phone calls and endless wait times. With "Frizer.mk", you can book your appointment with just a few steps. Choose your preferred date, time, and service, and let "Frizer.mk" handle the rest. Plus, you'll receive instant confirmation, so you can plan your day with confidence.

### 2. Controllers 
The web application consists of the following controllers:

  #### 2.1 Appointment Controller
  The Appointment Controller is mapped on 2 URLs: 
  "/api/appointments" and  "/api/appointment".
  This controller deals with appointments for beauty services like haircuts or facials. It helps users make, view, or delete appointments. The controller uses the appointment service where the whole bussiness logic is implemented.

  It contains getAllAppointments() method that takes no parameters and returns list of all appointments.

  getAppointmentById method takes path variable id as a parameter and returns the appointment wrapped as Response Entity, if the appointment with that specific id is not found, an exception is thrown in the method and it returns HTTP status Not found as a response. 

  createAppointment method takes AppointmentDTO as a Request body parameter. This object is used for transmitting appointment data from forms in order to hide unnecessary details and for security.
  As a response it gives Response Entity ok if the appointment i created or bad request in the other case.

   deleteAppointmentById method takes the Path variable id as a parameter and returns Response Entity ok if the appointment is successfully deleted or bad request otherwise with exception AppointmentNotFoundException.

   changeAttendanceForAppointment takes path variable id as a parameter and returns Reesponse Entity ok if the appointment is found and the status of attendance is changed and Response Entity bad request in the other case.
   
  ```java
    @RestController
    @RequestMapping({ "/api/appointments", "/api/appointment" })
    @CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
    public class AppointmentRestController {
        private final AppointmentService appointmentService;

        public AppointmentRestController(AppointmentService appointmentService) {
            this.appointmentService = appointmentService;
        }

        @GetMapping()
        public List<Appointment> getAllAppointments() {
            return appointmentService.getAppointments();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
            return this.appointmentService.getAppointmentById(id)
                    .map(owner -> ResponseEntity.ok().body(owner))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping("/add")
        public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentAddDTO appointmentAddDTO) {
            return this.appointmentService.createAppointment(appointmentAddDTO)
                    .map(appointment -> ResponseEntity.ok().body(appointment))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Appointment> deleteAppointmentById(@PathVariable Long id) {
            Optional<Appointment> appointment = this.appointmentService.deleteAppointmentById(id);
            try {
                this.appointmentService.getAppointmentById(id);
                return ResponseEntity.badRequest().build();
            } catch (AppointmentNotFoundException exception) {
                return ResponseEntity.ok().body(appointment.get());
            }
        }

        @PostMapping("/attended/{id}")
        public ResponseEntity<Appointment> changeAttendanceForAppointment(@PathVariable Long id) {
            return this.appointmentService.changeUserAttendanceAppointment(id)
                    .map(appointment -> ResponseEntity.ok().body(appointment))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }

    }

  ```
  
  
  #### 2.2 Review Controller
  The Review Controller is mapped on 2 URLs: 
  "/api/reviews" and  "/api/review".
  This controller is responsible for managing reviews. It is used for creating, viewing, changing, or deleting reviews. The controller uses the review service.

  It contains getAllReviews() method that takes no parameters and returns list of all reviews.

  getReviewById method takes path variable id as a parameter and returns the review wrapped as Response Entity, if the review with that specific id is not found, an exception is thrown in the method and it returns HTTP status Not found as a response. 

  createReviewForEmployee method uses Request Body ReviewAddDTO as a parameter and creates a review for a concrete employee.

  As a response it gives Response Entity ok if the review is created or bad request in the other case.
  Similarly the method createReviewForCustomer creates review for a customer.

   deleteReviewById method takes the Path Variable id as a parameter and returns Response Entity ok if the review is successfully deleted or bad request otherwise with the exception ReviewNotFoundException.

   updateReview method is used for updating specific review, as a parameter it takes the Path variable id of the concrete review and Request body ReviewUpdateDTO. If the review is found and updated this method returns Request Entity ok and if not it returns status bad request.
   
   ```java
@RestController
@RequestMapping({ "/api/reviews", "/api/review" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class ReviewRestController {
    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping()
    public List<Review> getAllReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return this.reviewService.getReviewById(id)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add-for-employee")
    public ResponseEntity<Review> createReviewForEmployee(@RequestBody ReviewAddDTO reviewAddDto) {
        System.out.println(reviewAddDto);
        return this.reviewService.createReviewForEmployee(reviewAddDto)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/add-for-customer")
    public ResponseEntity<Review> createReviewForCustomer(@RequestBody ReviewAddDTO reviewAddDto) {
        System.out.println(reviewAddDto);
        return this.reviewService.createReviewForCustomer(reviewAddDto)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateDTO reviewUpdateDTO) {
        return this.reviewService.updateReview(id, reviewUpdateDTO)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Review> deleteReviewById(@PathVariable Long id) {
        Optional<Review> review = this.reviewService.deleteReviewById(id);
        try {
            this.reviewService.getReviewById(id);
            return ResponseEntity.badRequest().build();
        } catch (ReviewNotFoundException exception) {
            return ResponseEntity.ok().body(review.get());
        }
    }
}

   ```
  
  #### 2.3 Salon Controller
  The Salon Controller is mapped on: "/api/salons" and  "/api/salon".
  This controller deals with salons for beauty services like haircuts or facials. It helps users make, view, or delete salons. The controller uses the salon service where the whole bussiness logic is implemented.

  It contains getAllSalons() method that takes no parameters and returns list of all salons.

  getSalonById method takes path variable id as a parameter and returns the salon wrapped as Response Entity, if the salon with that specific id is not found, an exception is thrown in the method and it returns http status Not found as a response. 

  createSalon method takes SalonDTO as a Request body parameter. SalonDTO object is used for successfully transmitting salon data.
  As a response it gives Response Entity ok if the salon is created or bad request in the other case.

   deleteSalonById method takes the Path variable id as a parameter and returns Response Entity ok if the salon is successfully deleted or if not SalonNotFoundException exception is caught and the method returns HTTP status bad request.
   uploadImage method takes parameters the Path variable salon id and Request Param image  as a MulitpartFile. After successfull upload it returns the salon. If the request is not succesful it returns Bad Request as HTTP status code.  

  ```java
@RestController
@RequestMapping({ "/api/salons", "/api/salon" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class SalonRestController {
    private final SalonService salonService;
    private static final String UPLOAD_DIR = "src/main/resources/static/salons/";

    public SalonRestController(SalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping()
    public List<Salon> getAllSalons() {
        return salonService.getSalons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salon> getSalonById(@PathVariable Long id) {
        return this.salonService.getSalonById(id)
                .map(salon -> ResponseEntity.ok().body(salon))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Salon> createSalon(@RequestBody SalonAddDTO salonAddDTO) {
        return this.salonService.createSalon(salonAddDTO)
                .map(salon -> ResponseEntity.ok().body(salon))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Salon> updateSalon(@PathVariable Long id, @RequestBody SalonUpdateDTO salonUpdateDTO) {
        return this.salonService.updateSalon(id, salonUpdateDTO)
                .map(salon -> ResponseEntity.ok().body(salon))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Salon> deleteSalonById(@PathVariable Long id) {
        Optional<Salon> salon = this.salonService.deleteSalonById(id);
        try {
            this.salonService.getSalonById(id);
            return ResponseEntity.badRequest().build();
        } catch (SalonNotFoundException exception) {
            return ResponseEntity.ok().body(salon.get());
        }
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<Salon> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image)  {
        try{
            Optional<Salon> salon = salonService.saveImage(id, image);
            return ResponseEntity.ok().body(salon.get());
        }
        catch (IOException exception){
            return ResponseEntity.badRequest().build();
        }
    }
}
  ```

  #### 2.4 Tag Controller
  The Tag Controller provides endpoints for managing tags in the system, including retrieving all tags, getting specific tags by ID, creating new tags, and deleting tags. It acts as the interface for interacting with tag-related operations within the application. It is mapped on  "/api/tags" and  "/api/tag".
  Methods that are implemented in this controller:
  getTags it does not take any parameters and it returns the list of all tags.
  getTagById method takes path variable id as a parameter and returns the tag wrapped as Response Entity, if the tag with that specific id is not found, an exception is thrown in the method and it returns http status Not found as a response. 

  createTag method takes String name of the tag as a Request param. As a response it gives Response Entity ok if the Tag is created or bad request in the other case.

  deleteTagById method takes the Path variable id as a parameter and returns Response Entity ok if the Tag is successfully deleted or if not TagNotFoundException exception is caught and the method returns HTTP status bad request.

  ```java
@RestController
@RequestMapping({ "/api/tags", "/api/tag" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class TagRestController {
    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public List<Tag> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        return this.tagService.getTagById(id)
                .map(tag -> ResponseEntity.ok().body(tag))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Tag> createTag(@RequestParam String name) {
        return this.tagService.createTag(name)
                .map(tag -> ResponseEntity.ok().body(tag))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Tag> deleteTagById(@PathVariable Long id) {
        Optional<Tag> tag = this.tagService.deleteTagById(id);
        try {
            this.tagService.getTagById(id);
            return ResponseEntity.badRequest().build();
        } catch (TagNotFoundException exception) {
            return ResponseEntity.ok().body(tag.get());
        }
    }
}
  ```

  #### 2.5 Treatment Controller
  Treatment Controller handles everything related to treatments in the app, from showing available options to making changes and updates as needed. 
  It is mapped to handle requests starting with either "/api/treatments" or "/api/treatment".

  getTreatments method is a method with no parameters and returns the list of all treatments in the database.
  getTreatmentById takes Path variable id as a parameter and returns the treatment wrapped as Response Entity. If the treatment with that specific id is not found, an exception is thrown in the method and it returns http status Not found as a response. 

  createTreatment method takes TreatmentAddDTO as a Request body. As a response it gives Response Entity ok if the Tag is created or bad request in the other case.

  updateTreatment method is used for updating specific treatment, as a parameter it takes the Path variable id of the concrete treatment and Request body treatmentUpdateDTO. If the treatment is found and updated this method returns Request Entity ok and if not it returns status bad request.

  deleteTreatmentById method takes the Path variable id as a parameter and returns Response Entity ok if the Tag is successfully deleted or if not TagNotFoundException exception is caught and the method returns HTTP status bad request.

  ```java
@RestController
@RequestMapping({ "/api/treatments", "/api/treatment" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class TreatmentRestController {
    private final TreatmentService treatmentService;

    public TreatmentRestController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping()
    public List<Treatment> getTreatments() {
        return treatmentService.getTreatments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable Long id) {
        return this.treatmentService.getTreatmentById(id)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Treatment> createTreatment(@RequestBody TreatmentAddDTO treatmentAddDTO) {
        System.out.println(treatmentAddDTO);
        return this.treatmentService.createTreatment(treatmentAddDTO)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Treatment> updateTreatment(@PathVariable Long id,
            @RequestBody TreatmentUpdateDTO treatmentUpdateDTO) {
        return this.treatmentService.updateTreatment(id, treatmentUpdateDTO)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Treatment> deleteTreatmentById(@PathVariable Long id) {
        Optional<Treatment> treatment = this.treatmentService.deleteTreatmentById(id);
        try {
            this.treatmentService.getTreatmentById(id);
            return ResponseEntity.badRequest().build();
        } catch (TreatmentNotFoundException exception) {
            return ResponseEntity.ok().body(treatment.get());
        }
    }
}

  ```

  #### 2.6 User Controller
  User Controller handles everything related to users in the application, from managing user data to performing operations like creation, updating, and deletion of user accounts.
  It is mapped on 2 URLS:"/api/users", "/api/user".

  getAllUsers method is a method with no parameters and returns the list of all users in the database.
  getUserById takes Path variable id as a parameter and returns the user wrapped as Response Entity. If the user with that specific id is not found, an exception is thrown in the method and it returns HTTP status Not found as a response. 

  createUser method takes BaseUserAddDTO as a Request body. As a response it gives Response Entity ok if the User is created or bad request in the other case.

  updateUser method is used for updating specific user, as a parameter it takes the Path variable id of the concrete User and Request body BaseUserUpdateDTO. If the User is found and updated this method returns Request Entity ok and if not it returns status bad request.
  updatePasswordForUser method takes Path variable id similarly to the update method plus the encoded password as a Request param. If the password is successfully changed it returns Response Entity ok with the user in the body and Response entity with HTTP status Bad Request otherwise.

  deleteUserById method takes the Path variable id as a parameter and returns Response Entity ok if the User is successfully deleted or if not UserNotFoundException exception is caught and the method returns HTTP status bad request.
  ```java
@RestController
@RequestMapping({ "/api/users", "/api/user" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class UserController {
    private final BaseUserService baseUserService;

    public UserController(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @GetMapping()
    public List<BaseUser> getAllUsers() {
        return baseUserService.getBaseUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseUser> getUserById(@PathVariable Long id) {
        return this.baseUserService.getBaseUserById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<BaseUser> createUser(@RequestBody BaseUserAddDTO baseUserAddDTO) {
        return this.baseUserService.createBaseUser(baseUserAddDTO)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<BaseUser> updateUser(@PathVariable Long id,
            @RequestBody BaseUserUpdateDTO baseUserUpdateDTO) {
        return this.baseUserService.updateBaseUser(id, baseUserUpdateDTO)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // TODO send encrypted password from frontend, and decrypt it here with same
    // key...
    @PostMapping("/edit/password/{id}")
    public ResponseEntity<BaseUser> updatePasswordForUser(@PathVariable Long id, @RequestParam String password) {
        return this.baseUserService.changeBaseUserPassword(id, password)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseUser> deleteUserById(@PathVariable Long id) {
        Optional<BaseUser> user = this.baseUserService.deleteBaseUserById(id);
        try {
            this.baseUserService.getBaseUserById(id);
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException exception) {
            return ResponseEntity.ok().body(user.get());
        }
    }
}
```

   #### 2.7 Business Owner Controller
  Business Owner Controller uses business owner service.
  It is mapped on 2 URLS:"/api/owners", "/api/owner".

  getAllOwners method is a method with no parameters and returns the list of all owners.
  getOwnerById takes Path variable id as a parameter and returns the Owner wrapped as Response Entity. If the Owner with that specific id is not found, an exception is thrown in the method and it returns HTTP status Not found as a response. 

  createOwner method takes Path variable id as a parameter. After finding the base user with the given id it creates the Bussines Owner. As a response it gives Response Entity ok if the Owner is created or bad request in the other case.

  deleteOwnerById method takes the Path variable id as a parameter and returns Response Entity ok if the Owner is successfully deleted or if not the method returns HTTP status bad request.
  ```java
 @RestController
@RequestMapping({ "/api/owners", "/api/owner" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class BusinessOwnerRestController {
    private final BusinessOwnerService businessOwnerService;

    public BusinessOwnerRestController(BusinessOwnerService businessOwnerService) {
        this.businessOwnerService = businessOwnerService;
    }

    @GetMapping()
    public List<BusinessOwner> getAllOwners() {
        return businessOwnerService.getBusinessOwners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessOwner> getBusinessOwnerById(@PathVariable Long id) {
        return this.businessOwnerService.getBusinessOwnerById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<BusinessOwner> createBusinessOwner(@PathVariable Long id) {
        return this.businessOwnerService.createBusinessOwner(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BusinessOwner> deleteBusinessOwnerById(@PathVariable Long id) {
        Optional<BusinessOwner> user = this.businessOwnerService.deleteBusinessOwnerById(id);
        try {
            this.businessOwnerService.getBusinessOwnerById(id);
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException exception) {
            return ResponseEntity.ok().body(user.get());
        }
    }
}
  ```

 #### 2.8 Employee Controller
  This controller provides essential CRUD operations for managing employee data, ensuring proper HTTP status codes and error handling for a robust RESTful API.
  Employee Controller is mapped on 2 URLS:"/api/Customers", "/api/employee".

  getAllCustomers method is a method with no parameters and returns the list of all Customers.
  getEmployeeById takes Path variable id as a parameter and returns the Employee wrapped as Response Entity. If the Employee with that specific id is not found, an exception is thrown in the method and it returns HTTP status Not found as a response. 

  createEmployee method takes EmployeeAddDTO as a Request body. As a response it gives Response Entity ok if the Employee is created or bad request in the other case.

  deleteEmployeeById method takes the Path variable id as a parameter and returns Response Entity ok if the Employee is successfully deleted or if not the method returns HTTP status bad request.
  ```java
@RestController
@RequestMapping({ "/api/employees", "/api/employee" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class EmployeeRestController {
    private final EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return this.employeeService.getEmployeeById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeAddDTO employeeAddDTO) {
        return this.employeeService.createEmployee(employeeAddDTO)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id) {
        Optional<Employee> user = this.employeeService.deleteEmployeeById(id);
        try {
            this.employeeService.getEmployeeById(id);
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException exception) {
            return ResponseEntity.ok().body(user.get());
        }
    }
}
  ```

   #### 2.9 Customer Controller
   The Customer Controller provides endpoints for managing customers in the system, including retrieving all customers, getting specific customers by ID, creating new customers, and deleting customers. 
  Customer Controller is mapped on 2 URLS:"/api/customers", "/api/customer".

  getAllCustomers method is a method with no parameters and returns the list of all customers.
  getCustomerById takes Path variable id as a parameter and returns the Customer wrapped as Response Entity. If the Customer with that specific id is not found, an exception is thrown in the method and it returns HTTP status Not found as a response. 

  createCustomer method takes Path variable id as a parameter. After finding the base user with the given id it creates the Customer. As a response it gives Response Entity ok if the Customer is created or bad request in the other case.

  deleteCustomerById method takes the Path variable id as a parameter and returns Response Entity ok if the Customer is successfully deleted or if not the method returns HTTP status bad request.
  ```java
@RestController
@RequestMapping({ "/api/customers", "/api/customer" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class CustomerRestController {
    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return this.customerService.getCustomerById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Customer> createCustomer(@PathVariable Long id) {
        return this.customerService.createCustomer(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable Long id) {
        Optional<Customer> user = this.customerService.deleteCustomerById(id);
        try {
            this.customerService.getCustomerById(id);
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException exception) {
            return ResponseEntity.ok().body(user.get());
        }
    }
}
  ```
---
The entire code source code is in the main branch of the repository **https://github.com/Clippers-Crew/frizer.mk** \
