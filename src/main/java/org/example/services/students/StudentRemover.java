package org.example.services.students;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRemover {

    public void removeStudent(Connection connection, String id_students) {
        try {
            String sql = "DELETE FROM students WHERE id_students = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id_students);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("Student not found or removal failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing student");
            throw new RuntimeException(e);
        }
    }
}
