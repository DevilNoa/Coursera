package org.example.api;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.services.StudentService;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentDatabase;

    public StudentResource(StudentService studentService) {
        this.studentDatabase = studentService;
    }

    @GET
    public String getAllStudents() {
        // Call the method from the StudentDatabase to get all students
        return studentDatabase.getAll(); // Implement this method in StudentDatabase
    }
}