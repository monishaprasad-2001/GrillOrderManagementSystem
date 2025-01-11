/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.OrderDetails;
import edu.iit.sat.itmd4515.mprasad.service.CustomerService;
import edu.iit.sat.itmd4515.mprasad.service.OrderDetailsService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CustomerController for managing customer interactions in the web layer.
 * Author: Monisha
 */
@Named
@RequestScoped
public class CustomerController {

    @Inject
    private OrderDetailsService orderDetailsService;
    
    
    @Inject
    private FacesContext facesContext;
    
    
    @EJB
    private CustomerService custSvc;

    
    private Customer loggedInCustomer;
    
    private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());

    private Customer customer;

    // Default constructor

    /**
     *
     */
    public CustomerController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside CustomerController.postConstruct()");
        customer = new Customer(); // Initialize customer to prevent null references
    }

    /**
     * Saves the customer using the customer service.
     *
     * @return Navigation outcome string to redirect to confirmation page.
     */
    public String saveCustomer() {
        LOG.info("Inside CustomerController.saveCustomer() - before service call. Customer details: "
                + customer.toString());

        try {
            if (customer != null) {
                custSvc.create(customer);
                LOG.info(
                        "Inside CustomerController.saveCustomer() - after service call. Customer created successfully.");
            } else {
                LOG.warning("Inside CustomerController.saveCustomer() - customer is null.");
                return "error.xhtml"; // Redirect to an error page if customer is null
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception occurred in CustomerController.saveCustomer() - " + e.getMessage(), e);
            return "error.xhtml"; // Redirect to an error page if an exception occurs
        }

        return "createCustomerConfirmation.xhtml?faces-redirect=true"; // Redirect with URL update
    }

    // Getters and Setters

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
    
    /**
     *
     * @return
     * 
     * returns a list of orderDetails associated with a particular customer
     */
    public List<OrderDetails> getLoggedInCustomerOrders() {
        Customer customer = getLoggedInCustomer(); // Get the logged-in customer entity
        if (customer != null) {
            LOG.info("Fetching orders for logged-in customer with ID: " + customer.getId());
            return orderDetailsService.findOrdersByCustomerId(customer.getId()); // Assuming a method exists to find orders by customer ID
        }
        return null;
    }

    // Getter for logged-in customer

    /**
     *
     * @return
     */
    public Customer getLoggedInCustomer() {
        if (loggedInCustomer == null) {
            String username = facesContext.getExternalContext().getRemoteUser(); // Get the current logged-in username
            if (username != null) {
                loggedInCustomer = custSvc.findByUsername(username); // Retrieve customer using the username
            }
        }
        return loggedInCustomer;
    }
}
