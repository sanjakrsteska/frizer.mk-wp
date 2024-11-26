# "Frizer.mk"

#### Web application by: Dario Martinovski and Sanja Krsteska
---

## 1. Project description
Introducing "Frizer.mk",  your one-stop destination for all your hair and beauty needs. Whether you're looking for a fresh haircut, a rejuvenating facial, or a glamorous makeover, "Frizer.mk" connects you with the best salons and professionals in your area, ensuring you look and feel your best every time.

With "Frizer.mk", booking your next appointment has never been easier. Simply browse through a curated list of top-rated salons, and choose the one that suits your preferences. Want a specific stylist or beauty therapist? No problem! "Frizer.mk" allows you to select your preferred professional, ensuring you receive personalized service tailored to your unique style and preferences.

Say goodbye to long phone calls and endless wait times. With "Frizer.mk", you can book your appointment with just a few steps. Choose your preferred date, time, and service, and let "Frizer.mk" handle the rest. Plus, you'll receive instant confirmation, so you can plan your day with confidence.

## 2. Controllers 
The web application consists of the following controllers:

### 2.1 Appointment Controller
  The AppointmentRestController class handles RESTful web services for managing appointments in the "Frizer.mk" application
## Resource URL
`/api/appointments`, `/api/appointment`

  ## Methods
  ### Get All Appointments
  #### HTTP Request
  ```bash
  GET /api/appointments
  ```
  #### Response
  ***200 OK***: Returns the list of appointments.
  #### Example Request 
   ```bash
  GET /api/appointments
  ```
  #### Example Response
  ``` json
  [
    {
        "id": 1,
        "dateFrom": "2024-05-12 10:00",
        "dateTo": "2024-05-12 11:00",
        "treatmentId": 2,
        "salonId": 3,
        "employeeId": 2,
        "customerId": 2,
        "attended": true
    },
    {
        "id": 2,
        "dateFrom": "2024-05-13 10:00",
        "dateTo": "2024-05-13 11:00",
        "treatmentId": 2,
        "salonId": 3,
        "employeeId": 2,
        "customerId": 2,
        "attended": true
    }
]
  ```
  ### Get Appointment by ID
  #### HTTP Request
  ```bash
  GET /api/appointments/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the appointment.
  #### Response
  ***200 OK:*** Returns the appointment details.
  ***404 Not Found:*** If the appointment with the specified ID does not exist.
  #### Example Request 
   ```bash
  GET /api/appointments/1
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "dateFrom": "2024-05-12 10:00",
    "dateTo": "2024-05-12 11:00",
    "treatmentId": 2,
    "salonId": 3,
    "employeeId": 2,
    "customerId": 2,
    "attended": true
}
```
   
  ### Create Appointment
  #### HTTP Request
  ```bash
  POST /api/appointment/add
```
#### Request Body
The request body must contain a JSON object with the following fields:
  - dateFrom (LocalDateTime, required): The start date and time of the appointment.
  - dateTo (LocalDateTime, required): The end date and time of the appointment.
  - treatmentId (Long, required): ID of the treatment.
  - salonId (Long, required): ID of the salon.
  - employeeId (Long, required): ID of the employee.
  - customerId (Long, required): ID of the customer.
#### Response
***200 OK:*** Returns the created appointment.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
  POST /api/appointment/add
  Content-Type: application/json
  {
      "dateFrom": "2024-05-22T10:00:00",
      "dateTo": "2024-05-22T11:00:00",
      "treatmentId": 2,
      "salonId": 3,
      "employeeId": 2,
      "customerId": 2
  }
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "dateFrom": "2024-05-12 10:00",
    "dateTo": "2024-05-12 11:00",
    "treatmentId": 2,
    "salonId": 3,
    "employeeId": 2,
    "customerId": 2,
    "attended": true
}
  ```  

  ### Delete Appointment by ID
  #### HTTP Request
  ```bash
  DELETE /api/appointments/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the appointment to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted appointment.\
  ***404 Not Found:*** If the appointment could not be deleted.
  #### Example Request 
  ```bash
    DELETE /api/appointments/delete/1
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "dateFrom": "2024-05-12 10:00",
    "dateTo": "2024-05-12 11:00",
    "treatmentId": 2,
    "salonId": 3,
    "employeeId": 2,
    "customerId": 2,
    "attended": true
}
```
  ### Change Attendance Status of Appointment
  #### HTTP Request
  ```bash
  POST /api/appointment/attended/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the appointment to be deleted.
  #### Response
  ***200 OK:*** Returns the updated appointment with the changed attendance status.
  ***404 Not Found:*** If the appointment could not be updated.
  #### Example Request 
   ```bash
    POST /api/appointment/attended/1
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "dateFrom": "2024-05-12 10:00",
    "dateTo": "2024-05-12 11:00",
    "treatmentId": 2,
    "salonId": 3,
    "employeeId": 2,
    "customerId": 2,
    "attended": true
}
  ```  
## 2.2 Review Controller
The Review Controller class manages RESTful web services for handling reviews in the "Frizer.mk" application.
  
  ## Resource URL
`/api/reviews`, `/api/review`

  ## Methods
  ### Get All Reviews
  #### HTTP Request
  ```bash
  GET /api/reviews
  ```
  #### Response
  ***200 OK***: Returns the list of reviews.
  #### Example Request 
   ```bash
  GET /api/reviews
  ```
  #### Example Response
  ``` json
  [
    {
        "id": 1,
        "customerId": 1,
        "employeeId": 2,
        "rating": 4,
        "comment": "Great service!"
    },
    {
        "id": 2,
        "customerId": 3,
        "employeeId": 2,
        "rating": 4.6,
        "comment": "Excellent experience!"
    }
]
  ```
  #
  ### Get Review by ID
  #### HTTP Request
  ```bash
  GET /api/reviews/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the review.
  #### Response
  ***200 OK:*** Returns the review details.
  ***404 Not Found:*** If the review with the specified ID does not exist.
  #### Example Request 
   ```bash
  GET /api/reviews/1
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "customerId": 1,
    "employeeId": 2,
    "rating": 4.6,
    "comment": "Great service!"
}
  ```
#

  ### Create Review for Employee
  #### HTTP Request
  ```bash
  POST /api/review/add-for-employee
  ```
#### Request Body
The request body must contain a JSON object with the following fields:
  - customerId (Long, required): ID of the customer who submitted the review.
  - employeeId (Long, required): ID of the employee who is being reviewed.
  - rating (Double, required): Rating given by the customer.
  - comment (String): Optional comment provided by the  customer.

#### Response
***200 OK:*** Returns the created review.
***400 Bad Request:*** If the request body is invalid.
#### Example Request
```bash
POST /api/review/add-for-employee
Content-Type: application/json

{
    "customerId": 1,
    "employeeId": 2,
    "rating": 4.5,
    "comment": "Great service!"
}
```
### Example Response
```json
{
    "id": 1,
    "customerId": 1,
    "employeeId": 2,
    "rating": 4.5,
    "comment": "Great service!"
} 
```
   ### Create Review for Customer
  #### HTTP Request
  ```bash
  POST /api/review/add-for-customer
  ```
#### Request Body
The request body must contain a JSON object with the following fields:
  - customerId (Long, required): ID of the customer who  is being reviewed.
  - employeeId (Long, required): ID of the employee who  submitted the review.
  - rating (Double, required): Rating given by the employee.
  - comment (String): Optional comment provided by the   employee.

#### Response
***200 OK:*** Returns the created review.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/reviews/add-for-customer
Content-Type: application/json

{
    "customerId": 1,
    "employeeId": 2,
    "rating": 4.2,
    "comment": "Great customer!"
}
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "customerId": 1,
    "employeeId": 2,
    "rating": 4.2,
    "comment": "Great customer!"
}
  ```  

  ### Delete Review by ID
  #### HTTP Request
  ```bash
  DELETE /api/reviews/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the review to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted review.
  ***404 Not Found:*** If the review could not be deleted.
  #### Example Request 
   ```bash
    DELETE /api/reviews/delete/1
  ```
  #### Example Response
``` json
{
    "id": 1,
    "customerId": 1,
    "employeeId": 2,
    "rating": 4.2,
    "comment": "Great service!"
}
```
### Edit Review 
  #### HTTP Request
  ```bash
  POST /api/reviews/edit/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the review to be deleted.
 #### Request Body
The request body must contain a JSON object with the following fields:
  - rating (Double): Updated rating.
  - comment (String): Updated comment.

#### Response
***200 OK:*** Returns the updated review.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/reviews/edit/1
Content-Type: application/json

{
    "rating": 5.0,
    "comment": "Excellent service!"
}
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "customerId": 1,
    "employeeId": 2,
    "rating": 5.0,
    "comment": "Excellent service!"
}
  ```  

  #
### 2.3 Salon Controller
  The SalonRestController class handles RESTful web services for managing salons in the "Frizer.mk" application
## Resource URL
`/api/salons`, `/api/salon`

  ## Methods
  ### Get All Salons
  #### HTTP Request
  ```bash
  GET /api/salons
  ```
  #### Response
  ***200 OK***: Returns the list of salons.
  #### Example Request 
   ```bash
  GET /api/salons
  ```
  #### Example Response
  ``` json
  [
    {
        "id": 3,
        "name": "Frizerski salon Asim",
        "description": "Frizerski salon za mazhi",
        "location": "veles",
        "phoneNumber": "broj3",
        "owner": "1",
        "employees":[],
        "salonTreatments": [],
        "imagePaths": [],
        "salonTags":[]
        
    },
    {
        "id": 4,
        "name": "Frizerski salon Nenko",
        "description": "Frizerski salon za mazhi",
        "location": "Sv. Nikole",
        "phoneNumber": "broj22",
        "owner": "1",
        "employees":[],
        "salonTreatments": [],
        "imagePaths": [],
        "salonTags":[]    
    }
]
  ```
  #
  ### Get Salon by ID
  #### HTTP Request
  ```bash
  GET /api/salons/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the salon.
  #### Response
  ***200 OK:*** Returns the salon details.
  ***404 Not Found:*** If the salon with the specified ID does not exist.
  #### Example Request 
  ```bash
  GET /api/salons/1
  ```
  #### Example Response
``` json
{
        "id": 1,
        "name": "Frizerski salon Asim",
        "description": "Frizerski salon za mazhi",
        "location": "veles",
        "phoneNumber": "broj3",
        "owner": "1",
        "employees":[],
        "salonTreatments": [],
        "imagePaths": [],
        "salonTags":[]        
    }
```
   
#
  ### Create Salon
  #### HTTP Request
  ```bash
  POST /api/salon/add
  ```
#### Request Body
The request body must contain a JSON object with the following fields:
  - name (String, required): Name of the salon.
  - location (String, required): Location of the salon.
  - description (String,required): Description of the salon.
 - phoneNumber (String,required): Phone number of the salon.
 - businessOwnerId (Long,required): ID of the business owner.
#### Response
***200 OK:*** Returns the created salon.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/salon/add
Content-Type: application/json
{
    "name": "Salon XYZ",
    "description": "A modern hair salon offering a range of services.",
    "location": "123 Main Street, Cityville",
    "phoneNumber": "+1234567890",
    "businessOwnerId": "1",
}
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "name": "Salon XYZ",
    "description": "A modern hair salon offering a range of services.",
    "location": "123 Main Street, Cityville",
    "phoneNumber": "+1234567890",
    "owner": "1",
    "employees": [],
    "salonTreatments": [],
    "salonTags": [],
    "imagePaths": []
    
}
  ```  
  ### Delete Salon by ID
  #### HTTP Request
  ```bash
  DELETE /api/salons/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the salon to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted salon.
  ***404 Not Found:*** If the salon could not be deleted.
  #### Example Request 
   ```bash
    DELETE /api/salons/delete/1
  ```
  #### Example Response
``` json
{
    "id": 1,
    "name": "Salon XYZ",
    "description": "A modern hair salon offering a range of services.",
    "location": "123 Main Street, Cityville",
    "phoneNumber": "+1234567890",
    "owner": "1",
    "employees": [],
    "salonTreatments": [],
    "salonTags": [],
    "imagePaths": []
    
}
```
  ### Edit Salon 
  #### HTTP Request
  ```bash
  POST /api/salons/edit/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the salon to be deleted.
 #### Request Body
The request body must contain a JSON object with the following fields:
  - name (String, required): Name of the salon.
  - location (String, required): Location of the salon.
  - description (String,required): Description of the salon.
  - phoneNumber (String,required): Phone number of the salon.

#### Response
***200 OK:*** Returns the updated salon.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/salons/edit/1
Content-Type: application/json
{
    "name": "Salon XYZ",
    "description": "A modern hair salon offering a range of services.",
    "location": "123 Main Street, Cityville",
    "phoneNumber": "+1234567890"
    
}
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "name": "Salon XYZ",
    "description": "A modern hair salon offering a range of services.",
    "location": "123 Main Street, Cityville",
    "phoneNumber": "+1234567890",
    "owner": "1",
    "employees": [],
    "salonTreatments": [],
    "salonTags": [],
    "imagePaths": []    
}
  ```  
#
### 2.4 Tag Controller
  The TagRestController class handles RESTful web services for managing tags in the "Frizer.mk" application
## Resource URL
`/api/tags`, `/api/tag`

  ## Methods
  ### Get All Tags
  #### HTTP Request
  ```bash
  GET /api/tags
  ```
  #### Response
  ***200 OK***: Returns the list of tags.
  #### Example Request 
   ```bash
  GET /api/tags
  ```
  #### Example Response
  ``` json
  [
    {
        "id": 1,
        "name": "Tag A"
    },
    {
        "id": 2,
        "name": "Tag B"
    }
]
  ```
  #
  ### Get Tag by ID
  #### HTTP Request
  ```bash
  GET /api/tags/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the tag.
  #### Response
  ***200 OK:*** Returns the tag details.
  ***404 Not Found:*** If the tag with the specified ID does not exist.
  #### Example Request 
   ```bash
  GET /api/tags/1
  ```
  #### Example Response
``` json
{
    "id": 1,
    "name": "Tag A"
}
```  
#
  ### Create Tag
  #### HTTP Request
  ```bash
  POST /api/tags/add
  ```
#### Request Body
The request body must contain a JSON object with the following fields:
  - name (String, required): Name of the tag.
 
#### Response
***200 OK:*** Returns the created tag.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
  POST /api/tag/add
  Content-Type: application/json
  {
    "name": "Tag C"
  }
  ```
  #### Example Response
  ``` json
{
    "id": 3,
    "name": "Tag C"
}
  ```  

  ### Delete Tag by ID
  #### HTTP Request
  ```bash
  DELETE /api/tags/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the tag to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted tag.
  ***404 Not Found:*** If the tag could not be deleted.
  #### Example Request 
  ```bash
    DELETE /api/tags/delete/1
  ```
  #### Example Response
``` json
{
    "id": 3,
    "name": "Tag C"
}
```
#
### 2.5 Treatment Controller
  The TreatmentRestController class handles RESTful web services for managing treatments in the "Frizer.mk" application
## Resource URL
`/api/treatments`, `/api/treatment`

  ## Methods
  ### Get All Treatments
  #### HTTP Request
  ```bash
  GET /api/treatments
  ```
  #### Response
  ***200 OK***: Returns the list of treatments.
  #### Example Request 
   ```bash
  GET /api/treatments
  ```
  #### Example Response
  ``` json
  [
    {
        "id": 1,
        "name": "Haircut",
        "salon": 3,
        "price": 20.0
    },
    {
        "id": 2,
        "name": "Hair Coloring",
        "salon": 4,
        "price": 50.0
    }
]
  ```
  #
  ### Get Treatment by ID
  #### HTTP Request
  ```bash
  GET /api/treatments/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the treatment.
  #### Response
  ***200 OK:*** Returns the treatment details.
  ***404 Not Found:*** If the treatment with the specified ID does not exist.
  #### Example Request 
  ```bash
  GET /api/treatments/1
  ```
  #### Example Response
``` json
{
    "id": 1,
    "name": "Haircut",
    "salon": 3,
    "price": 20.0
}
```
   
#
  ### Create Treatment
  #### HTTP Request
  ```bash
  POST /api/treatment/add
  ```
#### Request Body
The request body must contain a JSON object with the following fields:
  - name (String, required): Name of the treatment.
  - salonId (Long, required): ID of the salon where the treatment is offered.
  - price (Double, required): Price of the treatment.

#### Response
***200 OK:*** Returns the created treatment.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/treatments/add
Content-Type: application/json

{
    "name": "Spa Manicure",
    "salonId": 5,
    "price": 30.0
}
  ```
  #### Example Response
  ``` json
{
    "id": 3,
    "name": "Spa Manicure",
    "salon": 5,
    "price": 30.0
}
  ```  

  ### Delete Treatment by ID
  #### HTTP Request
  ```bash
  DELETE /api/treatments/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the treatment to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted treatment.
  ***404 Not Found:*** If the treatment could not be deleted.
  #### Example Request 
   ```bash
    DELETE /api/treatments/delete/3
  ```
  #### Example Response
``` json
{
    "id": 3,
    "name": "Spa Manicure",
    "salon": 5,
    "price": 30.0
}
```
### Edit Treatment 
  #### HTTP Request
  ```bash
  POST /api/treatments/edit/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the review to be deleted.
 #### Request Body
The request body must contain a JSON object with the following fields:
  - name (String, optional): Updated name of the treatment.
  - price (Double, optional): Updated price of the treatment.

#### Response
***200 OK:*** Returns the updated treatment.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/treatments/edit/1
Content-Type: application/json
{
    "name": "Spa Manicure",
    "price": 30.0
}
  ```
  #### Example Response
  ``` json
{
    "id" : 1,
    "salon": 5,
    "name": "Spa Manicure",
    "price": 30.0
}
  ```  
#
### 2.6 User Controller
  The UserRestController class handles RESTful web services for managing users in the "Frizer.mk" application
## Resource URL
`/api/users`, `/api/user`

  ## Methods
  ### Get All Users
  #### HTTP Request
  ```bash
  GET /api/users
  ```
  #### Response
  ***200 OK***: Returns the list of users.
  #### Example Request 
   ```bash
  GET /api/users
  ```
  #### Example Response
  ``` json
 [
    {
        "id": 1,
        "email": "john@example.com",
        "firstName": "John",
        "lastName": "Doe",
        "phoneNumber": "+1234567890",
        "role": "ROLE_USER"
    },
    {
        "id": 2,
        "email": "jane@example.com",
        "firstName": "Jane",
        "lastName": "Doe",
        "phoneNumber": "+9876543210",
        "role": "ROLE_USER"
    }
]
  ```
  #
  ### Get User by ID
  #### HTTP Request
  ```bash
  GET /api/users/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the user.
  #### Response
  ***200 OK:*** Returns the user details.
  ***404 Not Found:*** If the user with the specified ID does not exist.
  #### Example Request 
  ```bash
  GET /api/users/1
  ```
  #### Example Response
``` json
{
        "id": 1,
        "email": "john@example.com",
        "firstName": "John",
        "lastName": "Doe",
        "phoneNumber": "+1234567890",
        "role": "ROLE_USER"
}
```
   
#
  ### Create User
  #### HTTP Request
  ```bash
  POST /api/users/add
  ```
#### Request Body
The request body must contain a JSON object with the following fields:
  - email (String, required): Email address of the user.
  - password (String, required): Password of the user.
  - firstName (String, required): First name of the user.
  - lastName (String, required): Last name of the user.
  - phoneNumber (String, optional): Phone number of the user.

#### Response
***200 OK:*** Returns the created user.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/users/add
Content-Type: application/json
{
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890"
}
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "role": "ROLE_USER"
}
  ```  

  ### Delete User by ID
  #### HTTP Request
  ```bash
  DELETE /api/users/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the user to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted user.
  ***404 Not Found:*** If the user could not be deleted.
  #### Example Request 
   ```bash
    DELETE /api/users/delete/3
  ```
  #### Example Response
``` json
{
    "id": 1,
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "role": "ROLE_USER"
}
```
### Edit User 
  #### HTTP Request
  ```bash
  POST /api/users/edit/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the user to be updated.
 #### Request Body
The request body must contain a JSON object with the following fields:
 - firstName (String, required): Updated First name of the user.
  - lastName (String, required): Updated Last name of the user.
  - phoneNumber (String, optional): Updated Phone number of the user.

#### Response
***200 OK:*** Returns the updated user.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/users/edit/1
Content-Type: application/json
{
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890"
}
  ```
  #### Example Response
  ``` json
{
    "id": 1,
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "role": "ROLE_USER"
}
  ``` 
### Edit Password For User 
  #### HTTP Request
  ```bash
  POST /api/users/edit/password/{id}
  ```
  #### Path parameters
  - `id` (required): id (required): The unique identifier of the user whose password is to be updated.
 #### Request Body
The request body must contain a JSON object with the following fields:
 - password (String, required): The new password for the user.

#### Response
***200 OK:*** Returns the updated user.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/user/edit/password/3
Content-Type: application/json

{
    "password": "newpassword123"
}
  ```
  #### Example Response
  ``` json
{
    "id": 3,
    "email": "alice@example.com",
    "firstName": "Alice",
    "lastName": "Smith",
    "phoneNumber": "+1122334455",
    "role": "ROLE_USER"
}
  ```   
#
### 2.7 Business Owner Controller
  The BusinessOwnerRestController class handles RESTful web services for managing owners in the "Frizer.mk" application
## Resource URL
`/api/owners`, `/api/owner`

  ## Methods
  ### Get All Owners
  #### HTTP Request
  ```bash
  GET /api/owners
  ```
  #### Response
  ***200 OK***: Returns the list of owners.
  #### Example Request 
   ```bash
  GET /api/owners
  ```
  #### Example Response
  ``` json
[
    {
        "id": 1,
        "baseUser": {
            "id": 1,
            "email": "dario@email.com",
            "firstName": "FirstName",
            "lastName": "LastName",
            "phoneNumber": "phoneNumber",
            "role": "ROLE_OWNER"
        },
        "salonList": [
            {
                "id": 7,
                "name": "Kaj Vace",
                "location": "Kej 24mi Maj,Prilep",
                "phoneNumber": "074443443",
                "owner": 1
            },
            {
                "id": 8,
                "name": "Kaj Dimo",
                "location": "Kej 24mi Maj,Valandovo",
                "phoneNumber": "078999111",
                "owner": 1
            }
           
        ]
    },
    {
        "id": 2,
         "baseUser": {
            "id": 1,
            "email": "jane.smith@example.com",
            "firstName": "Jane",
            "lastName": "Smith",
            "phoneNumber": "+9876543210",
            "role": "ROLE_OWNER"
        },
         "salonList":[]     
    }
]
  ```
  #
  ### Get Owner by ID
  #### HTTP Request
  ```bash
  GET /api/owners/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the owner.
  #### Response
  ***200 OK:*** Returns the owner details.
  ***404 Not Found:*** If the owner with the specified ID does not exist.
  #### Example Request 
  ```bash
  GET /api/owners/1
  ```
  #### Example Response
``` json
{
        "id": 1,
        "baseUser": {
            "id": 1,
            "email": "dario@email.com",
            "firstName": "FirstName",
            "lastName": "LastName",
            "phoneNumber": "phoneNumber",
            "role": "ROLE_OWNER"
        },
        "salonList": [
            {
                "id": 7,
                "name": "Kaj Vace",
                "location": "Kej 24mi Maj,Prilep",
                "phoneNumber": "074443443",
                "owner": 1
            },
            {
                "id": 8,
                "name": "Kaj Dimo",
                "location": "Kej 24mi Maj,Valandovo",
                "phoneNumber": "078999111",
                "owner": 1
            }
           
        ]
    }
```
#
  ### Create Owner
  #### HTTP Request
  ```bash
  POST /api/owners/add/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the base user.

#### Response
***200 OK:*** Returns the created owner.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
```bash
POST /api/owners/add/5
Content-Type: application/json
```
  #### Example Response
  ``` json
{
        "id": 1,
        "baseUser": {
            "id": 5,
            "email": "john@example.com",
            "firstName": "John",
            "lastName": "Doe",
            "phoneNumber": "+1234567890",
            "role": "ROLE_OWNER"
        },
        "salonList": []
    }
  ```  

  ### Delete Owner by ID
  #### HTTP Request
  ```bash
  DELETE /api/owners/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the owner to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted owner.
  ***404 Not Found:*** If the owner could not be deleted.
  #### Example Request 
   ```bash
    DELETE /api/owners/delete/1
  ```
  #### Example Response
``` json
{
        "id": 1,
        "baseUser": {
            "id": 5,
            "email": "john@example.com",
            "firstName": "John",
            "lastName": "Doe",
            "phoneNumber": "+1234567890",
            "role": "ROLE_OWNER"
        },
        "salonList": []
    }
```
 ### 2.8 Employee Controller
The EmployeeRestController class handles RESTful web services for managing employees in the "Frizer.mk" application
## Resource URL
`/api/employees`, `/api/employee`

  ## Methods
  ### Get All Employees
  #### HTTP Request
  ```bash
  GET /api/employees
  ```
  #### Response
  ***200 OK***: Returns the list of employees.
  #### Example Request 
   ```bash
  GET /api/employees
  ```
  #### Example Response
  ``` json
[
   {
    "id": 1,
    "appointmentsActive": [],
    "appointmentsHistory": [],
    "salon": {
        "id": 7,
        "name": "Kaj Vace",
        "description": "Zenski frizerski salon",
        "location": "Kej 24mi Maj,Prilep",
        "phoneNumber": "074443443",
        "owner": "1",
        "employees": [],
        "salonTreatments": [],
        "salonTags": [],
        "imagePaths": []
    },
    "baseUser": {
        "id": 1,
        "email": "dario@email.com",
        "firstName": "FirstName",
        "lastName": "LastName",
        "phoneNumber": "phoneNumber",
        "role": "ROLE_EMPLOYEE"
    }
}
]
  ```
  #
  ### Get Employee by ID
  #### HTTP Request
  ```bash
  GET /api/employees/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the employee.
  #### Response
  ***200 OK:*** Returns the employee details.
  ***404 Not Found:*** If the employee with the specified ID does not exist.
  #### Example Request 
  ```bash
  GET /api/employees/1
  ```
  #### Example Response
``` json
{
    "id": 1,
    "appointmentsActive": [],
    "appointmentsHistory": [],
    "salon": {
        "id": 7,
        "name": "Kaj Vace",
        "description": "Zenski frizerski salon",
        "location": "Kej 24mi Maj,Prilep",
        "phoneNumber": "074443443",
        "owner": "1",
        "employees": [],
        "salonTreatments": [],
        "salonTags": [],
        "imagePaths": []
    },
    "baseUser": {
        "id": 1,
        "email": "dario@email.com",
        "firstName": "FirstName",
        "lastName": "LastName",
        "phoneNumber": "phoneNumber",
        "role": "ROLE_EMPLOYEE"
    }
}
```
#
  ### Create Employee
  #### HTTP Request
  ```bash
  POST /api/employees/add
  ```

 #### Request Body
The request body must contain a JSON object with the following fields:
  - userId (Long, required): ID of the base user.
  - salonId (Long, required): ID of the salon.

#### Response
***200 OK:*** Returns the created employee.
***400 Bad Request:*** If the request body is invalid.

  #### Example Request 
   ```bash
POST /api/employees/add

Content-Type: application/json   
{
    "userId": 1,
    "salonId": 205
}

  ```
  #### Example Response
  ``` json
  {
    "id": 1,
    "appointmentsActive": [],
    "appointmentsHistory": [],
    "salon": {
        "id": 7,
        "name": "Kaj Vace",
        "description": "Zenski frizerski salon",
        "location": "Kej 24mi Maj,Prilep",
        "phoneNumber": "074443443",
        "owner": "1",
        "employees": [],
        "salonTreatments": [],
        "salonTags": [],
        "imagePaths": []
    },
    "baseUser": {
        "id": 1,
        "email": "dario@email.com",
        "firstName": "FirstName",
        "lastName": "LastName",
        "phoneNumber": "phoneNumber",
        "role": "ROLE_EMPLOYEE"
    }
  }

  ```  
  ### Delete Employee by ID
  #### HTTP Request
  ```bash
  DELETE /api/employees/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the employee to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted employee.
  ***404 Not Found:*** If the employee could not be deleted.
  #### Example Request 
   ```bash
    DELETE /api/employees/delete/1
  ```
  #### Example Response
``` json
{
    "id": 1,
    "appointmentsActive": [],
    "appointmentsHistory": [],
    "salon": {
        "id": 7,
        "name": "Kaj Vace",
        "description": "Zenski frizerski salon",
        "location": "Kej 24mi Maj,Prilep",
        "phoneNumber": "074443443",
        "owner": "1",
        "employees": [],
        "salonTreatments": [],
        "salonTags": [],
        "imagePaths": []
    },
    "baseUser": {
        "id": 1,
        "email": "dario@email.com",
        "firstName": "FirstName",
        "lastName": "LastName",
        "phoneNumber": "phoneNumber",
        "role": "ROLE_EMPLOYEE"
    }
  }
```
#
 ### 2.9 Customer Controller
The CustomerRestController class handles RESTful web services for managing customers in the "Frizer.mk" application.
## Resource URL
`/api/customers`, `/api/customer`

  ## Methods
  ### Get All Customers
  #### HTTP Request
  ```bash
  GET /api/customers
  ```
  #### Response
  ***200 OK***: Returns the list of customers.
  #### Example Request 
   ```bash
  GET /api/customers
  ```
  #### Example Response
  ``` json
 [
    {
        "id": 1,
        "appointmentsActive": [],
        "appointmentsHistory": [],
        "baseUser": {
            "id": 1,
            "email": "dario@email.com",
            "firstName": "FirstName",
            "lastName": "LastName",
            "phoneNumber": "phoneNumber",
            "role": "ROLE_EMPLOYEE"
        }
    }
]
  ```
  #
  ### Get Customer by ID
  #### HTTP Request
  ```bash
  GET /api/customers/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the customer.
  #### Response
  ***200 OK:*** Returns the customer details.
  ***404 Not Found:*** If the customer with the specified ID does not exist.
  #### Example Request 
  ```bash
  GET /api/customers/1
  ```
  #### Example Response
``` json
 {
        "id": 1,
        "appointmentsActive": [],
        "appointmentsHistory": [],
        "baseUser": {
            "id": 1,
            "email": "dario@email.com",
            "firstName": "FirstName",
            "lastName": "LastName",
            "phoneNumber": "phoneNumber",
            "role": "ROLE_EMPLOYEE"
        }
  }
```
#
  ### Create Customer
  #### HTTP Request
  ```bash
  POST /api/customers/add/{id}
  ```
#### Path parameters
  - `id` (required): The unique identifier of the base user.

#### Response
***200 OK:*** Returns the created customer.
***400 Bad Request:*** If the request body is invalid.

#### Example Request 
   ```bash
POST /api/customer/add/5

  ```
  #### Example Response
  ``` json
{
        "id": 1,
        "appointmentsActive": [],
        "appointmentsHistory": [],
        "baseUser": {
            "id": 5,
            "email": "dario@email.com",
            "firstName": "FirstName",
            "lastName": "LastName",
            "phoneNumber": "phoneNumber",
            "role": "ROLE_EMPLOYEE"
        }
  }
  ```  

  ### Delete Customer by ID
  #### HTTP Request
  ```bash
  DELETE /api/customers/delete/{id}
  ```
  #### Path parameters
  - `id` (required): The unique identifier of the customer to be deleted.
  #### Response
  ***200 OK:*** Returns the deleted customer.
  ***404 Not Found:*** If the customer could not be deleted.
  #### Example Request 
   ```bash
    DELETE /api/customers/delete/1
  ```
  #### Example Response
``` json
{
        "id": 1,
        "appointmentsActive": [],
        "appointmentsHistory": [],
        "baseUser": {
            "id": 1,
            "email": "dario@email.com",
            "firstName": "FirstName",
            "lastName": "LastName",
            "phoneNumber": "phoneNumber",
            "role": "ROLE_EMPLOYEE"
        }
  }
```
---
The entire code source code is in the main branch of the repository **https://github.com/Clippers-Crew/frizer.mk** 
