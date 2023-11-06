package org.example.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.example.core.StudentCourse;
import org.example.core.StudentReport;

public class StudentReportDatabase {

  private final Connection connection;

  public StudentReportDatabase(Connection connection) {
    this.connection = connection;
  }

  public List<StudentReport> createStudentReport(
      String[] studentIds, Timestamp startDate, Timestamp endDate, short minimumCredits)
      throws SQLException {
    try {
      String sql =
          "SELECT "
              + "s.id_students AS student_id, "
              + "s.first_name || ' ' || s.last_name AS student_name, "
              + "SUM(c.credits) AS total_credits, "
              + "array_agg(c.course_name) AS course_names, "
              + "array_agg(c.total_time) AS total_times, "
              + "array_agg(i.first_name || ' ' || i.last_name) AS instructor_names, "
              + "array_agg(c.credits) AS course_credits "
              + "FROM "
              + "students s "
              + "JOIN "
              + "student_courses_xref scx ON s.id_students = scx.id_student "
              + "JOIN "
              + "courses c ON scx.id_course = c.id_courses "
              + "JOIN "
              + "instructors i ON c.id_instructor = i.id_instructors "
              + "WHERE "
              + "(s.id_students = ANY(?) OR ? IS NULL) "
              + "AND "
              + "scx.completion_date BETWEEN ? AND ? "
              + "GROUP BY "
              + "s.id_students "
              + "HAVING "
              + "SUM(c.credits) >= ? "
              + "ORDER BY "
              + "s.id_students";

      PreparedStatement statement = connection.prepareStatement(sql);
      Array studentIdsArray = connection.createArrayOf("VARCHAR", studentIds);
      statement.setArray(1, studentIdsArray);
      statement.setArray(2, studentIdsArray); // WHERE
      statement.setTimestamp(3, startDate);
      statement.setTimestamp(4, endDate);
      statement.setShort(5, minimumCredits);

      ResultSet resultSet = statement.executeQuery();
      List<StudentReport> reports = new ArrayList<>();

      //      while (resultSet.next()) {
      //        StudentReport report = new StudentReport();
      //        report.setStudentName(resultSet.getString("student_name"));
      //        report.setTotalCredits(resultSet.getInt("total_credits"));
      //
      //        report.setCourseNames((String[]) resultSet.getArray("course_names").getArray());
      //        report.setTotalTimes((Short[]) resultSet.getArray("total_times").getArray());
      //        report.setCourseCredits((Short[]) resultSet.getArray("course_credits").getArray());
      //        report.setInstructorNames((String[])
      // resultSet.getArray("instructor_names").getArray());
      //
      //        List<StudentCourse> zipped =
      //            IntStream.range(0, report.getCourseNames().length)
      //                .mapToObj(
      //                    i ->
      //                        new StudentCourse(
      //                            report.getCourseNames()[i],
      //                            report.getTotalTimes()[i],
      //                            report.getCourseCredits()[i],
      //                            report.getInstructorNames()[i]))
      //                .collect(Collectors.toList());
      //
      //        report.setCourses(zipped);
      //
      //        reports.add(report);
      //
      while (resultSet.next()) {
        StudentReport report = new StudentReport();
        report.setStudentName(resultSet.getString("student_name"));
        report.setTotalCredits(resultSet.getInt("total_credits"));

        String[] courseNames = (String[]) resultSet.getArray("course_names").getArray();
        Short[] totalTimes = (Short[]) resultSet.getArray("total_times").getArray();
        Short[] courseCredits = (Short[]) resultSet.getArray("course_credits").getArray();
        String[] instructorNames = (String[]) resultSet.getArray("instructor_names").getArray();

        List<StudentCourse> zipped =
            IntStream.range(0, courseNames.length)
                .mapToObj(
                    i ->
                        new StudentCourse(
                            courseNames[i], totalTimes[i], courseCredits[i], instructorNames[i]))
                .collect(Collectors.toList());

        report.setCourses(zipped);

        reports.add(report);
      }

      return reports;
    } catch (SQLException e) {
      //   e.printStackTrace();
      throw new SQLException("Error in creating Report", e);
    }
  }
}
