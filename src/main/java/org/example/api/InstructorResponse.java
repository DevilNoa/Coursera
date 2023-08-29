package org.example.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.services.InstructorService;

@Path("/instructors")
@Produces(MediaType.APPLICATION_JSON)
public class InstructorResponse {

    private final InstructorService instructorDatabase;

    public InstructorResponse(InstructorService instructorDatabase) {
        this.instructorDatabase = instructorDatabase;
    }

    @GET
    public String getAllInstructors() {
        // Call the method from the StudentDatabase to get all students
        return instructorDatabase.getAllInstructorsASList().toString(); // Implement this method in StudentDatabase
    }
}
