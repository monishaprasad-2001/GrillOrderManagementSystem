package edu.iit.sat.itmd4515.mprasad.security;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Entity Listener for hashing passwords before persisting or updating User entities.
 * It ensures that the password is securely hashed before saving it to the database.
 *
 * Author: Monisha
 */
@Dependent // Indicates this can be managed by CDI container
public class UserPasswordHash {

    private static final Logger LOG = Logger.getLogger(UserPasswordHash.class.getName());

    private Pbkdf2PasswordHash hash;

    /**
     *
     */
    @PostConstruct
    public void init() {
        // Programmatically initializing the PBKDF2 hasher
        hash = CDI.current().select(Pbkdf2PasswordHash.class).get();

        // Configure the hash parameters if needed
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "2048");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        hash.initialize(parameters);
    }

    /**
     *
     * @param u
     */
    @PrePersist
    @PreUpdate
    protected void hashPassword(User u) {
        // Log that we are hashing the password
        LOG.info("Hashing the password for user: " + u.getUserName());

        // Check if password is already hashed
        if (isPasswordAlreadyHashed(u.getPassword())) {
            LOG.info("Password for user " + u.getUserName() + " is already hashed. Skipping rehashing.");
        } else {
            // Hash the password only if it is not already hashed
            String hashedPassword = hash.generate(u.getPassword().toCharArray());
            u.setPassword(hashedPassword);
            LOG.info("Password hashed successfully for user: " + u.getUserName());
        }
    }

    /**
     * Utility method to check if the password appears to be already hashed.
     * 
     * In this example, we assume that PBKDF2 hashed passwords have a specific format.
     * PBKDF2-generated hashes generally contain a marker such as a prefix or certain length.
     *
     * @param password the password to be checked
     * @return true if the password appears to be already hashed, false otherwise
     */
    private boolean isPasswordAlreadyHashed(String password) {
        // Assuming PBKDF2WithHmacSHA256 adds "$31$" as a prefix in our library's hashed passwords
        return password != null && password.startsWith("PBKDF2WithHmacSHA256");
    }
}
