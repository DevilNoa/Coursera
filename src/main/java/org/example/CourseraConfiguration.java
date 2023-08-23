package org.example;

import io.dropwizard.core.Configuration;

import java.sql.*;


public class CourseraConfiguration extends Configuration {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:postgresql://localhost:5433/postgres";
        String username = "postgres";
        String password = "admin";
//checking the connection to the server
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            System.out.println("connected to server");

            connection.close();
        } catch (SQLException e) {
            System.out.println("error in connecting to server");
            throw new RuntimeException(e);
        }
    }
}
