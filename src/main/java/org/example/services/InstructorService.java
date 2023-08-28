package org.example.services;

import java.sql.*;

public class InstructorService {

    //creating and instructor in the db
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


    //removing instructor by id
    public void removeInstructor(Connection connection, Integer id_instructors) {
        try {
            String sql = "DELETE FROM instructors  WHERE id_instructors = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_instructors);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Instructor removed successfully");
            } else {
                System.out.println("Instructor not found or removal failed");
            }
        } catch (SQLException e) {
            System.out.println("Error removing student");
            throw new RuntimeException(e);
        }
    }

    //modify instructor by id
    public void instructorAlter(Connection connection, Integer id_instructors, String newFirstName, String newLastName) {
        try {
            String sql = "UPDATE instructors SET first_name = ?, last_name = ? WHERE id_instructors = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newFirstName);
            statement.setString(2, newLastName);
            statement.setInt(3, id_instructors);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Instructor information updated successfully");
            } else {
                System.out.println("Instructor not found or update failed");
            }
        } catch (SQLException e) {
            System.out.println("Error updating instructor information");
            throw new RuntimeException(e);
        }
    }

    //print instructor by id

    public void printInstructorByID(Connection connection, Integer id_instructors) {
        try {
            String sql = "SELECT * FROM instructors WHERE id_instructors = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_instructors);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String id = result.getString("id_instructors");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_created");

                System.out.printf("%s-%s-%s-%s-\n", id, firstName, lastName, timeCreated);
            } else {
                System.out.println("Instructor not found");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving instructor information");
            throw new RuntimeException(e);
        }
    }


    //printing the list of the instructors
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