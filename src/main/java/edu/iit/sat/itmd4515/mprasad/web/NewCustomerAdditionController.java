/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.security.User;
import edu.iit.sat.itmd4515.mprasad.security.UserService;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 *
 * @author Monisha
 */
@Named
@RequestScoped
public class NewCustomerAdditionController {
    
    private static final Logger LOG = Logger.getLogger(NewCustomerAdditionController.class.getName());

    //Add model
    private Customer customer;
    
    @EJB UserService usvc;
    
    /**
     *
     */
    public NewCustomerAdditionController() {
    }
    
    @PostConstruct
    private void PostConstruct() {
        LOG.info("Inside NewCustomerAdditionController PostConstruct");
        
        customer = new Customer();
        customer.setUser(new User());
    }
    
    /**
     *
     * @return
     */
    public String executeAddNewCustomerClick() {
        LOG.info(this.customer.toString());
        
        usvc.addNewCustomerUser(this.customer);
        return "/admin/welcome.xhtml?faces-redirect=true";
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
