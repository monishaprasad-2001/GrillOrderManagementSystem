package edu.iit.sat.itmd4515.mprasad.security;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.Staff;
import edu.iit.sat.itmd4515.mprasad.service.AbstractService;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for managing User entities.
 * Provides methods for interacting with the User entity.
 * 
 * Author: Monisha
 */
@Stateless
public class UserService extends AbstractService<User> {

    /**
     *
     */
    public UserService() {
        super(User.class);
    }

    /**
     *
     * @return
     */
    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();

    }

    /**
     *
     * @param customer
     * 
     * Function to add new a Customer, assign a group and enable the user
     */
    public void addNewCustomerUser(Customer c) {
        //create non-owning entity at first

        Group customerGroup
                = em.createQuery("select g from Group g where g.groupName = 'CUSTOMER_GROUP'", Group.class).getSingleResult();
        c.getUser().addGroup(customerGroup);
        c.getUser().setEnabled(true);
        em.persist(c.getUser());

        // then we persist the owning entity
        em.persist(c);
    }
    
    /**
     *
     * s t
     * @param staff
     * Function to add new a Staff, assign a group and enable the user
     */
    public void addNewStaffUser(Staff s) {
        //create non-owning entity at first

        Group staffGroup
                = em.createQuery("select g from Group g where g.groupName = 'STAFF_GROUP'", Group.class).getSingleResult();
        s.getUser().addGroup(staffGroup);
        s.getUser().setEnabled(true);
        em.persist(s.getUser());

        // then we persist the owning entity
        em.persist(s);
    }
    
    /**
     *
     * @param userName
     * @return
     * 
     * Fetches a particular user by using the userName of the user and returns the user if present
     */
    public User findByUserName(String userName) {
    try {
        TypedQuery<User> query = em.createNamedQuery("User.findByUserName", User.class);
        query.setParameter("userName", userName);
        return query.getSingleResult();
    } catch (Exception e) {
        Logger.getLogger(UserService.class.getName()).warning("User name found with name: " + userName);
        return null;
    }
}
}
