package org.example.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.StudentReport;
import org.example.services.StudentReportService;

import java.sql.SQLException;
import java.util.List;

@Path("/student-report")
public class StudentReportResponse {
    private final StudentReportService reportService;

    public StudentReportResponse(StudentReportService reportService) {
        this.reportService = reportService;
    }

    @GET
    @Path("/get-report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentCourseReport() {
        try {
            List<StudentReport> reportDataList = reportService.getStudentCourseReport();
            return Response.ok(reportDataList).build();
        } catch (SQLException e) {
            // Handle database errors here
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}