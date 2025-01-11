import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.GrillOrder;
import edu.iit.sat.itmd4515.mprasad.domain.ItemStatus;
import edu.iit.sat.itmd4515.mprasad.domain.MenuItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * Abstract class to provide setup for JPA testing.
 * Author: Monisha
 */
public abstract class AbstractJPATest {

    private static EntityManagerFactory emf;

    /**
     *
     */
    protected EntityManager em;

    /**
     *
     */
    protected EntityTransaction tx;

    /**
     *
     */
    protected Customer testCustomer;  // Declare the Customer to be used in tests

    /**
     *
     */
    protected GrillOrder testGrillOrder; // Declare GrillOrder for use in tests

    /**
     *
     */
    @BeforeAll
    public static void beforeAll() {
        // Initializing the EntityManagerFactory
        emf = Persistence.createEntityManagerFactory("itmd4515testPU");
        System.out.println("beforeAll - EntityManagerFactory initialized.");
    }

    // Setting up EntityManager, creating initial Customer, and creating GrillOrder before each test method

    /**
     *
     */
    @BeforeEach
    public void beforeEach() {
        em = emf.createEntityManager();
        tx = em.getTransaction();

        // Create and persist a Customer and associated GrillOrder for use in each test
        testCustomer = new Customer("Test Customer", "1234567890", "testcustomer@example.com", "Test Address");
        testGrillOrder = new GrillOrder(testCustomer, 3, 150.0, ItemStatus.FINISHED,  MenuItem.TOTS);

        tx.begin();
        try {
            em.persist(testCustomer);
            em.persist(testGrillOrder);
            tx.commit();
            System.out.println("beforeEach - Customer and GrillOrder created:\n\t" + testCustomer + "\n\t" + testGrillOrder);
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Exception during beforeEach: " + e.getMessage());
            throw e;
        }
    }

    // Removing the created GrillOrder and Customer after each test method

    /**
     *
     */
    @AfterEach
    public void afterEach() {
        tx.begin();
        try {
            // Refreshing to get the updated state from persistence context
            testGrillOrder = em.find(GrillOrder.class, testGrillOrder.getId());
            if (testGrillOrder != null) {
                em.remove(testGrillOrder);
                System.out.println("afterEach - GrillOrder removed: " + testGrillOrder);
            }

            testCustomer = em.find(Customer.class, testCustomer.getId());
            if (testCustomer != null) {
                em.remove(testCustomer);
                System.out.println("afterEach - Customer removed: " + testCustomer);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Exception during afterEach: " + e.getMessage());
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
                System.out.println("afterEach - EntityManager closed.");
            }
        }
    }

    /**
     *
     */
    @AfterAll
    public static void afterAll() {
        // Closing the EntityManagerFactory
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("afterAll - EntityManagerFactory closed.");
        }
    }
}
