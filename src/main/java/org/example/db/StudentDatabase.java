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


    //print all students
    public void printAllStudents() {
        try {
            String sql = "SELECT * FROM students";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String id = result.getString("id_students");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_created");

                System.out.printf("%s-%s-%s-%s-\n", id, firstName, lastName, timeCreated);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students");
            throw new RuntimeException(e);
        }
    }

    //creating a new student
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

    //removing a student
    public void removeStudent(String id_students) {
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

    //changing the info for the student by id
    public void studentAlter(String id_students, String newFirstName, String newLastName) {
        try {
            String sql = "UPDATE students SET first_name = ?, last_name = ? WHERE id_students = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newFirstName);
            statement.setString(2, newLastName);
            statement.setString(3, id_students);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Student information updated successfully");
            } else {
                System.out.println("Student not found or update failed");
            }
        } catch (SQLException e) {
            System.out.println("Error updating student information");
            throw new RuntimeException(e);
        }
    }


    //print student by id
    public void printStudentByID(String id_students) {
        try {
            String sql = "SELECT * FROM students WHERE id_students = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id_students);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String id = result.getString("id_students");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_created");

                System.out.printf("%s-%s-%s-%s-\n", id, firstName, lastName, timeCreated);
            } else {
                System.out.println("Student not found");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving student information");
            throw new RuntimeException(e);
        }
    }

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

    //using get request printing student by id
    public Student getStudentByID(String id_students) throws SQLException {
        try {
            String sql = "SELECT * FROM students WHERE id_students = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id_students); // Set the string value directly
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String id = result.getString("id_students");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String timeCreated = result.getString("time_created");

                return new Student(id, firstName, lastName, timeCreated);
            } else {
                return null; // Return null if student not found
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving student by ID");
            throw new RuntimeException(e);
        }
    }

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