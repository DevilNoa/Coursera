package org.example.services.students;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentCreator {
    private String jdbcURL = "jdbc:postgresql://localhost:5433/postgres";
    private String username = "postgres";
    private String password = "admin";

    public void createStudent (String id_students,String firstNamme,String lastName){
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String sql = "INSERT INTO students (id_students, first_name, last_name)" +
                    "VALUES (" + id_students + ",'" + firstNamme + "','" + lastName + "')";
            Statement statement = connection.createStatement();
            statement.execute(sql);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error in connecting to server or creating student");
            throw new RuntimeException(e);
        }
    }
}
