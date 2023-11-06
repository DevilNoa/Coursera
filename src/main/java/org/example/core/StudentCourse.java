package org.example.core;

public class StudentCourse {
  String courseName;
  Short totalTime;
  Short courseCredits;

  String InstructorNames;

  public StudentCourse(
      String courseName, Short totalTime, Short courseCredits, String instructorNames) {
    this.courseName = courseName;
    this.totalTime = totalTime;
    this.courseCredits = courseCredits;
    InstructorNames = instructorNames;
  }

  public String getInstructorNames() {
    return InstructorNames;
  }

  public void setInstructorNames(String instructorNames) {
    InstructorNames = instructorNames;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public Short getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(Short totalTime) {
    this.totalTime = totalTime;
  }

  public Short getCourseCredits() {
    return courseCredits;
  }

  public void setCourseCredits(Short courseCredits) {
    this.courseCredits = courseCredits;
  }
}
