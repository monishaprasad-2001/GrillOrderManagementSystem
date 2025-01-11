package edu.iit.sat.itmd4515.mprasad.config;

import jakarta.annotation.security.DeclareRoles;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 * Security configuration for ITMD4515.
 * Author: Monisha
 */
@DeclareRoles({"ADMIN_ROLE", "STAFF_ROLE", "CUSTOMER_ROLE"})
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "/error.xhtml"
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:app/jdbc/itmd4515DS",
        callerQuery = "select PASSWORD from SEC_USER where USERNAME = ?",
        groupsQuery = "select GROUPNAME from SEC_USER_GROUPS where USERNAME = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Iterations=2048",
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA256",
                "Pbkdf2PasswordHash.SaltSizeBytes=32",
                "Pbkdf2PasswordHash.KeySizeBytes=32"
        },
        priority = 30
)
public class ITMD4515SecurityConfig {
    // This class configures Jakarta EE security for the application.
}
