package org.example;

import org.example.services.instructors.InstructorCreator;
import org.example.services.instructors.InstructorPrintTable;
import org.example.services.instructors.InstructorRemover;
import org.example.services.students.StudentCreator;
import org.example.services.students.StudentPrintTable;
import org.example.services.students.StudentRemover;

public class Main {

    public static void main(String[] args) {
        CourseraConfiguration configuration = new CourseraConfiguration();
        StudentCreator studentCreator = new StudentCreator();
        StudentPrintTable studentPrintTable = new StudentPrintTable();
        StudentRemover studentRemover = new StudentRemover();
        InstructorCreator instructorCreator = new InstructorCreator();
        InstructorPrintTable instructorPrintTable = new InstructorPrintTable();
        InstructorRemover instructorRemover = new InstructorRemover();

        System.out.println("Database connection is active.");

        // Create a student
        studentCreator.createStudent(configuration.getConnection(), "13", "John", "Doe");

        // Print all students
        studentPrintTable.printAllStudents(configuration.getConnection());
        System.out.println("after removing the 13th student");
        // Remove a student
        studentRemover.removeStudent(configuration.getConnection(), "13");
        // Print all students
        studentPrintTable.printAllStudents(configuration.getConnection());

        // Create a instructor
        System.out.println("Creating/Removing instructor");
        instructorCreator.createInstructor(configuration.getConnection(), 3,"Go6o","Lo6o");
        // Print all instructor
        instructorPrintTable.printAllInstructors(configuration.getConnection());
        System.out.println("after removing instructor");
        // Removing instructor
        instructorRemover.removeInstructor(configuration.getConnection(), 3);
        // Print all instructor
        instructorPrintTable.printAllInstructors(configuration.getConnection());
    }
}
