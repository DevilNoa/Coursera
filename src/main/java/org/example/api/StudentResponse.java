package org.example.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.Student;
import org.example.services.JwtService;
import org.example.services.StudentService;
import org.example.services.UserService;

import java.util.List;


@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResponse {

    private final StudentService studentService;

    public StudentResponse(StudentService studentService, UserService userService) {
        this.studentService = studentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents(@HeaderParam("Authorization") String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                List<Student> students = studentService.getAllStudentsAsList();
                return Response.ok(students).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@HeaderParam("Authorization") String token, Student newStudent) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                studentService.addStudent(newStudent);
                return Response.status(Response.Status.CREATED).build(); // Return a 201 Created status if the student is successfully added.
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    // Endpoint to update the student  by id
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStudent(@HeaderParam("Authorization") String token, @PathParam("id") String id, Student updatedStudent) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                boolean success = studentService.updateStudent(id, updatedStudent);
                if (success) {
                    return Response.ok("Student updated successfully").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Student not found or update failed").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    //Delete student by id
    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@HeaderParam("Authorization") String token, @PathParam("id") String id) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                boolean success = studentService.deleteStudent(id);
                if (success) {
                    return Response.ok("Student deleted successfully").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Student not found or deletion failed").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}