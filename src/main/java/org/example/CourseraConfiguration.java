package org.example;

import io.dropwizard.core.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CourseraConfiguration extends Configuration {
    private final Connection connection;

    public CourseraConfiguration() {
        try {
            String jdbcURL = "jdbc:postgresql://localhost:5433/postgres";
            String username = "postgres";
            String password = "admin";
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database server.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database server.");
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}