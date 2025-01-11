import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.GrillOrder;
import edu.iit.sat.itmd4515.mprasad.domain.ItemStatus;
import edu.iit.sat.itmd4515.mprasad.domain.MenuItem;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for validating the JPA relationships between Order and associated entities.
 * Author: Monisha
 */
public class OrderJPARelationship extends AbstractJPATest {

    // Helper method to create and persist a customer
    private Customer createAndPersistCustomer(String name, String phoneNumber, String email, String address) {
        Customer customer = new Customer(name, phoneNumber, email, address);
        tx.begin();
        em.persist(customer);
        tx.commit();
        return customer;
    }


    // Cleanup helper method to remove entities
    private void cleanupEntities(Object... entities) {
        tx.begin();
        try {
            for (Object entity : entities) {
                if (entity != null) {
                    em.remove(em.contains(entity) ? entity : em.merge(entity));
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    // Test to verify unidirectional relationship between GrillOrder and Customer

    /**
     *
     */
    @Test
    public void uniDirectionalTestCase() {
        // Creating and persisting a customer
        Customer customer = createAndPersistCustomer("John Doe", "1234567890", "john@example.com", "123 Main St");

        // Creating GrillOrder entity and associating customer
        GrillOrder grillOrder = new GrillOrder(customer, 3, 50.0, ItemStatus.QUEUE,  MenuItem.TOTS);
        customer.addOrder(grillOrder);

        tx.begin();
        em.persist(grillOrder);
        tx.commit();

        // Verifying relationship from GrillOrder to Customer
        GrillOrder readBackFromDatabase = em.find(GrillOrder.class, grillOrder.getId());
        assertNotNull(readBackFromDatabase);
        assertNotNull(readBackFromDatabase.getCustomer());
        assertEquals("John Doe", readBackFromDatabase.getCustomer().getName());

        // Additional assertions for customer orders list
        assertEquals(1, readBackFromDatabase.getCustomer().getOrders().size());
        assertTrue(readBackFromDatabase.getCustomer().getOrders().contains(grillOrder));

        // Cleanup
        cleanupEntities(grillOrder, customer);
    }

    // Test to verify bidirectional relationship between GrillOrder and MenuItem

    /**
     *
     */
    @Test
    public void biDirectionalTestCase() {
        // Creating and persisting customer and menu item
        Customer customer = createAndPersistCustomer("Mark Spencer", "0987654321", "mark@example.com", "456 Elm St");

        // Creating and persisting GrillOrder, associating it with customer and menu item
        GrillOrder grillOrder = new GrillOrder(customer, 3, 36.97, ItemStatus.FINISHED,  MenuItem.TOTS);
        customer.addOrder(grillOrder);

        tx.begin();
        em.persist(grillOrder);
        tx.commit();

        GrillOrder readBackFromDatabase = em.find(GrillOrder.class, grillOrder.getId());
        assertNotNull(readBackFromDatabase);


        // Verify the relationship is correctly reflected in customer as well
        assertEquals(1, customer.getOrders().size());
        assertTrue(customer.getOrders().contains(grillOrder));

        // Cleanup
        cleanupEntities(grillOrder, customer);
    }
}
