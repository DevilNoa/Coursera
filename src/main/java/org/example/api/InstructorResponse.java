package org.example.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.core.Instructor;
import org.example.services.InstructorService;

@Path("/instructors")
@Produces(MediaType.APPLICATION_JSON)
public class InstructorResponse {

    private final InstructorService instructorService;

    public InstructorResponse(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GET
    public String getAllInstructors() {
        return instructorService.getAllInstructorsASList().toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addInstructor(Instructor newInstructor) {
        instructorService.addInstructor(newInstructor);
    }
}
