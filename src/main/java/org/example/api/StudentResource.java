package org.example.api;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.services.StudentService;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    public String getAllStudents() {
        // Call the method from the StudentService to get all students
        return studentService.getAllStudents(); // Implement this method in StudentService
    }

}