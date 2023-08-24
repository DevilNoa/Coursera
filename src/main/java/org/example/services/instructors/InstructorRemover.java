package org.example.services.instructors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstructorRemover {
    public void removeInstructor(Connection connection, Integer id_instructors) {
        try {
            String sql = "DELETE FROM instructors  WHERE id_instructors = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_instructors);
            int affectedRows = statement.executeUpdate();
            if (affectedRows >0){
                System.out.println("Instructor removed successfully");
            }else{
                System.out.println("Instructor not found or removal failed");
            }
        } catch (SQLException e) {
            System.out.println("Error removing student");
            throw new RuntimeException(e);
        }
    }
}
