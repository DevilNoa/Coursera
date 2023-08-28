package org.example;

import org.example.services.InstructorService;
import org.example.services.StudentService;

/**
 * demonstration for crud apperceptions with student and instructor
 */


public class Main {

    public static void main(String[] args) {
        CourseraConfiguration configuration = new CourseraConfiguration();
        StudentService studentService = new StudentService();
        InstructorService instructorService = new InstructorService();

        System.out.println("Database connection is active.");
        studentService.printAllStudents(configuration.getConnection());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        // Create a student
        studentService.createStudent(configuration.getConnection(), "13", "John", "Doe");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //changing student info
        studentService.studentAlter(configuration.getConnection(), "13", "some","ome");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //printing only a student by id
        studentService.printStudentByID(configuration.getConnection(), "13");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        // Print all students
        studentService.printAllStudents(configuration.getConnection());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("after removing the 13th student");
        // Remove a student
        studentService.removeStudent(configuration.getConnection(), "13");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // Print all students
        studentService.printAllStudents(configuration.getConnection());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // Create a instructor
        System.out.println("Creating/Removing instructor");
        instructorService.createInstructor(configuration.getConnection(), 3, "Go6o", "Lo6o");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // Print all instructor
        instructorService.printAllInstructors(configuration.getConnection());
        System.out.println("after removing instructor");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // Removing instructor
        instructorService.removeInstructor(configuration.getConnection(), 3);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // Print all instructor
        instructorService.printAllInstructors(configuration.getConnection());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //modifying instructor by id
        instructorService.instructorAlter(configuration.getConnection(), 2, "Stef", "Curry");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //print instructor by id
        instructorService.printInstructorByID(configuration.getConnection(), 2);
    }
}
