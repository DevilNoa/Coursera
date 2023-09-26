package org.example.api;

import io.dropwizard.auth.Auth;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.Instructor;
import org.example.core.User;
import org.example.services.InstructorService;

@Path("/instructors")
@Produces(MediaType.APPLICATION_JSON)
public class InstructorResponse {

    private final InstructorService instructorService;

    public InstructorResponse(InstructorService instructorService) {
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
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Instructor not found or removal failed")
                    .build();
        }
    }

    // Endpoint to get all instructors with authentication
    @GET
    @PermitAll
    @Path("/get-all-instructors-with-login")
    public Response getAllInstructors(@Auth User authenticatedUser) {

        if (authenticatedUser != null) {
            return Response.ok(instructorService.getAllInstructorsASList()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        }
    }
}

