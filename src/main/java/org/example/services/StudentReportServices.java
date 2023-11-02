package org.example.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import org.example.core.StudentReport;
import org.example.db.StudentReportDatabase;

public class StudentReportServices {
  private final StudentReportDatabase studentReportDatabase;

  public StudentReportServices(StudentReportDatabase studentReportDatabase) {
    this.studentReportDatabase = studentReportDatabase;
  }


  public List<StudentReport> createStudentReport(
          String studentIdStr, Timestamp startDate, Timestamp endDate, short minimumCredits)
          throws SQLException {
    List<StudentReport> reports = new ArrayList<>();
    String[] studentIds = null;

    if (studentIdStr != null) {
      studentIds = studentIdStr.split(",\\s*");
    }

    ResultSet resultSet =
            studentReportDatabase.createStudentReport(studentIds, startDate, endDate, minimumCredits);

    while (resultSet.next()) {
      StudentReport report = new StudentReport();
      report.setStudentName(resultSet.getString("student_name"));
      report.setTotalCredits(resultSet.getInt("total_credits"));
      report.setCourseNames(resultSet.getString("course_names").split(", "));
      report.setTotalTimes(
              Arrays.stream(resultSet.getString("total_times").split(", "))
                      .map(Integer::parseInt)
                      .toArray(Integer[]::new));
      report.setInstructorNames(resultSet.getString("instructor_names").split(", "));
      reports.add(report);
    }

    return reports;
  }

}
