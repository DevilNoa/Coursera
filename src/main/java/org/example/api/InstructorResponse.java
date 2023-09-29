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

@Path("/instructors")
@Produces(MediaType.APPLICATION_JSON)
public class InstructorResponse {

    private final InstructorService instructorService;


    public InstructorResponse(InstructorService instructorService, UserService userService) {
        this.instructorService = instructorService;
    }

    // Endpoint to get all instructors
    @GET
    public String getAllInstructors() {
        return instructorService.getAllInstructorsASList().toString();
    }

    // Endpoint to add a new instructor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addInstructor(Instructor newInstructor) {
        instructorService.addInstructor(newInstructor);
    }

    // Endpoint to get an instructor by ID
    @GET
    @Path("/{id}")
    public Response getInstructorByID(@PathParam("id") int id) {
        Instructor instructor = instructorService.getInstructorByID(id);

        if (instructor != null) {
            return Response.ok(instructor).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Instructor not found").build();
        }
    }

    // Endpoint to update an instructor by ID
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateInstructor(@PathParam("id") int id, Instructor updatedInstructor) {
        Instructor instructor = instructorService.updateInstructor(id, updatedInstructor);

        if (instructor != null) {
            return Response.ok(instructor).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Instructor not found").build();
        }
    }

    // Endpoint to remove an instructor by ID
    @DELETE
    @Path("/{id}")
    public Response removeInstructor(@PathParam("id") int id_instructors) {
        boolean success = instructorService.deleteInstructor(id_instructors);
        if (success) {
            return Response.ok("Instructor removed successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Instructor not found or removal failed").build();
        }
    }

    // Endpoint to get all instructors with authentication
    @GET
    @Path("/get-all-instructors-with-login")
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
    @Path("/new-course-login")
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
    @Path("/update-instructors-login/{id}")
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
    @Path("/delete-instructors-login/{id}")
    public Response deleteInstructor(@HeaderParam("Authorization") String token, @PathParam("id") int id_instructors) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                boolean success = instructorService.deleteInstructor(id_instructors);
                if (success) {
                    return Response.ok("Course deleted successfully").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Course not found or deletion failed").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}