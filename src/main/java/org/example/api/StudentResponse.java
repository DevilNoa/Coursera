package org.example.api;

import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.Student;
import org.example.core.User;
import org.example.services.StudentService;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResponse {

    private final StudentService studentService;

    public StudentResponse(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
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
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found or update failed").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") String id) {
        boolean success = studentService.deleteStudent(id);
        if (success) {
            return Response.ok("Student deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found or delete failed").build();
        }
    }


    @GET
    @Path("/get-all-students-with-login")
    public String getAllStudents(@Auth User authenticatedUser) {

        if (authenticatedUser != null) {
            return studentService.getAllStudentsAsList().toString();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build().toString();

        }
    }

//    @PUT
//    @Path("/update-student-with-login/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateStudentWithLogin(
//            @PathParam("id") String id,
//            @Auth User authenticatedUser, // Ensure that @Auth is correctly set up
//            Student updatedStudent
//    ) {
//        // Check if the authenticated user is authorized to perform the update
//        if (authenticatedUser != null && isAuthenticatedUserAuthorized(authenticatedUser)) {
//            boolean success = studentService.updateStudent(id, updatedStudent);
//            if (success) {
//                return Response.ok("Student updated successfully").build();
//            } else {
//                return Response.status(Response.Status.NOT_FOUND)
//                        .entity("Student not found or update failed")
//                        .build();
//            }
//        } else {
//            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
//        }
//    }
//
//    private boolean isAuthenticatedUserAuthorized(User authenticatedUser) {
//        if (authenticatedUser != null) {
//            return true;
//        }
//        return false;
//    }

}
