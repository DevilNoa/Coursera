package org.example.db;

import org.example.core.Courses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDatabase {

    Connection connection;

    public CoursesDatabase(Connection connection) {
        this.connection = connection;
    }

    //Method to create a new course in the db
    public void createCourse(int id_courses, int id_instructor, String course_name, short total_time, short credit) throws SQLException {
        try {
            String sql = "INSERT INTO courses (id_courses, course_name, id_instructor, total_time, credits) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_courses);
            statement.setString(2, course_name);
            statement.setInt(3, id_instructor);
            statement.setShort(4, total_time);
            statement.setShort(5, credit);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error in creating course", e);
        }
    }

    //Method to print a list of all courses
    public List<Courses> getAllCoursesAsList() {
        List<Courses> coursesList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM courses";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id_courses = result.getInt("id_courses");
                String course_name = result.getString("course_name");
                int id_instructor = result.getInt("id_instructor");
                String time_created = result.getString("time_created");
                short total_time = result.getShort("total_time");
                short credit = result.getShort("credits");

                Courses course = new Courses(id_courses, course_name, id_instructor, time_created, total_time, credit);
                coursesList.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Error in retrieving Courses");
            e.printStackTrace(); // Print the exception stack trace for further debugging
            throw new RuntimeException(e);
        }
        return coursesList;
    }

    // Method to update course by ID
    public Courses updateCourse(int id, Courses updatedCourse) throws SQLException {
        try {
            String sql = "UPDATE courses SET course_name = ?, id_instructor = ?, total_time = ?, credits = ? WHERE id_courses = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedCourse.getCourse_name());
            statement.setInt(2, updatedCourse.getId_instructor());
            statement.setShort(3, updatedCourse.getTotal_time());
            statement.setShort(4, updatedCourse.getCredit());
            statement.setInt(5, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                // Use the updated values for id_courses and time_created
                return new Courses(id, updatedCourse.getCourse_name(), updatedCourse.getId_instructor(), updatedCourse.getTime_created(), updatedCourse.getTotal_time(), updatedCourse.getCredit());
            } else {
                return null; // Course not found
            }
        } catch (SQLException e) {
            System.out.println("Error updating course information");
            throw new RuntimeException(e);
        }
    }

    //Method to delete course by ID
    public boolean deleteCourse(Integer id_courses) {
        try {
            String sql = "DELETE FROM courses WHERE id_courses = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_courses);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error in deleting course");
            throw new RuntimeException(e);
        }
    }

}