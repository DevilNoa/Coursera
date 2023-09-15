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
    public void createCourse(int id_courses, int id_instructor, String course_name, short total_time, short credit) {
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
            System.out.println("Error in creating course");
            throw new RuntimeException(e);
        }
    }


    // Method to remove a course from the database by ID with output to the console
    public void removeCourse(int id_courses) {
        try {
            String sql = "DELETE FROM public.courses WHERE id_courses = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_courses);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Course removed successfully");
            } else {
                System.out.println("Course not found or removal failed");
            }
        } catch (SQLException e) {
            System.out.println("Error in removing course");
            throw new RuntimeException(e);
        }
    }

    //Method to modify a course's information by ID with output to the console
    public void courseAlter(int id_courses, String newCourseName, short newTotalTime, short newCredit) {
        try {
            String sql = "UPDATE courses SET course_name = ?, total_time = ?, credits = ? WHERE id_courses = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newCourseName);
            statement.setShort(2, newTotalTime);
            statement.setShort(3, newCredit);
            statement.setInt(4, id_courses);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Course information updated successfully");
            } else {
                System.out.println("Course not found or update failed");
            }
        } catch (SQLException e) {
            System.out.println("Error updating course information");
            throw new RuntimeException(e);
        }
    }


    //Method to print  a courses by ID with output to the console
    public void printAllCourses() {
        try {
            String sql = "SELECT * from courses";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                String id_course = result.getString("id_courses");
                String course_name = result.getString("course_name");
                String id_instructor = result.getString("id_instructor");
                String total_time = result.getString("total_time");
                String credit = result.getString("credits");
                String time_created = result.getString("time_created");

                System.out.printf("%s %s %s %s %s %s \n", id_course, course_name, id_instructor, total_time, credit, time_created);

            }
        } catch (SQLException e) {
            System.out.println("Error in printing Courses");
            throw new RuntimeException(e);
        }
    }

    //Method to print a courses by ID with output to the console
    public void printCourseByID(int id_courses) {
        try {
            String sql = "SELECT * from courses where id_courses = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_courses);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String id_course = result.getString("id_courses");
                String course_name = result.getString("course_name");
                String id_instructor = result.getString("id_instructor");
                String total_time = result.getString("total_time");
                String credit = result.getString("credits");
                String time_created = result.getString("time_created");

                System.out.printf("%s %s %s %s %s %s \n", id_course, course_name, id_instructor, total_time, credit, time_created);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving course information");
            throw new RuntimeException(e);
        }
    }

    //Method to print a list of all courses
    public List<Courses> getAllCoursesAsList() {
        List<Courses> coursesList = new ArrayList<>();
        try {
            String sql = "SELECT * from courses";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id_courses = result.getInt("id_courses");
                String course_name = result.getString("course_name");
                int id_instructor = result.getInt("id_instructor");
                String time_created = result.getString("time_created"); // Make sure this matches the data type in your database
                short total_time = result.getShort("total_time");
                short credit = result.getShort("credits");

                //Creating a course object and add it to the list
                Courses course = new Courses(id_courses, course_name, id_instructor, time_created, total_time, credit);
                coursesList.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Error in printing Courses");
            throw new RuntimeException(e);
        }
        return coursesList;
    }

    //Method to retrieve a course by ID
    public Courses getCourseByID(int id_course) {
        try {
            String sql = "SELECT * FROM courses WHERE id_courses = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_course);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int id_courses = result.getInt("id_courses");
                String course_name = result.getString("course_name");
                int id_instructor = result.getInt("id_instructor");
                String time_created = result.getString("time_created"); // Ensure this matches the data type in your database
                short total_time = result.getShort("total_time");
                short credit = result.getShort("credits");

                return new Courses(id_courses, course_name, id_instructor, time_created, total_time, credit);
            } else {
                return null; // Course not found
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving course by ID");
            throw new RuntimeException(e);
        }
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
    public boolean deleteCourse(int id_courses) {
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