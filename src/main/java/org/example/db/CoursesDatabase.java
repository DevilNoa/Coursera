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

    //creating a new course in db
    public void createCourse(int id_courses, int id_instructor, String course_name, short total_time, short credit) {
        try {
            String sql = "INSERT INTO courses (id_courses, course_name, id_instructor, total_time, credit) VALUES (?, ?, ?,?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_courses);
            statement.setString(2, course_name);
            statement.setInt(3, id_instructor);
            statement.setShort(4, total_time);
            statement.setShort(5, credit);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error in creating course");
            throw new RuntimeException(e);
        }
    }

    //remove course by id
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

    //modify course by id
    public void courseAlter(int id_courses, String newCourseName, short newTotalTime, short newCredit) {
        try {
            String sql = "UPDATE courses SET course_name = ?, total_time = ?, credit = ? WHERE id_courses = ?";
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


    //printing courses
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
                String credit = result.getString("credit");
                String time_created = result.getString("time_created");

                System.out.printf("%s %s %s %s %s %s \n", id_course, course_name, id_instructor, total_time, credit, time_created);

            }
        } catch (SQLException e) {
            System.out.println("Error in printing Courses");
            throw new RuntimeException(e);
        }
    }

    //print course by id
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
                String credit = result.getString("credit");
                String time_created = result.getString("time_created");

                System.out.printf("%s %s %s %s %s %s \n", id_course, course_name, id_instructor, total_time, credit, time_created);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving course information");
            throw new RuntimeException(e);
        }
    }

    public List<Courses> getAllCoursesAsList() {
        List<Courses> coursesList = new ArrayList<>();
        try {
            String sql = "SELECT * from courses";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id_course = result.getInt("id_courses");
                String course_name = result.getString("course_name");
                int id_instructor = result.getInt("id_instructor");
                short total_time = result.getShort("total_time");
                short credit = result.getShort("credit");
                String time_created = result.getString("time_created");

                Courses course = new Courses(id_course, course_name, time_created, id_instructor, total_time, credit); // Corrected variable order
                coursesList.add(course); // Use the variable name coursesList
            }
        } catch (SQLException e) {
            System.out.println("Error in printing Courses");
            throw new RuntimeException(e);
        }
        return coursesList; // Use the variable name coursesList
    }

}

