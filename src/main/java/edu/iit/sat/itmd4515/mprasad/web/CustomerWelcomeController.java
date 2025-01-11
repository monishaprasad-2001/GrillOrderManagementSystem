package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.service.CustomerService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.logging.Logger;

/**
 *
 * @author sas
 */
@Named
@RequestScoped
public class CustomerWelcomeController {

    private static final Logger LOG = Logger.getLogger(CustomerWelcomeController.class.getName());

    // This is the owner model for the welcome page
    private Customer customer;

    @Inject
    LoginController loginController;

    @EJB
    CustomerService customerSvc;

    /**
     *
     */
    public CustomerWelcomeController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside CustomerWelcomeController.postConstruct with " + loginController.getAuthenticatedUsername());
        customer = customerSvc.findByUsername(loginController.getAuthenticatedUsername());
        LOG.info("Inside CustomerWelcomeController.postConstruct with " + customer.toString());
    }

    // A method to refresh the customer model after operations

    /**
     *
     */
    public void refreshCustomerModel() {
        customer = customerSvc.findByUsername(loginController.getAuthenticatedUsername());
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
