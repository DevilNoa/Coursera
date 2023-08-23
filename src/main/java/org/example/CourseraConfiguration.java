package org.example;

import io.dropwizard.core.Configuration;

import java.sql.*;


public class CourseraConfiguration extends Configuration {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:postgresql://localhost:5433/postgres";
        String username = "postgres";
        String password = "admin";
//checking the connection to the server
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            System.out.println("connected to server");


            /*a quick test if I can add a new row to the students table*/
            /*
            String sql = "INSERT INTO students (id_students, first_name, last_name)" + "VALUES (12,'go6o0','lo6o0')";
            Statement = connection.createStatement();
            statement.execute(sql);
            */

            /*quick test how to print a table from sql*/
            /*
            String sql = "SELECT * FROM students";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                String id_students = result.getString("id_students");
                String first_name= result.getString("first_name");
                String last_name = result.getString("last_name");
                String time_create = result.getString("time_create");
                System.out.printf("%s-%s-%s-%s-\n",id_students,first_name,last_name,time_create);
            }
            */

            connection.close();
        } catch (SQLException e) {
            System.out.println("error in connecting to server");
            throw new RuntimeException(e);
        }
    }
}
