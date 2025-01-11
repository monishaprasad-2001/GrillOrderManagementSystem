package edu.iit.sat.itmd4515.mprasad.security;

import edu.iit.sat.itmd4515.mprasad.domain.Customer;
import edu.iit.sat.itmd4515.mprasad.domain.Staff;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User entity representing users in the security module.
 * Author: Monisha
 */
@Entity
@Table(name = "SEC_USER")
@EntityListeners(UserPasswordHash.class)
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "select u from User u"),
    @NamedQuery(name = "User.findByUserName", query = "select u from User u where u.userName = :userName")
})
public class User {

    @Id
    @NotNull(message = "Username must not be null")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String userName;
    
    private Boolean enabled;

    @NotNull(message = "Password must not be null")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SEC_USER_GROUPS",
            joinColumns = @JoinColumn(name = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "GROUPNAME"))
    private List<Group> groups = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Staff staff;

    // Constructors

    /**
     *
     * @param userName
     * @param password
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     *
     */
    public User() {
    }

    // Getters and Setters

    /**
     *
     * @return
     */

    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     *
     * @param groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
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

    // Helper methods for managing bidirectional relationships

    /**
     *
     * @param group
     */
    public void addGroup(Group group) {
        if (!this.groups.contains(group)) {
            this.groups.add(group);
            group.getUsers().add(this);
        }
    }

    /**
     *
     * @param group
     */
    public void removeGroup(Group group) {
        if (this.groups.contains(group)) {
            this.groups.remove(group);
            group.getUsers().remove(this);
        }
    }

    // Override hashCode, equals, and toString

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName);
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
        final User other = (User) obj;
        return Objects.equals(this.userName, other.userName);
    }
    
    /**
     *
     * @return
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = this.enabled;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "User{" + "userName='" + userName + '\'' + ", groups=" + groups + '}';
    }
}
