package edu.iit.sat.itmd4515.mprasad.service;

import edu.iit.sat.itmd4515.mprasad.domain.*;
import edu.iit.sat.itmd4515.mprasad.security.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.logging.Logger;

/**
 * Service to initialize the database with some initial data.
 * Author: Monisha
 */
@Startup
@Singleton
public class StartupServiceInitDB {

    private static final Logger LOG = Logger.getLogger(StartupServiceInitDB.class.getName());

    @EJB
    private StaffService staffSvc;
    @EJB
    private GrillOrderService orderSvc;
    @EJB
    private OrderDetailsService detailsSvc;
    @EJB
    private CustomerService custSvc;

    // Security realm services
    @EJB
    private UserService userSvc;
    @EJB
    private GroupService groupSvc;

    /**
     *
     */
    public StartupServiceInitDB() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside StartupServiceInitDB.postConstruct()");

        try {
            // Creating Groups
            createGroups();

            // Creating Users and associating Groups
            createAndAssociateUsers();

            // Creating Staff and associating with Users
            Staff s1 = createStaffMember("Mango", "Chef", "3127854625", "staff1@gmail.com");
            Staff s2 = createStaffMember("Apple", "Deputy Chef", "3127854626", "staff2@gmail.com");
            Staff s3 = createStaffMember("Alfonso", "Cook", "3127854627", "staff3@gmail.com");

            // Creating Customers and associating with Users
            Customer c1 = createCustomer("Ben", "2345678908", "ben@gmail.com", "California");
            Customer c2 = createCustomer("Genshin", "1345678908", "genshin@gmail.com", "Chicago");
            Customer c3 = createCustomer("Hyper", "2345678908", "hyper@gmail.com", "Argentina");

            // Associate Customers and Staff with Users
            associateEntitiesWithUsers(s1, s2, s3, c1, c2, c3);

            // Creating Grill Orders and associating with Customers and Menu Items
            createGrillOrder(c1, 2, 11.98, ItemStatus.PROCESSING, s1, MenuItem.TOTS);
            createGrillOrder(c2, 1, 4.99, ItemStatus.FINISHED, s2,  MenuItem.CHEESEBURGER);
            createGrillOrder(c3, 3, 29.97, ItemStatus.QUEUE, s3,  MenuItem.FRENCHFRIES);

            // Demonstrating output
            demonstrateRelationships();

        } catch (Exception e) {
            LOG.severe("Error during startup service initialization: " + e.getMessage());
        }
    }

    // Group creation
    private void createGroups() {
        try {
            Group adminGroup = new Group("ADMIN_GROUP", "Group of admins");
            Group staffGroup = new Group("STAFF_GROUP", "Group of staff");
            Group customerGroup = new Group("CUSTOMER_GROUP", "Group of customers");
            groupSvc.create(adminGroup);
            groupSvc.create(staffGroup);
            groupSvc.create(customerGroup);
            LOG.info("Successfully created user groups.");
        } catch (Exception e) {
            LOG.severe("Error creating groups: " + e.getMessage());
        }
    }

    // User and Group association
    private void createAndAssociateUsers() {
        try {
            createUserWithGroup("customer1", "customer1", "CUSTOMER_GROUP");
            createUserWithGroup("customer2", "customer2", "CUSTOMER_GROUP");
            createUserWithGroup("customer3", "customer3", "CUSTOMER_GROUP");
            createUserWithGroup("staff1", "staff1", "STAFF_GROUP");
            createUserWithGroup("staff2", "staff2", "STAFF_GROUP", "ADMIN_GROUP");
            createUserWithGroup("staff3", "staff3", "STAFF_GROUP");
            createUserWithGroup("admin1", "admin1", "ADMIN_GROUP");
            LOG.info("Successfully created and associated users with groups.");
        } catch (Exception e) {
            LOG.severe("Error creating users: " + e.getMessage());
        }
    }

    //user creation with group
    
    private void createUserWithGroup(String username, String password, String... groups) {
        try {
            User user = new User(username, password);
            for (String group : groups) {
                Group groupEntity = groupSvc.findByName(group);
                if (groupEntity != null) {
                    user.addGroup(groupEntity);
                } else {
                    throw new IllegalArgumentException("Group not found: " + group);
                }
            }
            userSvc.create(user);
            LOG.info("Created user with username: " + username);
        } catch (Exception e) {
            LOG.severe("Error creating user with group: " + e.getMessage());
        }
    }

    // Staff creation
    private Staff createStaffMember(String name, String role, String phoneNumber, String email) {
        Staff staff = new Staff(name, role, phoneNumber, email);
        staffSvc.create(staff);
        LOG.info("Created staff: " + name);
        return staff;
    }

    // Customer creation
    private Customer createCustomer(String name, String phone, String email, String address) {
        Customer customer = new Customer(name, phone, email, address);
        custSvc.create(customer);
        LOG.info("Created customer: " + name);
        return customer;
    }


    // GrillOrder and OrderDetails creation
    private void createGrillOrder(Customer customer, int quantity, double totalPrice, ItemStatus status, Staff staff, MenuItem menuItem) {
        System.out.println("Customer "+ customer+ " Quantity  "+ quantity + totalPrice + status + staff + menuItem);
        GrillOrder order = new GrillOrder(customer, quantity, totalPrice, status, menuItem);
        order.setStaff(staff);
        System.out.println("Order within createGrillOrder" + order);
        orderSvc.create(order);

        OrderDetails orderDetail = new OrderDetails(quantity, totalPrice);
        orderDetail.setOrderDetails(order, staff, customer, menuItem);
        detailsSvc.create(orderDetail);
        System.out.println("orderDetail within createGrillOrder" + orderDetail);

        LOG.info("Created grill order for customer: " + customer.getName());
    }

    // Associations between staff/customers and user
    private void associateEntitiesWithUsers(Staff s1, Staff s2, Staff s3, Customer c1, Customer c2, Customer c3) {
        try {
            // Associate staff with their respective users
            associateUserToStaff("staff1", s1);
            associateUserToStaff("staff2", s2);
            associateUserToStaff("staff3", s3);

            // Associate customers with their respective users
            associateUserToCustomer("customer1", c1);
            associateUserToCustomer("customer2", c2);
            associateUserToCustomer("customer3", c3);

            LOG.info("Successfully associated users with staff and customers.");
        } catch (Exception e) {
            LOG.severe("Error associating entities with users: " + e.getMessage());
        }
    }

    //Associations between user and staff
    private void associateUserToStaff(String username, Staff staff) {
        User user = userSvc.findByUserName(username);
        if (user != null) {
            staff.setUser(user);
            staffSvc.update(staff);
            user.setStaff(staff);
            userSvc.update(user);
            LOG.info("Associated user " + username + " with staff " + staff.getName());
        } else {
            LOG.warning("No user found with username: " + username);
        }
    }

    //Associations between user and customer

    private void associateUserToCustomer(String username, Customer customer) {
        User user = userSvc.findByUserName(username);
        if (user != null) {
            customer.setUser(user);
            custSvc.update(customer);
            user.setCustomer(customer);
            userSvc.update(user);
            LOG.info("Associated user " + username + " with customer " + customer.getName());
        } else {
            LOG.warning("No user found with username: " + username);
        }
    }

    // Demonstrate relationships
    private void demonstrateRelationships() {
        LOG.info("----------------------------------------------------------------------");
        LOG.info("Demonstrating output section for different relationships");
        LOG.info("----------------------------------------------------------------------");

        try {
            for (Staff s : staffSvc.readAll()) {
                LOG.info("-------------------------------------------------------------------------");
                LOG.info(s.toString());
                LOG.info("\t Order Details for Staff >>>>>>>>>>>>>>>>>>");
                for (OrderDetails od : s.getOrderDetails()) {
                    LOG.info("\t" + od.toString());
                }
            }

            for (GrillOrder o : orderSvc.readAll()) {
                LOG.info("-------------------------------------------------------------------------");
                LOG.info(o.toString());
                LOG.info("\t Order Details for Grill Order >>>>>>>>>>>>>>>>>>");
                for (OrderDetails od : o.getOrderDetails()) {
                    LOG.info("\t" + od.toString());
                }
            }

            for (Customer c : custSvc.readAll()) {
                LOG.info("-------------------------------------------------------------------------");
                LOG.info(c.toString());
                LOG.info("\t Order Details for Customer >>>>>>>>>>>>>>>>>>");
                for (OrderDetails od : c.getOrderDetails()) {
                    LOG.info("\t" + od.toString());
                }
            }

        } catch (Exception e) {
            LOG.severe("Error demonstrating relationships: " + e.getMessage());
        }
    }
}
