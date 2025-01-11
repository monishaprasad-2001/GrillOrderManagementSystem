import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.GrillOrder;
import edu.iit.sat.itmd4515.mprasad.domain.ItemStatus;
import edu.iit.sat.itmd4515.mprasad.domain.MenuItem;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the validation of GrillOrder entities.
 * Author: Monisha
 */
public class OrderValidation {

    private static Validator validator;
    private static ValidatorFactory factory;

    /**
     *
     */
    @BeforeAll
    public static void beforeAll() {
        // Initialize the ValidatorFactory and Validator instance before all tests
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     *
     */
    @AfterAll
    public static void afterAll() {
        // Closing the ValidatorFactory to release resources
        if (factory != null) {
            factory.close();
        }
    }

    // Test case to validate a valid GrillOrder instance

    /**
     *
     */
    @Test
    public void orderIsValid() {
        // Creating a customer to associate with the order
        Customer customer = new Customer("John Doe", "1234567890", "john@example.com", "123 Main St");

        // Creating a valid GrillOrder instance with required fields
        GrillOrder grillOrder = new GrillOrder(customer, 3, 150.0, ItemStatus.FINISHED,  MenuItem.TOTS);
        System.out.println("Testing valid order instance: " + grillOrder.toString());

        // Validate the GrillOrder instance using Bean Validation
        Set<ConstraintViolation<GrillOrder>> violations = validator.validate(grillOrder);

        // Assert that no violations occurred
        assertEquals(0, violations.size(), "Expected no validation violations for a valid order.");
    }

    // Test case to check if negative quantity results in validation error

    /**
     *
     */
    @Test
    public void orderIsInvalidDueToNegativeQuantity() {
        // Creating a customer to associate with the order
        Customer customer = new Customer("Jane Doe", "0987654321", "jane@example.com", "456 Main St");

        // Creating an invalid GrillOrder instance with negative quantity
        GrillOrder grillOrder = new GrillOrder(customer, -5, 150.0, ItemStatus.FINISHED,  MenuItem.TOTS); // Negative quantity
        System.out.println("Testing order with negative quantity: " + grillOrder.toString());

        // Validate the GrillOrder instance
        Set<ConstraintViolation<GrillOrder>> violations = validator.validate(grillOrder);

        // Assert that there is at least one validation error for negative quantity
        assertFalse(violations.isEmpty(), "There should be a validation error for negative quantity.");
        violations.forEach(violation -> System.out.println("Violation: " + violation.getMessage()));
    }

    // Test case to check if null customer results in validation error

    /**
     *
     */
    @Test
    public void orderIsInvalidDueToNullCustomer() {
        // Creating an invalid GrillOrder instance with null customer
        GrillOrder grillOrder = new GrillOrder(null, 2, 100.0, ItemStatus.QUEUE,  MenuItem.TOTS); // Null customer
        System.out.println("Testing order with null customer: " + grillOrder.toString());

        // Validate the GrillOrder instance
        Set<ConstraintViolation<GrillOrder>> violations = validator.validate(grillOrder);

        // Assert that there is at least one validation error for null customer
        assertFalse(violations.isEmpty(), "There should be a validation error for null customer.");
        violations.forEach(violation -> System.out.println("Violation: " + violation.getMessage()));
    }

    // Test case to check if null quantity results in validation error

    /**
     *
     */
    @Test
    public void orderIsInvalidDueToNullQuantity() {
        // Creating a customer to associate with the order
        Customer customer = new Customer("Jim Beam", "1122334455", "jim@example.com", "789 Main St");

        // Creating an invalid GrillOrder instance with null quantity
        GrillOrder grillOrder = new GrillOrder(customer, null, 120.0, ItemStatus.PROCESSING,  MenuItem.TOTS); // Null quantity
        System.out.println("Testing order with null quantity: " + grillOrder.toString());

        // Validate the GrillOrder instance
        Set<ConstraintViolation<GrillOrder>> violations = validator.validate(grillOrder);

        // Assert that there is at least one validation error for null quantity
        assertFalse(violations.isEmpty(), "There should be a validation error for null quantity.");
        violations.forEach(violation -> System.out.println("Violation: " + violation.getMessage()));
    }

    // Test case to check if zero or negative price results in validation error

    /**
     *
     */
    @Test
    public void orderIsInvalidDueToZeroOrNegativePrice() {
        // Creating a customer to associate with the order
        Customer customer = new Customer("Lara Croft", "4455667788", "lara@example.com", "789 Explorer St");

        // Creating an invalid GrillOrder instance with zero price
        GrillOrder grillOrder = new GrillOrder(customer, 3, 0.0, ItemStatus.QUEUE,  MenuItem.TOTS); // Zero price
        System.out.println("Testing order with zero price: " + grillOrder.toString());

        // Validate the GrillOrder instance
        Set<ConstraintViolation<GrillOrder>> violations = validator.validate(grillOrder);

        // Assert that there is at least one validation error for zero price
        assertFalse(violations.isEmpty(), "There should be a validation error for zero price.");
        violations.forEach(violation -> System.out.println("Violation: " + violation.getMessage()));
    }
}
