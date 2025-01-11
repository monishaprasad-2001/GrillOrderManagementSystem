/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.security.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import static jakarta.security.enterprise.AuthenticationStatus.NOT_DONE;
import static jakarta.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static jakarta.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static jakarta.security.enterprise.AuthenticationStatus.SUCCESS;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sas
 */
@Named
@RequestScoped
public class LoginController {

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    @Inject
    FacesContext facesContext;
    @Inject
    SecurityContext securityContext;

    // Model
    private User user;

    /**
     *
     */
    public LoginController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside LoginController.postConstruct()");
        user = new User();
    }

    // helper method

    /**
     *
     * @return
     */
    public String getAuthenticatedUsername(){
        LOG.info("Entered");
        return securityContext.getCallerPrincipal().getName();
         
    }
    
    /**
     *
     * @return
     */
    public boolean isStaff(){
        return securityContext.isCallerInRole("STAFF_ROLE");
    }

    /**
     *
     * @return
     */
    public boolean isCustomer(){
        return securityContext.isCallerInRole("CUSTOMER_ROLE");
    }

    /**
     *
     * @return
     */
    public boolean isAdmin(){
        return securityContext.isCallerInRole("ADMIN_ROLE");
    }
    
    /**
     *
     * @return
     */
    public String doLogin() {

    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    // Create credentials from user input
    Credential cred = new UsernamePasswordCredential(this.user.getUserName(), new Password(this.user.getPassword()));

    // Authenticate the user
    AuthenticationStatus status = securityContext.authenticate(request, response, AuthenticationParameters.withParams().credential(cred));

    switch (status) {
        case SUCCESS:
            LOG.info(status.toString());

            // Redirect based on the role
            if (isAdmin()) {
                return "/admin/welcome.xhtml?faces-redirect=true";  // Admin landing page
            } else if (isCustomer()) {
                return "/customer/welcome.xhtml?faces-redirect=true";  // Customer landing page
            } else if (isStaff()) {
                return "/staff/welcome.xhtml?faces-redirect=true";  // Staff landing page
            } else {
                return "/error.xhtml";  // No role found, return to error page
            }

        case SEND_FAILURE:
            LOG.info("FAILURE! " + status.toString());
            return "/error.xhtml";  // Failure page

        case NOT_DONE:
            LOG.info("NOT DONE! " + status.toString());
            return "/error.xhtml";  // Error page

        case SEND_CONTINUE:
            LOG.info(status.toString());
            break;
    }

    return null;  // In case none of the above cases are hit (just in case)
}

    // action methods
//    public String doLogin() {
//
//        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
//        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//
//        Credential cred = new UsernamePasswordCredential(this.user.getUserName(), new Password(this.user.getPassword()));
//
//        AuthenticationStatus status = securityContext.authenticate(request, response, AuthenticationParameters.withParams().credential(cred));
//
//        switch(status){
//            case SUCCESS:
//                LOG.info(status.toString());
//                break;
//            case SEND_FAILURE:
//                LOG.info("FAILURE! " + status.toString());
//                return "/error.xhtml";
//            case NOT_DONE:
//                LOG.info("NOT DONE! " + status.toString());
//                return "/error.xhtml";
//            case SEND_CONTINUE:
//                LOG.info(status.toString());
//                break;
//        }
//        
//        return "/welcome.xhtml?faces-redirect=true";
//    }

    /**
     *
     * @return
     */

    public String doLogout() {
        LOG.info("loginController.doLogout()");
        
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        return "/login.xhtml";
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }
}