package org.example.services;

import org.example.db.StudentDatabase;


public class StudentService {
    StudentDatabase studentDatabase;

    public StudentService(StudentDatabase studentDatabase) {
        this.studentDatabase = studentDatabase;
    }

    public String getAll() {
        return studentDatabase.getAllStudents();
    }
}
