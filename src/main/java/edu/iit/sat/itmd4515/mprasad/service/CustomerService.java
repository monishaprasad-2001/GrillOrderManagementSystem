package edu.iit.sat.itmd4515.mprasad.service;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Service for Customer entity operations.
 * Author: Monisha
 */
@Named
@Stateless
public class CustomerService extends AbstractService<Customer> {
    
    @PersistenceContext(name = "itmd4515PU")
    private EntityManager em;

    
      /**
     *
     * @param c
     */
    public void create(Customer c) {
        em.persist(c);
    }
    
     /**
     *
     * @param id
     * @return
     */
    public Customer read(Long id) {
        return em.find(Customer.class, id);
    }

    /**
     *
     * @param c
     */
    public void update(Customer c) {
        em.merge(c);
    }

    /**
     *
     * @param c
     */
    public void delete(Customer c) {
        em.remove(em.merge(c));
    }

    /**
     *
     */
    public CustomerService() {
        super(Customer.class);
    }

    /**
     *
     * @return
     */
    public List<Customer> readAll() {
        return super.readAll("Customer.readAll");
    }
	
    /**
     *
     * @param userName
     * @return the customer with the username passed as the argument
     */
    public Customer findByUsername(String userName) {
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findByUsername", Customer.class);
        query.setParameter("userName", userName);
        return query.getResultStream().findFirst().orElse(null);
    }
}

