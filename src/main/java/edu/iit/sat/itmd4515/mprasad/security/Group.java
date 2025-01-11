package edu.iit.sat.itmd4515.mprasad.security;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity representing security groups.
 * Author: Monisha
 */
@Entity
@Table(name = "SEC_GROUP")
@NamedQueries({
    @NamedQuery(name = "Group.findByName", query = "SELECT g FROM Group g WHERE g.groupName = :groupName"),
    @NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g")
})
public class Group {

    @Id
    private String groupName;

    private String groupDesc;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> users = new ArrayList<>();

    // Constructors

    /**
     *
     * @param groupName
     * @param groupDesc
     */
    public Group(String groupName, String groupDesc) {
        this.groupName = groupName;
        this.groupDesc = groupDesc;
    }

    /**
     *
     * @param groupName
     */
    public Group(String groupName) {
        this(groupName, null); // Chain constructor for groupName only
    }

    /**
     *
     */
    public Group() {
    }

    // Method to add a User to the Group

    /**
     *
     * @param user
     */
    public void addUser(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
            user.getGroups().add(this);
        }
    }

    // Method to remove a User from the Group

    /**
     *
     * @param user
     */
    public void removeUser(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
            user.getGroups().remove(this);
        }
    }

    // Getters and Setters

    /**
     *
     * @return
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     *
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     *
     * @return
     */
    public String getGroupDesc() {
        return groupDesc;
    }

    /**
     *
     * @param groupDesc
     */
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    /**
     *
     * @return
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     *
     * @param users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Overriding hashCode method

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(groupName);
    }

    // Overriding equals method to compare based on groupName

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
        Group other = (Group) obj;
        return Objects.equals(this.groupName, other.groupName);
    }

    // Overriding toString method

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Group{" + "groupName=" + groupName + ", groupDesc=" + groupDesc + '}';
    }
}
