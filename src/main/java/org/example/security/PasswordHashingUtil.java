package org.example.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordHashingUtil {
    private static final int SALT_LENGTH = 16; // Length of the salt in bytes
    private static final int ITERATIONS = 10000; // Number of iterations for the PBKDF2 algorithm
    private static final int KEY_LENGTH = 256; // Length of the derived key in bits

    public static String hashPassword(String password, String salt) {
        int iterations = 10000; // Number of iterations
        int keyLength = 256;    // Key length in bits

        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
            byte[] combined = new byte[saltBytes.length + hashedPassword.length];
            System.arraycopy(saltBytes, 0, combined, 0, saltBytes.length);
            System.arraycopy(hashedPassword, 0, combined, saltBytes.length, hashedPassword.length);

            // Encode the combined byte array as Base64
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Handle exceptions (e.g., log the error)
            return null;
        }
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
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