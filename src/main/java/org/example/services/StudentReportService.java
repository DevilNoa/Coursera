package org.example.services;

import org.example.core.StudentReport;
import org.example.db.StudentReportDatabase;

import java.sql.SQLException;
import java.util.List;

public class StudentReportService {
    private final StudentReportDatabase reportDatabase;

    public StudentReportService(StudentReportDatabase reportDatabase) {
        this.reportDatabase = reportDatabase;
    }

    public List<StudentReport> getStudentCourseReport() throws SQLException {
        try {
            // Your database query code here
            List<StudentReport> reportDataList = reportDatabase.getStudentCourseReport();
            return reportDataList;
        } catch (SQLException e) {
            // Handle the exception or rethrow it
            e.printStackTrace(); // You can replace this with proper error handling
            throw e; // Rethrow the exception to propagate it
        }
    }
}
