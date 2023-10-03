package org.example.services;

import org.example.core.Student;
import org.example.db.StudentDatabase;

import java.sql.SQLException;
import java.util.List;


public class StudentService {
    StudentDatabase studentDatabase;

    //Constructor to initialize StudentService with StudentDatabase instance
    public StudentService(StudentDatabase studentDatabase) {
        this.studentDatabase = studentDatabase;
    }

    //Method for retrieving all tje students as a list form the db
    public List<Student> getAllStudentsAsList() {
        return studentDatabase.getAllStudentsAsList();
    }

    //Method to add a new student to the db
    public void addStudent(Student newStudent) {
        studentDatabase.createStudent(newStudent.getId(), newStudent.getFirstName(), newStudent.getLastName());
    }

    //Method to update a student by ID
    public boolean updateStudent(String id, Student updateStudent) {
        try {
            return studentDatabase.updateStudent(id, updateStudent);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student ID", e);
        }
    }

    //Method to delete student by ID
    public boolean deleteStudent(String id) {
        return studentDatabase.deleteStudent(id);
    }
}
