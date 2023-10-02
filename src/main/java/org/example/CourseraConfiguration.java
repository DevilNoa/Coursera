package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import org.example.config.JwtConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CourseraConfiguration extends Configuration {
    //Declaring connection to the database
    private final Connection connection;
    //Declaring a JwtConfiguration object
    private JwtConfiguration jwtConfiguration;

    //Constructor for initializing thr configuration
    public CourseraConfiguration() {
        try {
            //DB connection parameters
            String jdbcURL = "jdbc:postgresql://localhost:5433/postgres";
            String username = "postgres";
            String password = "admin";

            //Establishing db connection using JDBC
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database server.");
        } catch (SQLException e) {
            //Handling SQLException that may occur during connection to db
            System.out.println("Error connecting to the database server.");
            throw new RuntimeException(e);
        }
    }

    @JsonProperty("jwtConfiguration")
    public JwtConfiguration getJwtConfiguration() {
        return jwtConfiguration;
    }


    public Connection getConnection() {
        return connection;
    }
}