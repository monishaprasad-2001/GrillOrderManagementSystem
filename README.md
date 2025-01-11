# GrillOrderManagementSystem
The Grill Order Management System is a streamlined application designed to manage customer orders efficiently in a grill or restaurant environment. It facilitates seamless order processing, staff coordination, and customer satisfaction through an intuitive interface and robust backend.

## GRILL ORDER MANAGEMENT SYSTEM
`Name: Monisha Prasad 
CWID: A20575867`
# 
## Project Summary
I created the Grill Order Management System, which has 3 main entities: Customer, Staff, and OrderDetails. The system allows customers to manage their grill orders, staff members to oversee operations, and an admin to manage users and menu items.

## Structure:
The project follows the MVC design pattern and consists of the following packages:

## ER Diagram

![Screenshot (10)](https://github.com/user-attachments/assets/5bbdaa0f-684e-4c14-98f4-de7bbe0633cd)

## Security:
This package handles user authentication and authorization. It includes the User, Group, and related services (UserService) to assign roles such as Customer, Staff, and Admin.

## Domain:
This package serves as the Model in the MVC pattern. It contains entities such as:

**Customer**: Represents users who place orders.

**Staff**: Represents users who process orders (e.g., chefs, delivery personnel).

**OrderDetails**: Stores details about orders, such as quantity and price.

**MenuItem**: Enum representing the available items on the grill menu.

**AbstractEntity**: A superclass containing common properties like id, createdTimestamp, and updatedTimestamp.

## Service:
The Service Layer performs database operations. It includes:

**CustomerService**: Manages customers and their associated orders.

**StaffService**: Handles staff operations like adding or removing employees.

**OrderDetailsService**: Manages order details like creation, modification, and deletion.

**Web**:
The Controller Layer contains managed beans (e.g., CustomerController, StaffController) to handle user interactions and manage page navigation.

**Web Pages**:
This folder contains JSF-based XHTML pages that serve as the View for the application. Pages are divided into:

**Customer pages**: For customers to view, edit, or delete their orders.
**Staff pages**: For staff to manage customer orders and view details.
**Admin pages**: For administrators to manage customers, staff, and system data.

## Config and Resources:
These packages handle database connectivity, configuration files, and utility classes.

## Design:
When users access the system, they are presented with a Login Page where they provide their credentials. Based on their role, they are redirected to their respective dashboards, which are dynamically customized for their permissions using the authNtemplate.xhtml.

## Workflow:
**Case 1: Customer Access**
**Login**: The user provides credentials (username and password) on the login page.
Upon successful authentication, the system determines the role (Customer) and redirects the user to their Customer Dashboard.

**Customer Dashboard**: The dashboard is dynamically customized for customers. 
It displays:
- A list of past orders associated with the logged-in customer.
- Buttons to view details, edit, or delete an existing order.
- An option to add a new order.
- The navigation bar only shows options relevant to customers (e.g., "My Orders").
- Order Actions:
  
 **View Details**: Clicking the "Details" button takes the customer to orderDetails.xhtml, displaying read-only information about the selected order.
  
**Edit Order**: Clicking "Edit" redirects the customer to editOrder.xhtml, where they can modify the order's quantity or menu items.

**Delete Order**: Clicking "Delete" removes the order from the database after confirmation, and the customer is redirected back to the dashboard.

**Add New Order**: Clicking the "Add New Order" button redirects the customer to createOrder.xhtml.

The form includes fields for order details (e.g., menu items, quantity).

**After submission**: Validation ensures all required fields are completed correctly.
The new order is saved in the database, and the user is redirected back to the dashboard, where the new order appears in the list.

**Case 2: Staff Access**
**Login**: The user logs in with credentials, and the system identifies the role as Staff.
Upon authentication, the staff member is redirected to their Staff Dashboard.

**Staff Dashboard**:
The dashboard displays:
- A list of customer orders assigned to the logged-in staff member.
- Buttons to view, edit, or delete orders.
- An option to create a new order on behalf of a customer.
- The navigation bar includes staff-specific options (e.g., "Order Management").

**Order Actions**:

**View Order Details**: Clicking "Details" shows detailed information about the selected order (orderDetails.xhtml). The staff member can only view the details here.

**Edit Order**: Clicking "Edit" redirects the staff member to editOrder.xhtml, allowing them to adjust the order's details, such as quantity or menu items.

**Delete Order**: Clicking "Delete" removes the order from the system after confirmation and updates the list on the dashboard.

**Add New Order**: Clicking "Add New Order" takes the staff member to createOrder.xhtml.

The form allows the staff member to input order details and associate the order with a specific customer.
Upon successful submission:

- The order is saved to the database.
- The staff member is redirected back to the dashboard, where the new order is displayed.

**Case 3: Admin Access**
**Login**: The admin logs in, and the system identifies the role as Admin.
Upon authentication, the admin is redirected to the Admin Dashboard.

**Admin Dashboard**: 

The dashboard displays two sections:
**Customer Management**:

- A table lists all registered customers with details like ID, name, email, and phone number.
- Buttons for each customer allow the admin to view details, edit information, or delete the customer.
- An "Add Customer" button allows the admin to create a new customer profile.

**Staff Management**:

A similar table lists all staff members with buttons to view, edit, or delete staff profiles.
- An "Add Staff" button allows the admin to create a new staff profile.
- The navigation bar provides options for both customer and staff management.

**Customer Actions**:

**View Details**: Clicking the "Details" button redirects to customerDetails.xhtml, displaying the customer’s full information (read-only).

**Edit Customer**: Clicking "Edit" redirects to editCustomer.xhtml, where the admin can update the customer’s details (e.g., name, email).

**Delete Customer**: Clicking "Delete" removes the customer from the system after confirmation.


**Staff Actions**:

**View Details**: Similar to customer management, clicking "Details" shows read-only information about a staff member.

**Edit Staff**: Clicking "Edit" redirects to editStaff.xhtml, where the admin can update the staff member’s role, phone number, or email.

**Delete Staff**: Clicking "Delete" removes the staff member’s record from the database.


**Add New User (Customer or Staff)**:

Clicking "Add Customer" or "Add Staff" opens a form page (createCustomer.xhtml or createStaff.xhtml).
The form collects required details like name, role, and contact information.
Upon submission:
- Validation ensures all fields are complete and valid.
- The new user is saved in the database.
- The admin is redirected back to the dashboard, where the new user is displayed in the relevant list.

# Project Setup Guide

## **Requirements (Installation, Compile, Runtime, Database, etc.)**

Follow these step-by-step instructions to install, build, and run the project. This guide assumes no prior knowledge of the tools or environment.

---

### **1. Prerequisites**
Before proceeding, ensure the following tools are installed:

#### **Required Tools**
- **NetBeans IDE**
  - Version: NetBeans 15 or later
  

- **Java Development Kit (JDK)**
  - Version: JDK 17.0.12 or later


- **Build Tool**
  - Maven
  - Version: Maven 3.x
 

- **Database**
  - MySQL Server
  - Version: MySQL 9.0.1 or later
 

- **Application Server**
  - Payara Server 6.0

#### **Optional Tools**
- **Browser**
  - Google Chrome or Mozilla Firefox for testing.

---

### **2. Installation**

#### **Step 1: Set Up NetBeans IDE**
1. Install NetBeans IDE from the official website.
2. Configure NetBeans to use the installed JDK:
   - Go to **Tools > Options > Java > Java Platforms** and add your JDK installation path.
3. Install the Payara Tools plugin for NetBeans:
   - Go to **Tools > Plugins > Available Plugins**.
   - Search for "Payara" and install the plugin.

#### **Step 2: Install and Configure MySQL**
1. Install MySQL Server and start the service.
2. Create a new database:
   ```sql
   CREATE DATABASE itmd515;
   ```
3. Create a user and grant privileges (optional):
   ```sql
   CREATE USER 'itmd515'@'localhost;
   GRANT ALL PRIVILEGES ON itmd515.* TO 'itmd515'@'localhost';
   ```

#### **Step 3: Install and Configure Payara Server**
1. Download and extract Payara Server 6.0.
2. Start the domain server:
   ```bash
   cd payara6/bin
   ./asadmin start-domain
   ```
3. Add the server to NetBeans:
   - Go to **Tools > Servers > Add Server**.
   - Select **Payara Server** and provide the installation path.

#### **Step 4: Configure the Project**
1. Open NetBeans and create a new Maven project.
   - Select **Java Web > Web Application**.
   - Choose **Maven** as the build tool.
2. Add the following dependencies to your `pom.xml`:
   ```xml
   <dependencies>
       <dependency>
           <groupId>jakarta.platform</groupId>
           <artifactId>jakarta.jakartaee-api</artifactId>
           <version>${jakartaee}</version>
           <scope>provided</scope>
       </dependency>
       <dependency>
         <groupId>com.mysql</groupId>
           <artifactId>mysql-connector-j</artifactId>
           <version>8.3.0</version>
           <scope>provided</scope>
       </dependency>
       <dependency>
           <groupId>org.eclipse.persistence</groupId>
           <artifactId>org.eclipse.persistence.jpa</artifactId>
           <version>4.0.4</version>
       </dependency>
   </dependencies>
   ```
3. Configure the database connection in `persistence.xml` (if using JPA):
   ```xml
   <persistence>
       <persistence-unit name="itmd4515PU" transaction-type="JTA">
    <jta-data-source>java:app/jdbc/itmd4515DS</jta-data-source>
    <exclude-unlisted-classes>false</exclude-unlisted-classes>
    <properties>
      <property name="jakarta.persistence.schema-generation.database.action" value="drop-and-create"/>
    </properties>
    </persistence-unit>
   </persistence>
   ```

4. Define the JDBC connection pool in Payara:
   - Access the Payara Admin Console at `http://localhost:4848`.
   - Navigate to **Resources > JDBC > Connection Pools** and create a new pool.
   - Add a JDBC resource under **JDBC > JDBC Resources** linked to your connection pool.

---

### **3. Compilation**
1. open the Netbean right-click on project and build it for compilation.
---
### **4. Runtime**
1. open the netbean click on run button to run the project.

2. Access the deployed application in your browser:
   ```
   http://localhost:8080/itmd515-f24-fp/
   ```
---

### **5. Tools, Libraries, and APIs**

| Tool/Library/API          | Version   | Purpose                           |
|---------------------------|-----------|-----------------------------------|
| NetBeans                  | 15        | Integrated Development Environment |
| JDK                       | 17.0.12   | Java compilation and runtime      |
| Maven                     | 3.x       | Build automation tool             |
| Payara Server             | 6.0       | Application server                |
| MySQL                     | 9.0.1     | Relational database management    |
| JSF (JavaServer Faces)    | 2.3.9     | Frontend framework                |
---

## **Screen Capture**
1. Login Page
   
   ![image](https://github.com/user-attachments/assets/015dab2f-fd3e-45c2-819d-66fe3dc022c9)
   
2. If Login fails.
   
   ![image](https://github.com/user-attachments/assets/d03bf9a6-9d8c-4786-b241-470d114b879f)

3. Welcome page of customer(username: customer1 password: customer1)
   
  ![image](https://github.com/user-attachments/assets/4ae91473-2835-464d-a142-d7490d533300)

4. When click on add New Order button
   
![image](https://github.com/user-attachments/assets/0ebdc29a-6b58-4a29-92c5-70309fbbc6c8)

5. New Order created
    
   ![image](https://github.com/user-attachments/assets/ca47e6fd-7caa-4d80-86d1-7af965857593)

6. Edit orderpage
    
  ![image](https://github.com/user-attachments/assets/5de2b218-df1f-4ee7-857e-3ff028573ba0)

7. After editing the order
    
    ![image](https://github.com/user-attachments/assets/c2aa998d-00c9-491e-be56-29fd3c15a40a)

8. After clicking on view details 
    
    ![image](https://github.com/user-attachments/assets/f1a32a94-9e94-4f40-94dc-c8564a1fa80b)

9. Delete order page
    
    ![image](https://github.com/user-attachments/assets/7d2f9ef3-c323-4d25-a0a7-598ae490cf2d)

10. After deleting the order

    ![image](https://github.com/user-attachments/assets/7275561a-3a3d-47a6-80a6-227687dcab0e)

11. Logging in as staff with admin privileges(username:staff2, password: staff2)

    ![image](https://github.com/user-attachments/assets/a6c1b6dd-e026-42ef-b453-64672ef3baa1)

12. Staff with admin privileges welcome page
    
  ![image](https://github.com/user-attachments/assets/4f0af526-36f4-4140-adf7-22d482ad8796)

13. Add new customer page
    
 ![image](https://github.com/user-attachments/assets/518163bb-121e-41cb-9521-ddd1032b2e84)

14. After adding the customer
    
 ![image](https://github.com/user-attachments/assets/1356da50-153b-4547-81f5-60c1fa89842e)

15. Add new staff page
    
    ![image](https://github.com/user-attachments/assets/d3f013af-d407-497e-b64d-580f8f4035f9)

16. New staff added
    
   ![image](https://github.com/user-attachments/assets/d1676e8f-4213-49c8-9590-5a402e2cad0e)

17.staff2 with staff access showing orders associated with him

  ![image](https://github.com/user-attachments/assets/06e2709b-9752-4db3-9fd0-ad7f770a8020)

18. Order details page
    
    ![image](https://github.com/user-attachments/assets/b22da054-33ef-4cbf-a10c-d316dd2f13dd)

19. Edit order page
    
    ![image](https://github.com/user-attachments/assets/2b2e8ca5-7fe7-4575-98dd-557e2dabd8af)

20. After editing the order
    
    ![image](https://github.com/user-attachments/assets/f704e133-07e0-4e13-95f3-c0ad9819aa30)

21. Add new order page
    
    ![image](https://github.com/user-attachments/assets/4ef30d42-6591-47f1-b495-2d41a0c3f054)

22. After creating another order
    
    ![image](https://github.com/user-attachments/assets/2acc0460-be33-4b4f-a6ef-7a3aeef52489)

23. Delete order page
    
    ![image](https://github.com/user-attachments/assets/62291777-a5e7-4d58-8354-d302f2c6f2f3)

24. After deleting the order
    
    ![image](https://github.com/user-attachments/assets/f2b88ed0-6c54-4682-8ca2-a6480f5a2ee1)
    
25. Logging in ad admin(username: admin1, password: admin1)
    
    ![image](https://github.com/user-attachments/assets/226e838f-f731-4892-b058-dd9f9eddd02f)

26. Admin welcome page
    
    ![image](https://github.com/user-attachments/assets/7b18077a-2e90-49bb-871f-cd9e1588d366)


## working test script

1. Customer credentials
  - username : customer1 
  - Password: customer1

2. Staff credential
   - username: staff2
   - passowrd: staff2
     or
   - username: staff1
   - password: staff1
   
4. Admin Credential
   - username: admin1
   - password: admin1

## Project Insight
###  What I learn In this?
Through this project, I learned to design and implement a robust web application using the MVC architecture. I gained hands-on experience with Jakarta EE technologies, including EJB for business logic, JPA for database interactions, and Facelets for creating dynamic web pages. I explored role-based access control (RBAC), ensuring secure navigation tailored to Admin, Staff, and Customer roles. I enhanced my understanding of form validation, database transactions, and lifecycle events like @PostConstruct. Working with dynamic workflows taught me UI design principles and integration testing. Additionally, I learned to manage timestamps, build reusable templates, and ensure code scalability and maintainability.

### Was there something you would like to explore further?
Yes, I would like to explore the following further:
**Advanced Security Mechanisms:** Implementing OAuth2 or JWT for more secure authentication and authorization instead of basic role-based access control.
**Microservices Architecture:** Breaking the application into independent microservices to explore distributed systems and scalability.
**CI/CD Pipelines:** Automating builds, tests, and deployments using tools like Jenkins, Docker, and Kubernetes.
**Performance Optimization:** Exploring caching mechanisms like Redis and optimizing database queries for high-performance applications.
**Integration with Frontend Frameworks:** Using frameworks like React or Angular for enhanced user experiences and real-time updates.
**Testing Strategies:** Expanding test coverage with unit, integration, and end-to-end testing using tools like JUnit and Selenium.

### What I Liked:
**Comprehensive Workflow:** The project allowed me to design and implement a complete workflow, integrating multiple entities and role-based functionalities, which deepened my understanding of full-stack development.

**Role-Based Access Control:** Implementing access control for Admin, Staff, and Customers helped me appreciate the importance of secure and segregated functionality.

**Database Integration:** Working with JPA annotations and persistence in the domain layer enhanced my skills in ORM and database management.

**MVC Architecture:** Developing a well-structured application using the MVC pattern reinforced best practices in software design.

**Problem Solving:** Debugging and troubleshooting issues like entity validation and navigation honed my analytical and problem-solving abilities.
