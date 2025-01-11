package edu.iit.sat.itmd4515.mprasad.domain;

import edu.iit.sat.itmd4515.mprasad.security.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity representing a customer in the grill order system.
 * Author: Monisha
 */
@Entity
@NamedQuery(name = "Customer.readAll", query = "SELECT c FROM Customer c")
@NamedQuery(name = "Customer.findByUsername", query = "SELECT c FROM Customer c WHERE c.user.userName = :userName")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USERNAME", unique = true)
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrillOrder> orders = new ArrayList<>();

    // Constructors

    /**
     *
     */
    public Customer() {
    }

    /**
     *
     * @param name
     * @param phoneNumber
     * @param email
     * @param address
     * Parameterized constructor
     */
    public Customer(String name, String phoneNumber, String email, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    // Getters and Setters

    /**
     *
     * @return
     */

    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
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

    /**
     *
     * @return
     */
    public List<GrillOrder> getOrders() {
        return orders;
    }

    /**
     *
     * @param orders
     */
    public void setOrders(List<GrillOrder> orders) {
        this.orders = orders;
    }

    // Helper methods for managing bidirectional relationships

    /**
     *
     * @param order
     * Function to check if the order is already present and add if not 
     */
    public void addOrder(GrillOrder order) {
        if (!this.orders.contains(order)) {
            this.orders.add(order);
            order.setCustomer(this);
        }
    }

    /**
     *
     * @param order
     * Function to check if the order is present and delete if yes 
     */
    public void removeOrder(GrillOrder order) {
        if (this.orders.contains(order)) {
            this.orders.remove(order);
            order.setCustomer(null);
        }
    }

    /**
     *
     * @param detail
     * Function to check if the orderDetails is already present and add if not 
     */
    public void addOrderDetail(OrderDetails detail) {
        if (!this.orderDetails.contains(detail)) {
            this.orderDetails.add(detail);
            detail.setCustomer(this);
        }
    }

    /**
     *
     * @param detail
     * Function to check if the order details is present and delete if yes 
     */
    public void removeOrderDetail(OrderDetails detail) {
        if (this.orderDetails.contains(detail)) {
            this.orderDetails.remove(detail);
            detail.setCustomer(null);
        }
    }

    // Override hashCode, equals, and toString

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        return Objects.equals(id, other.id);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", email='" + email + '\'' + ", address='" + address + '\'' + '}';
    }
}
