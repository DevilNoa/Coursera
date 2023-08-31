package org.example.services;

import org.example.core.Student;
import org.example.db.StudentDatabase;

import java.sql.SQLException;
import java.util.List;


public class StudentService {
    StudentDatabase studentDatabase;

    public StudentService(StudentDatabase studentDatabase) {
        this.studentDatabase = studentDatabase;
    }


    public List<Student> getAllStudentsAsList() {
        return studentDatabase.getAllStudentsAsList();
    }

    public void addStudent(Student newStudent) {
        studentDatabase.createStudent(newStudent.getId(), newStudent.getFirstName(), newStudent.getLastName());
    }

    public Student getStudentByID(String id) {
        try {
            return studentDatabase.getStudentByID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving student by ID ", e);
        }
    }

    public boolean updateStudent(String id, Student updateStudent) {
        try {
            return studentDatabase.updateStudent(id, updateStudent);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student ID", e);
        }
    }
}
