package org.example.services;

import org.example.core.Student;
import org.example.db.StudentDatabase;

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
}
