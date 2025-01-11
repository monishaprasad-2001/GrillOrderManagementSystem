package edu.iit.sat.itmd4515.mprasad.domain;

/**
 * Enum representing the menu items
 * Provides user-friendly labels for each menu item
 *
 * Author: Monisha
 */
public enum MenuItem {

    /**
     *
     */
    VEGGIEBURGER("VEGGIEBURGER"),

    /**
     *
     */
    CHEESEBURGER("CHEESEBURGER"),

    /**
     *
     */
    TOTS("TOTS"),

    /**
     *
     */
    FRENCHFRIES("Frenchfries");

    private String label;

    private MenuItem(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return label;
    }

    /**
     *
     * @param label
     * @return
     */
    public static MenuItem fromLabel(String label) {
        for (MenuItem status : MenuItem.values()) {
            if (status.label.equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown label: " + label);
    }
}
