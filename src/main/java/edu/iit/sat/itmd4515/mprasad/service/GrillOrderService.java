package edu.iit.sat.itmd4515.mprasad.service;

import edu.iit.sat.itmd4515.mprasad.domain.GrillOrder;
import edu.iit.sat.itmd4515.mprasad.domain.ItemStatus;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for managing GrillOrder entity operations.
 * Author: Monisha
 */
@Stateless
public class GrillOrderService extends AbstractService<GrillOrder> {

    private static final Logger LOG = Logger.getLogger(GrillOrderService.class.getName());

    /**
     *
     */
    public GrillOrderService() {
        super(GrillOrder.class);
    }

    /**
     * Method to retrieve all GrillOrder entities.
     * 
     * @return List of all GrillOrder entities.
     */
    public List<GrillOrder> readAll() {
        return super.readAll("GrillOrder.readAll");
    }

    /**
     * Method to find all GrillOrders with a specific status.
     * 
     * @param status The status of the grill orders to search for.
     * @return List of GrillOrders matching the provided status.
     */
    public List<GrillOrder> findByStatus(ItemStatus status) {
        LOG.info("Finding grill orders by status: " + status);
        TypedQuery<GrillOrder> query = em.createNamedQuery("GrillOrder.findByStatus", GrillOrder.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    /**
     * Method to update the status of an existing GrillOrder.
     * 
     * @param grillOrder The GrillOrder entity whose status needs to be updated.
     * @param newStatus The new status to be set for the GrillOrder.
     */
    public void updateOrderStatus(GrillOrder grillOrder, ItemStatus newStatus) {
        LOG.info("Updating status for grill order with ID: " + grillOrder.getId() + " to " + newStatus);
        grillOrder.setItemStatus(newStatus);
        update(grillOrder);
    }
}
