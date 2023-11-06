package org.example.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import org.example.core.StudentReport;
import org.example.services.JwtService;
import org.example.services.StudentReportServices;
import org.example.services.UserService;

@Path("/student-report")
@Produces(MediaType.APPLICATION_JSON)
public class StudentReportResponse {
  private final StudentReportServices studentReportServices;

  public StudentReportResponse(
      StudentReportServices studentReportServices, UserService userService) {
    this.studentReportServices = studentReportServices;
  }

  @GET
  public Response createStudentReport2(
      @HeaderParam("Authorization") String token,
      @QueryParam("studentId") String studentIdStr,
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

      if (studentIdStr != null && studentIdStr.isEmpty() || Objects.equals(studentIdStr, "null")) {
        studentIdStr = null; // Set to null if the string is empty
      }
      if (startDateStr == null || endDateStr == null) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity("Missing or null parameters")
            .build();
      }
      Timestamp startDate = Timestamp.valueOf(startDateStr);
      Timestamp endDate = Timestamp.valueOf(endDateStr);

      List<StudentReport> studentReports =
          studentReportServices.createStudentReport(
              studentIdStr, startDate, endDate, minimumCredits);
      return Response.ok(studentReports).build();
    } catch (JWTVerificationException | IllegalArgumentException | SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }
}
