package org.example.api;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.core.Student;
import org.example.services.StudentService;

import java.util.List;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentDatabase;

    public StudentResource(StudentService studentService) {
        this.studentDatabase = studentService;
    }

    @GET
    public List<Student> getAllStudents() {
        // Call the method from the StudentDatabase to get all students
        return studentDatabase.getAllStudentsAsList(); // Implement this method in StudentDatabase
    }
}