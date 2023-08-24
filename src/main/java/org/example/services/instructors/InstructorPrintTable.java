package org.example.services.instructors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InstructorPrintTable {
    public void printAllInstructors(Connection connection) {
        try {
            String sql = "SELECT * FROM instructors";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String id = result.getString("id_instructors");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_created");

                System.out.printf("%s-%s-%s-%s-\n", id, firstName, lastName, timeCreated);
            }
        } catch (SQLException e) {
            System.out.println("Error receiving Instructors");
            throw new RuntimeException(e);
        }
    }
}
