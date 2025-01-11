/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.domain.Staff;
import edu.iit.sat.itmd4515.mprasad.service.StaffService;
import edu.iit.sat.itmd4515.mprasad.web.LoginController;
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
public class StaffWelcomeController {

    private static final Logger LOG = Logger.getLogger(StaffWelcomeController.class.getName());

    // this is the owner model for the welcome page
    private Staff staff;

    @Inject LoginController loginController;
    @EJB StaffService staffSvc;
    
    /**
     *
     */
    public StaffWelcomeController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside StaffWelcomeController.postConstruct with " + loginController.getAuthenticatedUsername() );
        staff = staffSvc.findByUsername(loginController.getAuthenticatedUsername());
        LOG.info("Inside StaffWelcomeController.postConstruct with " + staff.toString() );
    }

    // a method like this can help you refresh collections after operations

    /**
     *
     */
    public void refreshStaffModel(){
        staff = staffSvc.findByUsername(loginController.getAuthenticatedUsername());
    }
    
    /**
     *
     * @return
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     *
     * @param staff
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
