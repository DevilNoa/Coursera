package org.example.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.Instructor;
import org.example.services.InstructorService;
import org.example.services.JwtService;
import org.example.services.UserService;


import java.util.List;

@Path("/instructor")
@Produces(MediaType.APPLICATION_JSON)
public class InstructorResponse {

    private final InstructorService instructorService;


    public InstructorResponse(InstructorService instructorService, UserService userService) {
        this.instructorService = instructorService;
    }

    // Endpoint to get all instructors with authentication
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInstructors(@HeaderParam("Authorization") String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                List<Instructor> instructors = instructorService.getAllInstructorsASList();
                return Response.ok(instructors).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    //endpoint to create a new instructor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInstructor(@HeaderParam("Authorization") String token, Instructor newInstructor) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                instructorService.addInstructor(newInstructor);
                return Response.status(Response.Status.CREATED).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    // Endpoint to update the instructor by id
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateInstructors(@HeaderParam("Authorization") String token, @PathParam("id") int id, Instructor updatedInstructor) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            Instructor instructor = instructorService.updateInstructor(id, updatedInstructor);

            if (instructor != null) {
                return Response.ok(instructor).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    //Endpoint to delete the instructor by id
    @DELETE
    @Path("/{id}")
    public Response deleteInstructor(@HeaderParam("Authorization") String token, @PathParam("id") int id_instructors) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                boolean success = instructorService.deleteInstructor(id_instructors);
                if (success) {
                    return Response.ok("Instructor removed successfully").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Instructor not found or deletion failed").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}