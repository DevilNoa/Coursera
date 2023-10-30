package org.example.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.example.core.StudentReport;
import org.example.services.JwtService;
import org.example.services.StudentReportServices;
import org.example.services.UserService;

@Path("/student-report")
@Produces(MediaType.APPLICATION_JSON)
public class StudentReportResponse {
    private final StudentReportServices studentReportServices;

    public StudentReportResponse(StudentReportServices studentReportServices, UserService userService) {
        this.studentReportServices = studentReportServices;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudentReport(@HeaderParam("Authorization") String token,
                                        @QueryParam("studentId") String[] studentId,
                                        @QueryParam("startDate") String startDateStr,
                                        @QueryParam("endDate") String endDateStr,
                                        @QueryParam("minimumCredits") short minimumCredits) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            String jwtToken = token.substring("Bearer ".length()).trim();

            if (!JwtService.verifyToken(jwtToken)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            if (startDateStr == null || endDateStr == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Missing or null parameters").build();
            }

            List<StudentReport> studentReports = studentReportServices.createStudentReport(studentId, Timestamp.valueOf(startDateStr), Timestamp.valueOf(endDateStr), minimumCredits);
            return Response.ok(studentReports).build();
        } catch (JWTVerificationException | IllegalArgumentException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
