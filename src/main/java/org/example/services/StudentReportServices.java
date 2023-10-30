package org.example.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.example.core.StudentReport;
import org.example.db.StudentReportDatabase;

public class StudentReportServices {
    private StudentReportDatabase studentReportDatabase;

    public StudentReportServices(StudentReportDatabase studentReportDatabase) {
        this.studentReportDatabase = studentReportDatabase;
    }

    public List<StudentReport> createStudentReport(String[] studentId, Timestamp startDate, Timestamp endDate, short minimumCredits) throws SQLException {
        List<StudentReport> reports = new ArrayList<>();
        ResultSet resultSet = studentReportDatabase.createStudentReport(studentId, startDate, endDate, minimumCredits);
        while (resultSet.next()) {
            StudentReport report = new StudentReport();
            report.setStudentId(resultSet.getString("student_id"));
            report.setStudentName(resultSet.getString("student_name"));
            report.setTotalCredits(resultSet.getInt("total_credits"));
            report.setCourseNames((String[]) resultSet.getArray("course_names").getArray());
            report.setTotalTimes((Integer[]) resultSet.getArray("total_times").getArray());
            report.setCourseCredits((Integer[]) resultSet.getArray("course_credits").getArray());
            report.setInstructorNames((String[]) resultSet.getArray("instructor_names").getArray());
            report.setCompletionDate(resultSet.getTimestamp("completion_date"));

            reports.add(report);
        }
        return reports;
    }
}
