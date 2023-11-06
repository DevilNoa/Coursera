package org.example.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtService {

  private static final String SECRET_KEY = "theBestKeptSecretIsToPretendThatThereIsntOne";
  private static final String ISSUER = "send_help_pls";

  public static String generateToken(String username) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
      JWTCreator.Builder builder = JWT.create().withClaim("username", username).withIssuer(ISSUER);

      return builder.sign(algorithm);
    } catch (Exception e) {
      return null;
    }
  }

  public static boolean verifyToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
      JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
