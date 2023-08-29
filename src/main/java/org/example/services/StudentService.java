package org.example.services;

import org.example.core.Student;
import org.example.db.StudentDatabase;

import java.util.List;


public class StudentService {
    StudentDatabase studentDatabase;

    public StudentService(StudentDatabase studentDatabase) {
        this.studentDatabase = studentDatabase;
    }

//    public String getAll() {
//        return studentDatabase.getAllStudents();
//    }

    public List<Student> getAllStudentsAsList() {
        return studentDatabase.getAllStudentsAsList();
    }
}
