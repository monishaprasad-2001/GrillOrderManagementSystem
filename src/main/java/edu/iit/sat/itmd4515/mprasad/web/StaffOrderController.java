/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.GrillOrder;
import edu.iit.sat.itmd4515.mprasad.domain.MenuItem;
import edu.iit.sat.itmd4515.mprasad.domain.OrderDetails;
import edu.iit.sat.itmd4515.mprasad.service.OrderDetailsService;
import edu.iit.sat.itmd4515.mprasad.service.StaffService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Staff Order Controller
 * @author monisha
 */
@Named
@RequestScoped
public class StaffOrderController {

    private static final Logger LOG = Logger.getLogger(StaffOrderController.class.getName());

    @EJB
    StaffService staffSvc;
    @EJB
    OrderDetailsService orderDetailSvc;
    @Inject
    StaffWelcomeController swc;
    @Inject
    CustomerController cc;

    private OrderDetails orderDetails;
    private Customer customer;

    /**
     *
     */
    public StaffOrderController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside OrderControlle.postConstruct()");
        orderDetails = new OrderDetails();
        customer = new Customer();
    }

    /**
     *
     * @param od
     * @return
     * responsible to redirect to the orderDetails Page
     */
    public String displayOrderDetailsPage(OrderDetails od) {
        this.orderDetails = od;
        LOG.info("Inside displayOrderDetailsPage with Order " + orderDetails.toString());
        return "/staff/detailsOrder.xhtml";
    }

    /**
     *
     * @param od
     * @return
     * responsible to redirect to the edit orderDetails Page
     */
    public String displayEditOrderPage(OrderDetails od) {
        this.orderDetails = od;

        LOG.info("Inside displayOrderDetailsPage with Order " + orderDetails.toString());
        return "/staff/editOrder.xhtml";
    }

    /**
     *
     * @param od
     * @return
     * responsible to redirect to the delete orderDetails Page
     */
    public String displayDeleteOrderPage(OrderDetails od) {
        this.orderDetails = od;

        LOG.info("Inside displayOrderDetailsPage with Order " + orderDetails.toString());
        return "/staff/deleteOrder.xhtml";
    }

    /**
     *
     * @return
     * responsible to redirect to the display add orders Page
     */
    public String displayAddOrderPage() {
        LOG.info("Inside displayOrderDetailsPage");
        return "/staff/createOrder.xhtml";
    }

    /**
     *
     * Method to save an order
     * @return
     */
    public String saveOrder() {
        LOG.info("Inside StaffOrderController.saveOrder() before call to service" + orderDetails.toString() + "with customer " + cc.getCustomer().toString());
        staffSvc.createOrderForAuthenticatedStaff(orderDetails, swc.getStaff(), cc.getCustomer());
        LOG.info("Inside StaffOrderController.saveOrderDetails after call to service " + orderDetails.toString() + cc.getCustomer().toString());
        swc.refreshStaffModel();
        return "/staff/welcome.xhtml";
    }

    /**
     *Method to edit an order
     * @return
     */
    public String editOrder() {
        LOG.info("Inside StaffOrderController.editOrder() before call to service" + orderDetails.toString());
        orderDetailSvc.editOrderForAuthenticatedStaff(orderDetails,swc.getStaff());
        LOG.info("Inside StaffOrderController.editOrder after call to service " + orderDetails.toString());
        swc.refreshStaffModel();
        return "/staff/welcome.xhtml";
    }
    
    /**
     *Method to delete an order
     * @return
     */
    public String deleteOrder() {
        LOG.info("Inside StaffOrderController.deleteOrder() before call to service" + orderDetails.toString() + "with customer " + cc.getCustomer().toString());
        orderDetailSvc.deleteOrderForAuthenticatedStaff(orderDetails, swc.getStaff());
        LOG.info("Inside StaffOrderController.deleteOrder() after call to service " + orderDetails.toString() + cc.getCustomer().toString());
        swc.refreshStaffModel();
        return "/staff/welcome.xhtml";
    }
    
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
}
