package org.example.services.students;

public class test {
    public static void main(String[] args) {
        StudentCreator studentCreator = new StudentCreator();
        //studentCreator.createStudent("13", "John", "Doe");

        StudentPrintTable studentPrintTable = new StudentPrintTable();
        studentPrintTable.printAllStudents();
    }
}
