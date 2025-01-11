package edu.iit.sat.itmd4515.mprasad.security;

import edu.iit.sat.itmd4515.mprasad.service.AbstractService;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Service class to handle CRUD operations for Group entity.
 * Author: Monisha
 */
@Stateless
public class GroupService extends AbstractService<Group> {

    /**
     *
     */
    public GroupService() {
        super(Group.class);
    }

    /**
     * Finds all Group entities from the database.
     * @return a list of all Group entities
     */
    public List<Group> findAll() {
        // Utilizing the Group.findAll named query to read all group entities
        TypedQuery<Group> query = em.createNamedQuery("Group.findAll", Group.class);
        return query.getResultList();
    }

    /**
     * Finds a Group by its name.
     * @param groupName the name of the group to find
     * @return the Group entity with the given name, or null if not found
     */
    public Group findByName(String groupName) {
        TypedQuery<Group> query = em.createNamedQuery("Group.findByName", Group.class);
        query.setParameter("groupName", groupName);
        return query.getResultStream().findFirst().orElse(null);
    }

}
