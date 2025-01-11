package edu.iit.sat.itmd4515.mprasad.service;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.GrillOrder;
import edu.iit.sat.itmd4515.mprasad.domain.OrderDetails;
import edu.iit.sat.itmd4515.mprasad.domain.Staff;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service for Staff entity operations. 
 * @author Monisha
 */
@Named
@Stateless
public class StaffService extends AbstractService<Staff> {

    @EJB
    private CustomerService custSvc;

    private static final Logger LOG = Logger.getLogger(StaffService.class.getName());

    /**
     *
     */
    public StaffService() {
        super(Staff.class);
    }

    /**
     *
     * @return
     */
    public List<Staff> readAll() {
        return super.readAll("Staff.readAll");
    }

    /**
     *
     * @param id
     * @return
     */
    public Staff findById(Long id) {
        LOG.info("Finding Staff by ID: " + id);
        return read(id);
    }

    /**
     *
     * @param staff
     */
    public void addStaff(Staff staff) {
        LOG.info("Adding new Staff: " + staff.getName());
        create(staff);
    }

    /**
     *
     * @param staff
     */
    public void updateStaff(Staff staff) {
        LOG.info("Updating Staff with ID: " + staff.getId());
        update(staff);
    }

    /**
     *
     * @param staffId
     */
    public void deleteStaff(Long staffId) {
        LOG.info("Deleting Staff with ID: " + staffId);
        Staff staff = read(staffId);
        if (staff != null) {
            delete(staff);
        } else {
            LOG.warning("No Staff found with ID: " + staffId);
        }
    }

    /**
     *Method to find staff by username
     * @param username
     * @return
     */
    public Staff findByUsername(String username) {
        LOG.info("Finding Staff by username: " + username);
        TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.user.userName = :username", Staff.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOG.warning("No Staff found with username: " + username);
            return null;
        }
    }

    // Method to find orders by Staff ID

    /**
     *
     * @param staffId
     * @return
     */
    public List<OrderDetails> findOrdersByStaffId(Long staffId) {
        LOG.info("Finding orders for Staff with ID: " + staffId);
        TypedQuery<OrderDetails> query = em.createQuery("SELECT od FROM OrderDetails od WHERE od.staff.id = :staffId", OrderDetails.class);
        query.setParameter("staffId", staffId);
        return query.getResultList();
    }

    /**
     * Method to create order by the authenticated staff
     *
     * @param orderDetails
     * @param staff
     * @param customer
     */
    public void createOrderForAuthenticatedStaff(OrderDetails orderDetails, Staff staff, Customer customer) {
        if (orderDetails.getQuantity() == null || orderDetails.getPrice() == null) {
            throw new IllegalArgumentException("Order details are incomplete.");
        }

        LOG.info("Inside createOrderForAuthenticatedStaff with orderdetails " + orderDetails.toString() + " and staff " + staff.toString());
        em.persist(orderDetails);
        Staff staffRef = em.getReference(Staff.class, staff.getId());

        if (customer != null) {
            custSvc.create(customer);
            LOG.info(
                    "Inside CustomerController.saveCustomer() - after service call. Customer created successfully.");
        } else {
            LOG.warning("Inside CustomerController.saveCustomer() - customer is null.");
        }

        orderDetails.setCustomer(customer);
        staffRef.addOrderDetails(orderDetails);
        em.merge(staffRef);
    }

}
