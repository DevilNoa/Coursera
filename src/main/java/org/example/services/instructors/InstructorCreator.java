package org.example.services.instructors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstructorCreator {
    public void createInstructor(Connection connection, Integer id_instructors, String firstName, String lastName) {
        try {
            String sql = "INSERT INTO instructors (id_instructors, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_instructors);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating student");
            throw new RuntimeException(e);
        }
    }
}
