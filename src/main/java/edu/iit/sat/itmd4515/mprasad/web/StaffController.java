package edu.iit.sat.itmd4515.mprasad.web;

import edu.iit.sat.itmd4515.mprasad.domain.OrderDetails;
import edu.iit.sat.itmd4515.mprasad.domain.Staff;
import edu.iit.sat.itmd4515.mprasad.service.StaffService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller for Staff-specific operations.
 * Author: Monisha
 */
@Named
@RequestScoped
public class StaffController {

    private static final Logger LOG = Logger.getLogger(StaffController.class.getName());

    @Inject
    private StaffService staffService;

    @Inject
    private FacesContext facesContext;

    private Staff loggedInStaff;

    // Get logged-in staff orders by ID

    /**
     *
     * @return
     */
    public List<OrderDetails> getLoggedInStaffOrders() {
        Staff staff = getLoggedInStaff(); // Get the logged-in staff entity
        if (staff != null) {
            LOG.info("Fetching orders for logged-in staff with ID: " + staff.getId());
            return staffService.findOrdersByStaffId(staff.getId());
        }
        return null;
    }

    // Getter for logged-in staff

    /**
     *
     * @return
     */
    public Staff getLoggedInStaff() {
        if (loggedInStaff == null) {
            String username = facesContext.getExternalContext().getRemoteUser(); // Get the current logged-in username
            if (username != null) {
                loggedInStaff = staffService.findByUsername(username);
            }
        }
        return loggedInStaff;
    }
}
