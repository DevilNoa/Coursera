package org.example.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHashingUtil {
  // Length of the salt in bytes
  private static final int SALT_LENGTH = 16;

  // Method for hashing the password using PBKDF2 with HMAC SHA-256
  public static String hashPassword(String password, String salt) {
    int iterations = 10000; // Number of iterations
    int keyLength = 256; // Key length in bits

    char[] passwordChars = password.toCharArray(); // Convert the password to a character array
    byte[] saltBytes =
        Base64.getDecoder().decode(salt); // Decode the salt from Base64 to a byte array

    // Creating a PBEKeySpec with password, salt, iterations, and key length
    PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);

    try {
      // Get a SecretKeyFactory instance for PBKDF2 with HMAC SHA-256
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

      byte[] hashedPassword =
          factory.generateSecret(spec).getEncoded(); // Generate the hashed password as a byte array
      byte[] combined =
          new byte
              [saltBytes.length
                  + hashedPassword
                      .length]; // Combine the salt and hashed password into one byte array

      System.arraycopy(saltBytes, 0, combined, 0, saltBytes.length);
      System.arraycopy(hashedPassword, 0, combined, saltBytes.length, hashedPassword.length);

      // Encoding the combined byte array as Base64
      return Base64.getEncoder().encodeToString(combined);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      // Handle exceptions (e.g., log the error)
      return null;
    }
  }

  // Method to generate a random salt and encode it as Base64
  public static String generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_LENGTH];
    random.nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }
}
