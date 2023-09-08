package org.example.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordHashingUtil {
    private static final int SALT_LENGTH = 16; // Length of the salt in bytes
    private static final int ITERATIONS = 10000; // Number of iterations for the PBKDF2 algorithm
    private static final int KEY_LENGTH = 256; // Length of the derived key in bits

    public static String hashPassword(String password) {
        // Generate a random salt
        byte[] salt = generateSalt();

        // Hash the password with the salt
        byte[] hashedPassword = hashWithPBKDF2(password.toCharArray(), salt);

        // Combine the salt and hashed password and encode them as Base64
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedPassword);

        // Store the salt and hashed password together (e.g., "salt:hashedPassword")
        return saltBase64 + ":" + hashedPasswordBase64;
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] hashWithPBKDF2(char[] password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        // Split the stored value into salt and hashed password parts
        String[] parts = hashedPassword.split(":");
        if (parts.length != 2) {
            return false; // Invalid format
        }

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);

        // Hash the provided password with the same salt and compare it to the stored hash
        byte[] hashedPasswordToCheck = hashWithPBKDF2(password.toCharArray(), salt);
        return MessageDigest.isEqual(storedHash, hashedPasswordToCheck);
    }
}
