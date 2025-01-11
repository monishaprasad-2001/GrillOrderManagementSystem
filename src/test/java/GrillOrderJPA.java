import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.GrillOrder;
import edu.iit.sat.itmd4515.mprasad.domain.ItemStatus;
import edu.iit.sat.itmd4515.mprasad.domain.MenuItem;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for GrillOrder entity.
 * Author: Monisha
 */
public class GrillOrderJPA extends AbstractJPATest {

    // Helper method to persist a customer
    private Customer createAndPersistCustomer(String name) {
        Customer customer = new Customer(name, "1234567890", name.toLowerCase() + "@example.com", "Test Address");

        tx.begin();
        em.persist(customer);
        tx.commit();

        return customer;
    }

    // Helper method to persist a menu item

    // Helper method for cleanup
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

    // Test to check if a GrillOrder is successfully created

    /**
     *
     */
    @Test
    public void createTest() {
        Customer customer = createAndPersistCustomer("CreateTestCustomer");

        GrillOrder grillOrder = new GrillOrder(customer, 3, 30.0, ItemStatus.FINISHED,  MenuItem.TOTS);

        tx.begin();
        em.persist(grillOrder);
        tx.commit();

        GrillOrder readBackFromDatabase = em.find(GrillOrder.class, grillOrder.getId());
        assertNotNull(readBackFromDatabase);
        assertTrue(readBackFromDatabase.getId() > 0);
        assertEquals(customer.getId(), readBackFromDatabase.getCustomer().getId());

        // Cleanup
        cleanupEntities(readBackFromDatabase, customer);
    }

    // Test to check if reading a GrillOrder from the database is successful

    /**
     *
     */
    @Test
    public void readTest() {
        Customer customer = createAndPersistCustomer("ReadTestCustomer");

        GrillOrder grillOrder = new GrillOrder(customer, 3, 60.0, ItemStatus.FINISHED, MenuItem.TOTS);

        tx.begin();
        em.persist(grillOrder);
        tx.commit();

        GrillOrder readBackFromDatabase = em.find(GrillOrder.class, grillOrder.getId());
        assertNotNull(readBackFromDatabase);
        assertEquals(customer.getId(), readBackFromDatabase.getCustomer().getId());
        assertEquals(3, readBackFromDatabase.getQuantity());
        assertEquals(60.0, readBackFromDatabase.getTotalPrice());
        assertEquals(ItemStatus.FINISHED, readBackFromDatabase.getItemStatus());

        // Cleanup
        cleanupEntities(readBackFromDatabase, customer);
    }

    // Test to check if the update functionality is working properly

    /**
     *
     */
    @Test
    public void updateTest() {
        Customer customer = createAndPersistCustomer("UpdateTestCustomer");

        GrillOrder grillOrder = new GrillOrder(customer, 2, 30.0, ItemStatus.PROCESSING, MenuItem.TOTS);

        tx.begin();
        em.persist(grillOrder);
        tx.commit();

        // Update the quantity
        tx.begin();
        grillOrder.setQuantity(5);
        grillOrder = em.merge(grillOrder);
        tx.commit();

        GrillOrder readBackFromDatabase = em.find(GrillOrder.class, grillOrder.getId());
        assertNotNull(readBackFromDatabase);
        assertEquals(5, readBackFromDatabase.getQuantity());

        // Cleanup
        cleanupEntities(readBackFromDatabase, customer);
    }

    // Test case to check if deleting a GrillOrder is working properly

    /**
     *
     */
    @Test
    public void deleteTest() {
        Customer customer = createAndPersistCustomer("DeleteTestCustomer");

        GrillOrder grillOrder = new GrillOrder(customer, 4, 72.0, ItemStatus.QUEUE,  MenuItem.FRENCHFRIES);

        tx.begin();
        em.persist(grillOrder);
        tx.commit();

        // Remove the order
        tx.begin();
        GrillOrder grillOrderToDelete = em.find(GrillOrder.class, grillOrder.getId());
        em.remove(grillOrderToDelete);
        tx.commit();

        int count = em.createQuery("select o from GrillOrder o where o.id = :id", GrillOrder.class)
                .setParameter("id", grillOrder.getId())
                .getResultList()
                .size();
        assertEquals(0, count);

        // Cleanup
        cleanupEntities(customer);
    }
}
