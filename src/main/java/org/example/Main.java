package org.example;

//
//import org.example.db.CoursesDatabase;
//import org.example.db.InstructorDatabase;
//import org.example.db.StudentDatabase;
//
//import javax.sql.DataSource;
//
///**
// * demonstration for crud apperceptions with student and instructor
// */
//
//
//public class Main {
//
//    public static void main(String[] args) {
//        CourseraConfiguration configuration = new CourseraConfiguration();
//        StudentDatabase studentDatabase = new StudentDatabase(configuration.getConnection());
//        InstructorDatabase instructorDatabase = new InstructorDatabase(configuration.getConnection());
//        CoursesDatabase coursesDatabase = new CoursesDatabase((DataSource) configuration.getConnection());
//
//        System.out.println("Database connection is active.");
//        studentDatabase.printAllStudents();
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        // Create a student
//        studentDatabase.createStudent("13", "John", "Doe");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //changing student info
//        studentDatabase.studentAlter("13", "some", "ome");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //printing only a student by id
//        studentDatabase.printStudentByID("13");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        // Print all students
//        studentDatabase.printAllStudents();
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println("after removing the 13th student");
//        // Remove a student
//        studentDatabase.removeStudent("13");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        // Print all students
//        studentDatabase.printAllStudents();
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        // Create a instructor
//        System.out.println("Creating/Removing instructor");
//        instructorDatabase.createInstructor(3, "Go6o", "Lo6o");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        // Print all instructor
//        instructorDatabase.printAllInstructors();
//        System.out.println("after removing instructor");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        // Removing instructor
//        instructorDatabase.removeInstructor(3);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        // Print all instructor
//        instructorDatabase.printAllInstructors();
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        //modifying instructor by id
//        instructorDatabase.instructorAlter(2, "Stef", "Curry");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        //print instructor by id
//        instructorDatabase.printInstructorByID(2);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //print all courses
//        coursesDatabase.printAllCourses();
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //add new course
//        coursesDatabase.createCourse(2, 2, "test", (short) 90, (short) 3);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //print new course by id
//        coursesDatabase.printCourseByID(2);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //modify the new course
//        coursesDatabase.courseAlter(2, "tester", (short) 91, (short) 4);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //print the same course by id
//        coursesDatabase.printCourseByID(2);
//
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //delete course
//        coursesDatabase.removeCourse(2);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        //print all courses
//        coursesDatabase.printAllCourses();
//    }
//}
//public class Main {
//    public static void main(String[] args) {
//        String password = "userPasswordd"; // The user's password
//
//        // Hash the password when registering a user
//        String hashedPassword = PasswordHashingUtil.hashPassword(password);
//
//        // Verify the password during login
//        boolean isPasswordCorrect = PasswordHashingUtil.verifyPassword(password, hashedPassword);
//
//        if (isPasswordCorrect) {
//            System.out.println("Password is correct!");
//        } else {
//            System.out.println("Password is incorrect.");
//        }
//    }
//
//}