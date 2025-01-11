package edu.iit.sat.itmd4515.mprasad.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * GrillOrder entity representing an order in the grill order system.
 * Author: Monisha
 */
@Entity
@Table(name = "GrillOrder")
@NamedQueries({
    @NamedQuery(name = "GrillOrder.readAll", query = "SELECT o FROM GrillOrder o"),
    @NamedQuery(name = "GrillOrder.findByStatus", query = "SELECT o FROM GrillOrder o WHERE o.itemStatus = :status")
})
public class GrillOrder extends AbstractEntity {

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Total price must not be null")
    @Positive(message = "Total price must be greater than zero")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
    
    @Enumerated(EnumType.STRING)
    private MenuItem menuItem;

    // Bidirectional relationship with Staff
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STAFF_ID")
    private Staff staff;

    // Bidirectional relationship with Customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @NotNull(message = "Customer must not be null")
    private Customer customer;

    // Bidirectional relationship with OrderDetails
    @OneToMany(mappedBy = "grillOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();

    // Constructors

    /**
     *
     */
    public GrillOrder() {
    }

    /**
     *
     * @param customer
     * @param quantity
     * @param totalPrice
     * @param itemStatus
     * @param menuItem
     * 
     * Parameterized constructor for the entity GrillOrder
     */
    public GrillOrder(Customer customer, Integer quantity, Double totalPrice, ItemStatus itemStatus, MenuItem menuItem) {
        this.customer = customer;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.itemStatus = itemStatus;
        this.menuItem = menuItem;
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
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     *
     * @param totalPrice
     */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     *
     * @return
     */
    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    /**
     *
     * @param itemStatus
     */
    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    /**
     *
     * @return staff
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
        if (this.customer != null) {
            this.customer.getOrders().remove(this);
        }
        this.customer = customer;
        if (customer != null && !customer.getOrders().contains(this)) {
            customer.getOrders().add(this);
        }
    }

    /**
     *
     * @return
     */
    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    /**
     *
     * @param orderDetails
     */
    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    // Helper Methods for managing bidirectional relationships

    /**
     *
     * @param orderDetail
     */

    public void addOrderDetail(OrderDetails orderDetail) {
        if (!this.orderDetails.contains(orderDetail)) {
            this.orderDetails.add(orderDetail);
            orderDetail.setGrillOrder(this);
        }
    }

    /**
     *
     * @param orderDetail
     */
    public void removeOrderDetail(OrderDetails orderDetail) {
        if (this.orderDetails.contains(orderDetail)) {
            this.orderDetails.remove(orderDetail);
            orderDetail.setGrillOrder(null);
        }
    }

    // Overriding equals, hashCode, and toString methods

    /**
     *
     * @param o
     * @return
     */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GrillOrder that = (GrillOrder) o;
        return Objects.equals(quantity, that.quantity) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                itemStatus == that.itemStatus &&
                Objects.equals(customer, that.customer);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(quantity, totalPrice, itemStatus, customer);
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
    return "GrillOrder{" +
           "id=" + id +
           ", customerName='" + (customer != null ? customer.getName() : "N/A") + '\'' +
           ", quantity=" + quantity +
           ", totalPrice=" + totalPrice +
           ", itemStatus=" + itemStatus +
           ", menuItem=" + (menuItem != null ? menuItem.getLabel() : "N/A") +
           ", orderDetailsCount=" + (orderDetails != null ? orderDetails.size() : 0) +
           '}';
}


}
