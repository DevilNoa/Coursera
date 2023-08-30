package org.example.services;

import org.example.core.Courses;
import org.example.db.CoursesDatabase;

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
}
