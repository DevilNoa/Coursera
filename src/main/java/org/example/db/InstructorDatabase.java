package org.example.db;

import org.example.core.Instructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorDatabase {
    Connection connection;

    public InstructorDatabase(Connection connection) {
        this.connection = connection;
    }


    //Method to create an instructor in the db
    public void createInstructor(Integer id_instructors, String firstName, String lastName) {
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


    // Method to remove an instructor from the database by ID with output to the console
    public void removeInstructor(Integer id_instructors) {
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

    //Method to modify an instructor's information by ID with output to the console
    public void instructorAlter(Integer id_instructors, String newFirstName, String newLastName) {
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

    //Method to print an instructor by ID with output to the console
    public void printInstructorByID(Integer id_instructors) {
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


    //Method to print an instructor by ID with output to the console
    public void printAllInstructors() {
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

    //Method to print a list of all instructors
    public List<Instructor> getAllInstructorsAsList() {
        List<Instructor> instructors = new ArrayList<>();
        try {
            String sql = "SELECT * FROM instructors";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Integer id = result.getInt("id_instructors");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_created"); // Fetch the time_created column

                //Create a new Instructor object and add it to the list
                Instructor instructor = new Instructor(id, firstName, lastName, timeCreated);
                instructors.add(instructor);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving instructors");
            throw new RuntimeException(e);
        }

        return instructors;
    }

    //Method to retrieve an instructor by ID
    public Instructor getInstructorByID(int id_instructors) throws SQLException {
        try {
            String sql = "SELECT * FROM instructors WHERE id_instructors = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_instructors);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Integer id = result.getInt("id_instructors");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_created");

                return new Instructor(id, firstName, lastName, timeCreated);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving instructor by ID");
            throw new RuntimeException(e);
        }
    }

    // Method to update instructor by ID
    public Instructor updateInstructor(int id, Instructor updatedInstructor) throws SQLException {
        try {
            String sql = "UPDATE instructors SET first_name = ?, last_name = ? WHERE id_instructors = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedInstructor.getFirstName());
            statement.setString(2, updatedInstructor.getLastName());
            statement.setInt(3, id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                return new Instructor(id, updatedInstructor.getFirstName(), updatedInstructor.getLastName(), "timestamp_placeholder");
            } else {
                return null; // Instructor not found
            }
        } catch (SQLException e) {
            System.out.println("Error updating instructor information");
            throw new RuntimeException(e);
        }
    }

    //Method to delete instructor by ID
    public boolean deleteInstructor(int id_instructors) {
        try {
            String sql = "DELETE FROM instructors  WHERE id_instructors = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_instructors);
            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error removing instructor", e);
        }
    }
}