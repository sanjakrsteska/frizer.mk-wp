# Frizer.mk

#### Web application by: Dario Martinovski and Sanja Krsteska
---

## 1. Project description
Introducing "Frizer.mk",  your one-stop destination for all your hair and beauty needs. Whether you're looking for a fresh haircut, a rejuvenating facial, or a glamorous makeover, "Frizer.mk" connects you with the best salons and professionals in your area, ensuring you look and feel your best every time.

With "Frizer.mk", booking your next appointment has never been easier. Simply browse through a curated list of top-rated salons, and choose the one that suits your preferences. Want a specific stylist or beauty therapist? No problem! "Frizer.mk" allows you to select your preferred professional, ensuring you receive personalized service tailored to your unique style and preferences.

Say goodbye to long phone calls and endless wait times. With "Frizer.mk", you can book your appointment with just a few steps. Choose your preferred date, time, and service, and let "Frizer.mk" handle the rest. Plus, you'll receive instant confirmation, so you can plan your day with confidence.

## 2. Pages
The web application consists of the following pages:

### 2.1 Home page

### Description
The Home Page of "Frizer.mk" highlights featured salons and popular cities. Users can search for salons and view top-rated establishments.

### Page Content
- **Landing Section**:
  - **Title & Subtitle**: Welcomes users and promotes easy booking.
  - **Search Bar**: For searching salons.
  - **Top Cities Tags**: Lists cities with the most salons.

- **Featured Salons**:
  - **Title**: Displays top-rated salons.
  - **Salon Cards**: Shows salon details and links to view each salon.

### Controller
**`HomeController`**
- **Path**: `/home`, `/`
- **Methods**:
  - **`getHomePage(Model model)`**:
    - **Fetches**: Top 8 salons and top 6 cities.
    - **Returns**: Home view.

### Service Layer
- **`SalonService.getTop8Salons()`**:
  - **Purpose**: Provides top 8 salons by rating.

- **`CityService.getTop6Cities()`**:
  - **Purpose**: Lists top 6 cities with the most salons.
---
### 2.2 Salon Results Page

### Description
The Salon Results Page of "Frizer.mk" displays a list of salons based on search criteria and filters. Users can view detailed information about each salon and navigate to individual salon pages.

### Page Content
- **Search Results**:
  - **Title**: Shows the number of results based on the search criteria.
  - **Salon Cards**: Each card displays a salon's name, rating, location, and a brief description. Users can click on the card to view detailed information about the salon.

- **Filters**:
  - **Search Bar**: Allows users to enter keywords to search for salons by name.
  - **City Filter**: Users can select a city to narrow down the search results.
  - **Distance Filter**: Users can specify the maximum distance from their location to filter salons.
  - **Rating Filter**: Users can filter salons based on a minimum rating.

### Controller
**SalonController**
- **Path**: `/salons`
- **Methods**:
  - **getSalonResultsPage(Model model, @RequestParam Map<String, String> params)**:
    - **Fetches**: List of salons based on search criteria and filters.
    - **Returns**: Salon Results View.

### Service Layer
- **`filterSalons(String name, String city, Float distance, Float rating, String userLocation)`**:
  - **Purpose**: Filters salons based on name, city, distance, rating, and user location. Returns a list of salons matching the criteria.

- **`getSalons()`**
   - **Purpose**: Retrieves all salons.

- **`SalonService.getSalonById(Long id)`**:
  - **Purpose**: Retrieves a specific salon by its ID. Throws `SalonNotFoundException` if the salon does not exist.

- **`SalonService.getSalonsAsString(List<Salon> salons)`**:
  - **Purpose**: Converts a list of salons into a list of string representations.

- **`SalonService.getSalonAsString(Salon salon)`**:
  - **Purpose**: Converts a single salon into a string representation.
---

### 2.3 Salon Details Page

### Description
The Salon Details Page showcases detailed information about a specific salon, including images, profile details, treatments, reviews, and employees. Business owners or authorized employees can manage salon details, add/remove images and treatments.

### Page Content
- **Featured Images**: Main and additional images of the salon.
- **Salon Profile**: Name, location, rating, and review count.
- **Image Management**: Upload and position images (for business owners).
- **Treatments**: List of available treatments with reservation options.
- **Reviews**: Display and add reviews, including comment and rating.
- **Employees**: List of employees with options to add/remove.
- **Map**: Displays salon location.

### Controller
**`SalonController`**
- **Path**: `/salons/{id}`
- **Methods**:
  - **`salonDetailsPage(@PathVariable Long id, Model model, Principal principal)`**:
    - **Fetches**: Salon details, treatments, reviews, employee statistics, and authorization checks.
    - **Returns**: Salon details view

### Service Layer
- **`SalonService.getSalonById(Long id)`**: Retrieves salon details by ID.
- **`ReviewService.getReviewsForEmployees(List<Employee> employees)`**: Provides employee reviews.
- **`SalonService.isUserAuthorizedToAddTreatment(Long salonId, String username)`**: Checks user authorization for adding treatments.
- **`SalonService.isUserAuthorizedToAddSalon(Long salonId, String username)`**: Checks user authorization for managing salon details.
---
### 2.4 Reserving Salon Appointments

### Overview

This feature allows users to reserve appointments at a salon. Users can select a salon, treatment, employee, and time slot, then confirm their reservation.

### Flow

1. **Select Employee**

   - **URL**: `/salons/appointment/employees`
   - **Method**: `GET`
   - **Parameters**:
     - `salon` (Long): Salon ID
     - `treatment` (Long): Treatment ID

   **Functionality**:
   - Display salon details and a list of employees.
   - Users select an employee to proceed with the appointment.

   **Errors**:
   - Redirect to `/salons/{id}` if no employees are found.

2. **Choose Appointment**

   - **URL**: `/salons/appointment/confirm`
   - **Method**: `GET`
   - **Parameters**:
     - `salon` (Long): Salon ID
     - `treatment` (Long): Treatment ID
     - `employee` (Long): Employee ID

   **Functionality**:
   - Show details of the selected salon, treatment, and employee.
   - Allow users to choose a date and time slot for the appointment.

   **Errors**:
   - Redirect to `/app-error` if any details are missing.

3. **Confirm Appointment**

   - **URL**: `/appointments/create`
   - **Method**: `POST`

   **Form Submission**:
   - Submit with salon, treatment, employee IDs, and appointment time.
   - Finalize the reservation.

### Templates

- **`appointment-employees.html`**: Displays salon details and a list of employees to choose from.
- **`appointment-choose-app.html`**: Allows users to select a date and time for the appointment.
- **`appointment-confirm.html`**: Shows final appointment details and confirmation button.

---
### 2.5 Appointments Page

### Overview

This page dispalys the appointments past and future appointments, with a functionality to manage them.

- **Customer View**:
  - **Active Appointments**: List of ongoing appointments. Option to cancel if not within 1 day.
  - **Past Appointments**: List of completed appointments.

- **Employee View**:
  - **Active Appointments**: List of ongoing appointments specific to the employee's salon. Option to cancel or mark as done if not within 1 day.
  - **Past Appointments**: List of completed appointments.

### Controller: `AppointmentController`

- **GET `/appointments`**: Displays appointments for the logged-in user. Redirects to login if unauthenticated.
- **POST `/appointments/create`**: Creates a new appointment. Redirects on success or shows an error.
- **POST `/appointments/mark-as-done/{id}`**: Marks an appointment as done.
- **POST `/appointments/delete/{id}`**: Deletes an appointment.

### Service: `AppointmentServiceImpl`

- **`createAppointment(AppointmentAddDTO)`**: Creates a new appointment.
- **`updateAppointment(Long id, LocalDateTime from, LocalDateTime to, Long treatmentId, Long salonId, Long employeeId, Long customerId)`**: Updates an appointment.
- **`deleteAppointmentById(Long id)`**: Deletes an appointment by ID.

### Usage

- **Customers**: View and manage your upcoming and past appointments. Cancel future appointments or review completed ones.
- **Employees**: Access and manage your appointments at the salon, including marking them as completed or canceling if necessary.

---
### 2.6 Profile Page

### Description
The Profile Page displays user details and, if applicable, the salons managed by business owners. Users can edit their profile or log out.

### Page Content
- **User Details**:
  - **Information**: First name, last name, email, phone number.
  - **Buttons**:
    - **Edit Details**: Links to Profile Edit Page.
    - **Log Out**: Logs the user out.

- **Business Owner Salons**:
  - **Title**: "Your Salons".
  - **Salon Cards**: Name, rating, location, and options to view or delete salons.

- **Add Salon Form**:
  - **Title**: "Register Salon".
  - **Fields**: Name, description, location, city, phone number, latitude, longitude.
  - **Submit**: "Add".

### Controller
**`ProfileController`**
- **Path**: `/profile`, `/profile/edit`
- **Methods**:
  - **`profile(Model model)`**: Fetches user details and business owner salons.
  - **`editProfile(Model model, @RequestParam(required = false) String error)`**: Prepares the profile edit view.
  - **`editProfile(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber, Model model)`**: Updates user details.
  - **`changeUserPassword(Locale locale, @RequestParam String oldPassword, @RequestParam String newPassword)`**: Changes user password.

### Service Layer
- **`BaseUserService.updateBaseUser(Long userId, BaseUserUpdateDTO updateDTO)`**: Updates user details.
- **`BaseUserService.getBaseUserById(Long userId)`**: Retrieves user information.
- **`AuthService.login(String email, String password)`**: Authenticates for password changes.
- **`BaseUserService.changeBaseUserPassword(Long userId, String newPassword)`**: Updates user password.

---
### 2.7 Authentication Pages

### Login Page

**Description**: The login page allows users to authenticate into their accounts.

**Page Content**:
- **Form Fields**:
  - **Username**: Input for username.
  - **Password**: Input for password.
- **Buttons**:
  - **Submit**: Log In.
- **Additional**:
  - **Error Message**: Displays if authentication fails.
  - **Link**: Redirects to registration page if the user does not have an account.

**Controller**: `LoginController`
- **Path**: `/login`
- **Methods**:
  - **`getLoginPage(Model model)`**: Displays login form.
  - **`login(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String redirect, RedirectAttributes redirectAttributes, Model model, HttpSession session)`**: Authenticates the user and redirects accordingly.
---
### Register Page

**Description**: The registration page allows new users to create an account.

**Page Content**:
- **Form Fields**:
  - **Email**: Input for email address.
  - **Password**: Input for password.
  - **Confirm Password**: Input to confirm password.
  - **First Name**: Input for first name.
  - **Last Name**: Input for last name.
  - **Phone Number**: Input for phone number.
- **Buttons**:
  - **Submit**: Register.
- **Additional**:
  - **Error Message**: Displays if registration fails.
  - **Link**: Redirects to login page if the user already has an account.

**Controller**: `RegisterController`
- **Path**: `/register`
- **Methods**:
  - **`getRegisterPage(@RequestParam(required = false) String error, Model model)`**: Displays registration form.
  - **`register(@RequestParam String email, @RequestParam String password, @RequestParam String repeatedPassword, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber)`**: Registers a new user and handles errors.
---
### Logout

**Description**: Logs the user out and redirects to the login page.

**Controller**: `LogoutController`
- **Path**: `/logout`
- **Methods**:
  - **`logout(HttpServletRequest request)`**: Invalidates the session and redirects to login.

---
## 3. Security

### WebSecurityConfig
The `WebSecurityConfig` class configures the application's security settings. It customizes HTTP security rules, including form-based login and logout. CSRF protection is disabled for simplicity, and by default, all requests are permitted. It specifies custom URLs for login and logout: successful logins redirect users to the home page, while failed logins direct them to an error page. Logout clears the session and cookies, and redirects users to the login page. Access denied scenarios are handled by redirecting to a specific access denied page.

### CustomUsernamePasswordAuthenticationProvider
The `CustomUsernamePasswordAuthenticationProvider` class provides a custom authentication mechanism. It validates user credentials by checking the provided username and password against the stored values using `UserService` and `PasswordEncoder`. If the credentials are correct, it returns an authenticated token; otherwise, it throws a `BadCredentialsException`. This custom provider enhances security by replacing the default authentication mechanism with a tailored solution.
