package org.example.services.students;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentPrintTable {
    private String jdbcURL = "jdbc:postgresql://localhost:5433/postgres";
    private String username = "postgres";
    private String password = "admin";

    public void printAllStudents() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String sql = "SELECT * FROM students";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String id = result.getString("id_students");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_create");

                System.out.printf("%s-%s-%s-%s-\n", id, firstName, lastName, timeCreated);
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error in connecting to server");
            throw new RuntimeException(e);
        }
    }
}