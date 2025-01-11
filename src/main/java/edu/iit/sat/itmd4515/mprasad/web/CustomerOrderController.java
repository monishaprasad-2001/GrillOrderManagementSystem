package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.MenuItem;
import edu.iit.sat.itmd4515.mprasad.domain.OrderDetails;
import edu.iit.sat.itmd4515.mprasad.service.OrderDetailsService;
import edu.iit.sat.itmd4515.mprasad.service.CustomerService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller for managing customer orders
 */
@Named
@RequestScoped
public class CustomerOrderController {

    private static final Logger LOG = Logger.getLogger(CustomerOrderController.class.getName());

    @EJB
    CustomerService customerSvc;

    @EJB
    OrderDetailsService orderDetailSvc;

    @Inject
    CustomerWelcomeController cwc;

    private OrderDetails orderDetails;
    private Customer customer;

    /**
     *
     */
    public CustomerOrderController() {
    }

   @PostConstruct
    private void postConstruct() {
    LOG.info("Inside CustomerOrderController.postConstruct()");
    orderDetails = new OrderDetails();
    customer = cwc.getCustomer(); // Ensure this method returns a valid customer
    if (customer == null) {
        LOG.severe("Authenticated customer is null in CustomerOrderController");
        customer = new Customer(); // Fallback to an empty customer if needed
    }
    }


    // Display the details of a specific order related to the authenticated customer

    /**
     *
     * @param od
     * @return
     */
    public String displayOrderDetailsPage(OrderDetails od) {
       if (od != null && od.getCustomer() != null && od.getCustomer().getId().equals(customer.getId())) {
        this.orderDetails = od;
        LOG.info("Inside displayOrderDetailsPage with Order " + orderDetails);
        return "/customer/detailsOrder.xhtml";
    } else {
        LOG.warning("Customer ID mismatch or null values.");
        return "/customer/error.xhtml";
    }
    }

    // Display the page to edit the order, ensuring the customer is the owner of the order

    /**
     *
     * @param od
     * @return
     */
   public String displayEditOrderPage(OrderDetails od) {
    if (od != null && od.getCustomer() != null && od.getCustomer().getId().equals(customer.getId())) {
        if (od.getId() == null) {
            LOG.severe("OrderDetails ID is null. Cannot proceed to edit page.");
            return "/customer/error.xhtml"; // Redirect to an error page
        }
        this.orderDetails = od;
        LOG.info("Inside displayEditOrderPage with Order " + orderDetails);
        return "/customer/editOrder.xhtml";
    } else {
        LOG.warning("Customer ID mismatch or null values.");
        return "/customer/error.xhtml"; // Redirect to an error page
    }
}


    // Display the page to delete the order, ensuring the customer is the owner of the order

    /**
     *
     * @param od
     * @return
     */
    public String displayDeleteOrderPage(OrderDetails od) {
        if (od.getCustomer().getId().equals(customer.getId())) {  // Only allow the authenticated customer to delete their orders
            this.orderDetails = od;
            LOG.info("Inside displayDeleteOrderPage with Order " + orderDetails.toString());
            return "/customer/deleteOrder.xhtml";
        } else {
            LOG.warning("Customer ID mismatch. Unauthorized access attempt.");
            return "/customer/error.xhtml"; // Redirect to an error page if the customer does not own the order
        }
    }

    // Display the page to create a new order for the authenticated customer

    /**
     *
     * @return
     */
    public String displayAddOrderPage() {
        LOG.info("Inside displayAddOrderPage");
        orderDetails = new OrderDetails(); // Reset to a new instance
        System.out.println("displayAddOrderPage "+ orderDetails );
        return "/customer/createOrder.xhtml"; // Navigate to the page for adding a new order
    }

    // Save the new order for the authenticated customer

    /**
     *
     * @return
     */
   public String saveOrder() {
    if (orderDetails == null) {
        LOG.warning("OrderDetails is null while attempting to save.");
        return "/customer/error.xhtml";
    }

    if (orderDetails.getId() != null) {
        LOG.warning("Existing OrderDetails ID found. Resetting to null for new creation.");
        orderDetails.setId(null);
    }

    LOG.info("Saving new OrderDetails for customer with ID: " + customer.getId());
    orderDetailSvc.createOrderForCustomer(orderDetails, customer);
    LOG.info("Order saved successfully: " + orderDetails);
    return "/customer/welcome.xhtml";
}




    // Edit the order for the authenticated customer

    /**
     *
     * @return
     */
    public String editOrder() {
    if (orderDetails.getId() == null) {
        LOG.severe("OrderDetails ID is null. Cannot proceed with edit.");
        return "/customer/error.xhtml"; // Redirect to an error page
    }
    if (orderDetails.getCustomer() == null) {
        orderDetails.setCustomer(customer); // Set the customer if not already set
    }
    LOG.info("Inside CustomerOrderController.editOrder() with order " + orderDetails);
    orderDetailSvc.editOrderForCustomer(orderDetails, customer); // Call the service method
    return "/customer/welcome.xhtml"; // Redirect to the customer welcome page
}


    // Delete the order for the authenticated customer

    /**
     *
     * @return
     */
    public String deleteOrder() {
         if (orderDetails.getCustomer() == null) {
        orderDetails.setCustomer(customer); // Ensure customer is set
    }
    LOG.info("Inside deleteOrder with order " + orderDetails);
    orderDetailSvc.deleteOrderForCustomer(orderDetails, customer);
    return "/customer/welcome.xhtml";
    }

    // Getter and Setter for orderDetails

    /**
     *
     * @return
     */
    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    /**
     *
     * @param orderDetails
     */
    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    /**
     *
     * @return
     */
    public List<MenuItem> getMenuItems() {
        // Return the list of enum values
        return Arrays.asList(MenuItem.values());
    }

    /**
     *
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
