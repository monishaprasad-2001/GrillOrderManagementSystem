package edu.iit.sat.itmd4515.mprasad.domain;

import com.github.flysium.io.yew.common.logger.LoggerConstants;
import com.mysql.cj.log.Log;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Objects;

/**
 * Entity representing the details of an order in the grill order system.
 * Author: Monisha
 */
@Entity
@NamedQuery(name = "OrderDetails.readAll", query = "SELECT od FROM OrderDetails od")
public class OrderDetails extends AbstractEntity {

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity; // Quantity of the menu item in the order

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than zero")
    private Double price; // Price of the menu item in the order

    // Many-to-one relationship with GrillOrder
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ORDER_ID", nullable = true)
    private GrillOrder grillOrder;

    // Many-to-one relationship with Staff
    @ManyToOne
    @JoinColumn(name = "STAFF_ID")
    private Staff staff;

    // Many-to-one relationship with Customer
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = true)
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    private MenuItem menuItem;

    // Constructors

    /**
     *
     */
    public OrderDetails() {
    }

    /**
     *
     * @param quantity
     * @param price
     * @param grillOrder
     * @param staff
     * @param customer
     * @param menuItem
     */
    public OrderDetails(Integer quantity, Double price, GrillOrder grillOrder, Staff staff, Customer customer, MenuItem menuItem) {
        this.quantity = quantity;
        this.price = price;
        this.menuItem = menuItem;
        this.setGrillOrder(grillOrder);
        this.setStaff(staff);
        this.setCustomer(customer);
    }

    /**
     *
     * @param quantity
     * @param price
     */
    public OrderDetails(Integer quantity, Double price) {
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters

    /**
     *
     * @return
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *
     * @return
     */
    public GrillOrder getGrillOrder() {
        return grillOrder;
    }

    /**
     *
     * @param grillOrder
     */
    public void setGrillOrder(GrillOrder grillOrder) {
        if (this.grillOrder != null) {
            this.grillOrder.getOrderDetails().remove(this);
        }
        this.grillOrder = grillOrder;
        if (grillOrder != null && !grillOrder.getOrderDetails().contains(this)) {
            grillOrder.getOrderDetails().add(this);
        }
    }

    /**
     *
     * @return
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     *
     * @param staff
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    /**
     *
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    

    // Helper method to set relationships more conveniently

    /**
     *
     * @param order
     * @param staff
     * @param customer
     * @param menuItem
     */
    public void setOrderDetails(GrillOrder order,Staff staff, Customer customer, MenuItem menuItem) {
        
        System.out.println("order within setOrderDetails"+ order);
        this.setGrillOrder(order);
        this.setStaff(staff);
        this.setCustomer(customer);
        this.setMenuItem(menuItem);
    }

    // Override hashCode and equals

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(quantity, price, grillOrder, staff, customer);
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OrderDetails other = (OrderDetails) obj;
        return Objects.equals(quantity, other.quantity) && Objects.equals(price, other.price)
                && Objects.equals(grillOrder, other.grillOrder)
                && Objects.equals(staff, other.staff) && Objects.equals(customer, other.customer);
    }

    /**
     *
     * @return
     */
    public MenuItem getMenuItem() {
        return menuItem;
    }

    /**
     *
     * @param menuItem
     */
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    /**
     *
     * @return
     */
    @Override
public String toString() {
    return "OrderDetails{" +
           "id=" + id +
           ", quantity=" + quantity +
           ", price=" + price +
           // Avoid calling grillOrder.toString() directly
           (grillOrder != null ? ", grillOrderId=" + grillOrder.getId() : "") +
           '}';
}

}
