package org.example.services;

import java.sql.SQLException;
import java.util.List;
import org.example.core.Courses;
import org.example.db.CoursesDatabase;

public class CourseService {
  CoursesDatabase coursesDatabase;

  // Constructor to initialize CourseService with CoursesDatabase instance
  public CourseService(CoursesDatabase coursesDatabase) {
    this.coursesDatabase = coursesDatabase;
  }

  // Method for retrieving all the courses as a list form the db
  public List<Courses> getAllCoursesASList() {
    return coursesDatabase.getAllCoursesAsList();
  }

  // Method to add a new course to the db
  public void addCourse(Courses newCourse) throws SQLException {
    coursesDatabase.createCourse(
        newCourse.getId_courses(),
        newCourse.getId_instructor(),
        newCourse.getCourse_name(),
        newCourse.getTotal_time(),
        newCourse.getCredit());
  }

  // Method to update a course by ID
  public Courses updateCourse(int id, Courses updatedCourse) {
    try {
      return coursesDatabase.updateCourse(id, updatedCourse);
    } catch (SQLException e) {
      throw new RuntimeException("Error updating course by ID", e);
    }
  }

  // Method to delete course by ID
  public boolean deleteCourse(int courseId) {
    return coursesDatabase.deleteCourse(courseId);
  }
}
