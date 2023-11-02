package org.example.db;

import java.sql.*;

public class StudentReportDatabase {

    private final Connection connection;

    public StudentReportDatabase(Connection connection) {
        this.connection = connection;
    }

    public ResultSet createStudentReport(String[] studentIds, Timestamp startDate, Timestamp endDate, short minimumCredits) throws SQLException {
        try {
            String sql = "SELECT " +
                    "s.id_students AS student_id, " +
                    "s.first_name || ' ' || s.last_name AS student_name, " +
                    "SUM(c.credits) AS total_credits, " +
                    "STRING_AGG(c.course_name, ', ') AS course_names, " +
                    "STRING_AGG(c.total_time::VARCHAR, ', ') AS total_times, " +
                    "STRING_AGG(i.first_name || ' ' || i.last_name, ', ') AS instructor_names " +
                    "FROM " +
                    "students s " +
                    "JOIN " +
                    "student_courses_xref scx ON s.id_students = scx.id_student " +
                    "JOIN " +
                    "courses c ON scx.id_course = c.id_courses " +
                    "JOIN " +
                    "instructors i ON c.id_instructor = i.id_instructors " +
                    "WHERE " +
                    "(s.id_students = ANY(?) OR ? IS NULL) " +
                    "AND " +
                    "scx.completion_date BETWEEN ? AND ? " +
                    "GROUP BY " +
                    "s.id_students " +
                    "HAVING " +
                    "SUM(c.credits) >= ? " +
                    "ORDER BY " +
                    "s.id_students";

            PreparedStatement statement = connection.prepareStatement(sql);
            Array studentIdsArray = connection.createArrayOf("VARCHAR", studentIds);
            statement.setArray(1, studentIdsArray);
            statement.setArray(2, studentIdsArray); //WHERE
            statement.setTimestamp(3, startDate);
            statement.setTimestamp(4, endDate);
            statement.setShort(5, minimumCredits);
            return statement.executeQuery();
        } catch (SQLException e) {
         //   e.printStackTrace();
            throw new SQLException("Error in creating Report", e);
        }
    }
}
