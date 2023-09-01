package org.example.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.Student;
import org.example.services.StudentService;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResponse {

    private final StudentService studentService;

    public StudentResponse(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    public String getAllStudents() {
        // Call the method from the StudentService to get all students
        return studentService.getAllStudentsAsList().toString(); // Implement this method in StudentService
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addStudent(Student newStudent) {
        studentService.addStudent(newStudent);
    }

    @GET
    @Path("/{id}")
    public Response getStudentByID(@PathParam("id") String id) {
        Student student = studentService.getStudentByID(id);
        if (student != null) {
            return Response.ok(student).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student not found")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("id") String id, Student updatedStudent) {
        boolean success = studentService.updateStudent(id, updatedStudent);
        if (success) {
            return Response.ok("Student updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student not found or update failed")
                    .build();
        }
    }
}
