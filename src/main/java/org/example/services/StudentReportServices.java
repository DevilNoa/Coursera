package org.example.services;

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

    String[] studentIds = null;

    if (studentIdStr != null) {
      studentIds = studentIdStr.split(",\\s*");
    }

    return studentReportDatabase.createStudentReport(
        studentIds, startDate, endDate, minimumCredits);
  }
}
