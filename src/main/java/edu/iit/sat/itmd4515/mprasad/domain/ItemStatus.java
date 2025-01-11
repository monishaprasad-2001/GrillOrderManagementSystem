package edu.iit.sat.itmd4515.mprasad.domain;

/**
 * Enum representing the status of an item in the grill order system.
 * Provides user-friendly labels for each status type.
 *
 * Author: Monisha
 */
public enum ItemStatus {

    /**
     *
     */
    QUEUE("Queue"),

    /**
     *
     */
    PROCESSING("Processing"),

    /**
     *
     */
    FINISHED("Finished");

    private String label;

    private ItemStatus(String label) {
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
    public static ItemStatus fromLabel(String label) {
        for (ItemStatus status : ItemStatus.values()) {
            if (status.label.equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown label: " + label);
    }
}
