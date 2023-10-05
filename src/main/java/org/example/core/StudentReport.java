package org.example.core;

public class StudentReport {
    private String studentName;
    private int totalCredit;
    private String courseName;
    private int totalTime;
    private Integer creditIfCompleted;
    private String instructorName;

    public StudentReport(String studentName, int totalCredit, String courseName, int totalTime, Integer creditIfCompleted, String instructorName) {
        this.studentName = studentName;
        this.totalCredit = totalCredit;
        this.courseName = courseName;
        this.totalTime = totalTime;
        this.creditIfCompleted = creditIfCompleted;
        this.instructorName = instructorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public Integer getCreditIfCompleted() {
        return creditIfCompleted;
    }

    public String getInstructorName() {
        return instructorName;
    }
}
