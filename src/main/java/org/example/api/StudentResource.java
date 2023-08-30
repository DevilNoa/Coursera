package org.example.api;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.core.Student;
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
        return studentDatabase.getAllStudentsAsList().toString(); // Implement this method in StudentDatabase
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addStudent(Student newStudent) {
        studentDatabase.addStudent(newStudent);
    }

}