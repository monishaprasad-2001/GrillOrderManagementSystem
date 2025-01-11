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
 * Entity representing a staff member in the grill order system.
 * Author: Monisha
 */
@Entity
@NamedQuery(name = "Staff.readAll", query = "SELECT s FROM Staff s")
@NamedQuery(name = "Staff.findByUsername", query = "SELECT s FROM Staff s WHERE s.user.userName = :userName")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the staff member

    @NotBlank(message = "Name must not be blank")
    private String name; // Name of the staff member

    @NotBlank(message = "Role must not be blank")
    private String role; // Role or position (e.g., Chef, Waiter)

    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber; // Staff member’s contact number

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email; // Staff member’s email address

    // One-to-many bidirectional relationship with OrderDetails
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();

    // One-to-One relationship with User for authentication and authorization data
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USERNAME", unique = true)
    private User user;

    // Constructors

    /**
     *
     */
    public Staff() {
    }

    /**
     *
     * @param name
     * @param role
     * @param phoneNumber
     * @param email
     */
    public Staff(String name, String role, String phoneNumber, String email) {
        this.name = name;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
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
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
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
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getStaff() != this) {
            user.setStaff(this);  // Ensuring bidirectional consistency
        }
    }

    // Methods to manage bidirectional relationship with OrderDetails

    /**
     *
     * @param orderDetail
     */
    public void addOrderDetails(OrderDetails orderDetail) {
        if (!this.orderDetails.contains(orderDetail)) {
            this.orderDetails.add(orderDetail);
            orderDetail.setStaff(this);
        }
    }

    /**
     *
     * @param orderDetail
     */
    public void removeOrderDetails(OrderDetails orderDetail) {
        if (this.orderDetails.contains(orderDetail)) {
            this.orderDetails.remove(orderDetail);
            orderDetail.setStaff(null);
        }
    }

    // Override hashCode and equals methods

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
        Staff other = (Staff) obj;
        return Objects.equals(id, other.id);
    }

    // Override toString for better logging and debugging

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", orderDetailsCount=" + orderDetails.size() +
                '}';
    }
}
