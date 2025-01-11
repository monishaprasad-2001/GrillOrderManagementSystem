package edu.iit.sat.itmd4515.mprasad.service;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.OrderDetails;
import edu.iit.sat.itmd4515.mprasad.domain.Staff;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service for OrderDetails entity operations.
 * Author: Monisha
 */
@Stateless
public class OrderDetailsService extends AbstractService<OrderDetails> {

    private static final Logger LOG = Logger.getLogger(OrderDetailsService.class.getName());

    /**
     *
     */
    public OrderDetailsService() {
        super(OrderDetails.class);
    }

    /**
     * Method to retrieve all OrderDetails entities.
     * 
     * @return List of all OrderDetails entities.
     */
    public List<OrderDetails> readAll() {
        return super.readAll("OrderDetails.readAll");
    }

    /**
     * Method to find an OrderDetails entity by its ID.
     * 
     * @param id The ID of the OrderDetails entity to retrieve.
     * @return The OrderDetails entity, or null if not found.
     */
    public OrderDetails findById(Long id) {
        LOG.info("Finding OrderDetails by ID: " + id);
        return read(id);
    }

    /**
     * Method to add new OrderDetails to the database.
     * 
     * @param orderDetails The OrderDetails entity to add.
     */
    public void addOrderDetails(OrderDetails orderDetails) {
        LOG.info("Adding new OrderDetails for GrillOrder ID: " + (orderDetails.getGrillOrder() != null ? orderDetails.getGrillOrder().getId() : "null"));
        create(orderDetails);
    }

    /**
     * Method to update an existing OrderDetails entity in the database.
     * 
     * @param orderDetails The OrderDetails entity to update.
     */
    public void updateOrderDetails(OrderDetails orderDetails) {
        LOG.info("Updating OrderDetails with ID: " + orderDetails.getId());
        update(orderDetails);
    }

    /**
     * Method to delete an OrderDetails entity from the database.
     * 
     * @param orderDetailsId The ID of the OrderDetails entity to delete.
     */
    public void deleteOrderDetails(Long orderDetailsId) {
        LOG.info("Deleting OrderDetails with ID: " + orderDetailsId);
        OrderDetails orderDetails = read(orderDetailsId);
        if (orderDetails != null) {
            delete(orderDetails);
        } else {
            LOG.warning("No OrderDetails found with ID: " + orderDetailsId);
        }
    }

    /**
     * Method to find all OrderDetails entities for a specific GrillOrder.
     * 
     * @param grillOrderId The ID of the GrillOrder to retrieve details for.
     * @return List of OrderDetails entities for the given GrillOrder.
     */
    public List<OrderDetails> findByGrillOrderId(Long grillOrderId) {
        LOG.info("Finding OrderDetails for GrillOrder ID: " + grillOrderId);
        TypedQuery<OrderDetails> query = em.createQuery("SELECT od FROM OrderDetails od WHERE od.grillOrder.id = :grillOrderId", OrderDetails.class);
        query.setParameter("grillOrderId", grillOrderId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            LOG.warning("No OrderDetails found for GrillOrder ID: " + grillOrderId);
            return null;
        }
    }
    
    /**
     * Method to edit the order associated with an authenticated staff
     * @param orderDetails
     * @param staff
     */
    public void editOrderForAuthenticatedStaff(OrderDetails orderDetails, Staff staff) {
    
        LOG.info("Inside editOrderForAuthenticatedStaff with orderDetails " + orderDetails.toString() + "Order details.getID " + orderDetails.getId());

        
        OrderDetails managedRef = em.getReference(OrderDetails.class, orderDetails.getId());
        
        managedRef.setQuantity(orderDetails.getQuantity());
        managedRef.setPrice(orderDetails.getPrice());
//        managedCust.setCustomer(customer);
        
        em.merge(managedRef);
    }
    
    /**
     *Method to find orders associated with a particular customer. Returns a list of OrderDetails
     * 
     * @param customerId
     * @return
     */
    public List<OrderDetails> findOrdersByCustomerId(Long customerId) {
        LOG.info("Finding orders for Customer with ID: " + customerId);
        TypedQuery<OrderDetails> query = em.createQuery("SELECT od FROM OrderDetails od WHERE od.customer.id = :customerId", OrderDetails.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
    
    /**
     * Method to create an order for a customer
     *
     * @param orderDetails
     * @param customer
     */
    public void createOrderForCustomer(OrderDetails orderDetails, Customer customer) {
    LOG.info("Adding new OrderDetails for Customer ID: " + (customer != null ? customer.getId() : "null") + ", OrderDetails: " + orderDetails.toString());
    
    // Associate the OrderDetails with the Customer
    orderDetails.setCustomer(customer);
    
    // Persist the new OrderDetails in the database
    create(orderDetails);
}

    /**
     * Method to edit an order by the customer 
     *
     * @param orderDetails
     * @param customer
     */
    public void editOrderForCustomer(OrderDetails orderDetails, Customer customer) {
    if (orderDetails == null || orderDetails.getId() == null) {
        throw new IllegalArgumentException("OrderDetails or its ID cannot be null");
    }
    if (customer == null || customer.getId() == null) {
        throw new IllegalArgumentException("Customer or its ID cannot be null");
    }

    LOG.info("Customer: " + customer);
    LOG.info("Inside editOrderForCustomer with OrderDetails: " + orderDetails + " for Customer ID: " + customer.getId());

    if (orderDetails.getCustomer() == null || !orderDetails.getCustomer().getId().equals(customer.getId())) {
        LOG.warning("Customer ID mismatch or invalid customer in OrderDetails.");
        throw new SecurityException("Unauthorized access to edit this order.");
    }

    try {
        // Get a managed reference to the order
        OrderDetails managedRef = em.getReference(OrderDetails.class, orderDetails.getId());

        // Log the changes
        LOG.info("Updating OrderDetails: ID=" + managedRef.getId() +
                 ", Old Quantity=" + managedRef.getQuantity() + ", New Quantity=" + orderDetails.getQuantity() +
                 ", Old Price=" + managedRef.getPrice() + ", New Price=" + orderDetails.getPrice());

        // Update the necessary fields
        managedRef.setQuantity(orderDetails.getQuantity());
        managedRef.setPrice(orderDetails.getPrice());

        // Merge the updated order back into the persistence context
        em.merge(managedRef);
        LOG.info("Order updated successfully: " + orderDetails);
    } catch (IllegalArgumentException ex) {
        LOG.warning("OrderDetails with ID " + orderDetails.getId() + " not found.");
        throw new IllegalArgumentException("Order not found for editing", ex);
    }
}

    /**
     *Method to delete an order by the authenticated staff
     * 
     * @param orderDetails
     * @param staff
     */
    public void deleteOrderForAuthenticatedStaff(OrderDetails orderDetails,Staff staff) {
    
        LOG.info("Inside deleteOrderForAuthenticatedStaff with orderDetails " + orderDetails.toString() + "Order details.getID " + orderDetails.getId());

        
        Staff managedStaffRef = em.getReference(Staff.class, staff.getId());
       OrderDetails managedOrderRef = em.getReference(OrderDetails.class, orderDetails.getId());
       
       
       managedStaffRef.removeOrderDetails(managedOrderRef);
        em.merge(managedStaffRef);
        
        em.remove(managedOrderRef);
    }
    
    /**
     * Method to delete an order by the customer
     * 
     * @param orderDetails
     * @param customer
     */
    public void deleteOrderForCustomer(OrderDetails orderDetails, Customer customer) {
    LOG.info("Inside deleteOrderForCustomer with OrderDetails: " + orderDetails.toString() + " for OrderID: " + orderDetails.getId());
        
        Customer managedCustRef = em.getReference(Customer.class, customer.getId());
       OrderDetails managedOrderRef = em.getReference(OrderDetails.class, orderDetails.getId());
       
       managedCustRef.removeOrderDetail(managedOrderRef);
        em.merge(managedCustRef);
        
        em.remove(managedOrderRef);
    }
}
