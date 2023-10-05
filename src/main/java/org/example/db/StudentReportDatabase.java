package org.example.db;

import org.example.core.StudentReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentReportDatabase {
    private final Connection connection;

    public StudentReportDatabase(Connection connection) {
        this.connection = connection;
    }
    public List<StudentReport> getStudentCourseReport () throws SQLException{
        List<StudentReport> reportDataList= new ArrayList<>();
        String sql = "SELECT " +
                "CONCAT(s.first_name, ' ', s.last_name) AS student_name, " +
                "tc.total_credits AS total_credit, " +
                "c.course_name AS course_name, " +
                "c.total_time AS total_time, " +
                "CASE WHEN scx.completion_date IS NOT NULL THEN c.credits END AS credit_if_completed, " +
                "CONCAT(i.first_name, ' ', i.last_name) AS instructor_name " +
                "FROM students s " +
                "JOIN student_courses_xref scx ON s.id_students = scx.id_student " +
                "JOIN courses c ON scx.id_course = c.id_courses " +
                "JOIN instructors i ON c.id_instructor = i.id_instructors " +
                "LEFT JOIN (" +
                "    SELECT " +
                "        s.id_students, " +
                "        SUM(CASE WHEN scx.completion_date IS NOT NULL THEN c.credits ELSE 0 END) AS total_credits " +
                "    FROM " +
                "        students s " +
                "    JOIN " +
                "        student_courses_xref scx ON s.id_students = scx.id_student " +
                "    JOIN " +
                "        courses c ON scx.id_course = c.id_courses " +
                "    GROUP BY " +
                "        s.id_students " +
                ") tc ON s.id_students = tc.id_students " +
                "ORDER BY s.id_students, c.course_name";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                String studentName = resultSet.getString("student_name");
                int totalCredit = resultSet.getInt("total_credit");
                String courseName = resultSet.getString("course_name");
                int totalTime = resultSet.getInt("total_time");
                Integer creditIfCompleted = resultSet.getInt("credit_if_completed");
                String instructorName = resultSet.getString("instructor_name");

                StudentReport reportData = new StudentReport(studentName, totalCredit, courseName, totalTime, creditIfCompleted, instructorName);
                reportDataList.add(reportData);
            }

        }
        return reportDataList;
    }
}