/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.domain.Staff;
import edu.iit.sat.itmd4515.mprasad.security.User;
import edu.iit.sat.itmd4515.mprasad.security.UserService;
import jakarta.inject.Named;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author Monisha
 */
@Named
@RequestScoped
public class NewStaffAdditionController {

    private static final Logger LOG = Logger.getLogger(NewStaffAdditionController.class.getName());

    
        private Staff staff;
        
        @EJB UserService usvc;
        
    /**
     *
     */
    public NewStaffAdditionController() {
        }

         @PostConstruct
    private void PostConstruct(){
        LOG.info("Inside NewStaffAdditionController PostConstruct");
        
        staff = new Staff();
        staff.setUser(new User());
    }
        
    /**
     *
     * @return
     */
    public String executeAddNewStaffClick(){
        LOG.info(this.staff.toString());
        
        usvc.addNewStaffUser(this.staff);
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
    
    
    /**
     * Get the value of staff
     *
     * @return the value of staff
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     * Set the value of staff
     *
     * @param staff new value of staff
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    
}
