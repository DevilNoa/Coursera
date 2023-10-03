package org.example.db;

import org.example.core.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {

    Connection connection;

    public StudentDatabase(Connection connection) {
        this.connection = connection;
    }


    //creating a new student inside the db
    public void createStudent(String id_students, String firstName, String lastName) {
        try {
            String sql = "INSERT INTO students (id_students, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id_students);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating student");
            throw new RuntimeException(e);
        }
    }

    //Method to get a list of students
    public List<Student> getAllStudentsAsList() {
        List<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT * FROM students";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String id = result.getString("id_students");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_created");

                Student student = new Student(id, firstName, lastName, timeCreated);
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students");
            throw new RuntimeException(e);
        }

        return students;
    }


    // Method to update a student's information
    public boolean updateStudent(String id, Student updatedStudent) throws SQLException {
        try {
            String sql = "UPDATE students SET first_name = ?, last_name = ? WHERE id_students = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedStudent.getFirstName());
            statement.setString(2, updatedStudent.getLastName());
            statement.setString(3, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating student information");
            throw new RuntimeException(e);
        }
    }

    // Method to delete a student by ID
    public boolean deleteStudent(String id) {
        try {
            String sql = "DELETE FROM students WHERE id_students = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing student");
            throw new RuntimeException(e);
        }
    }
}