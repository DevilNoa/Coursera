package org.example.services;

import org.example.core.Courses;
import org.example.db.CoursesDatabase;

import java.sql.SQLException;
import java.util.List;

public class CourseService {
    CoursesDatabase coursesDatabase;

    public CourseService(CoursesDatabase coursesDatabase) {
        this.coursesDatabase = coursesDatabase;
    }

    public List<Courses> getAllCoursesASList() {
        return coursesDatabase.getAllCoursesAsList();
    }

    public void addCourse(Courses newCourse) {
        coursesDatabase.createCourse(newCourse.getId_course(), newCourse.getId_instructor(), newCourse.getCourse_name(), newCourse.getTotal_time(), newCourse.getCredit());
    }

    public Courses getCourseByID(int id_course) {
        return (Courses) coursesDatabase.getCourseByID(id_course);
    }

    public Courses updateCourse(int id, Courses updatedCourse) {
        try {
            return coursesDatabase.updateCourse(id, updatedCourse);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating course by ID", e);
        }
    }
}