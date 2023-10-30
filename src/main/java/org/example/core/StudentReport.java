package org.example.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.sql.Timestamp;
import org.example.config.TimestampDeserializer;
import org.example.config.TimestampSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentReport {
  @JsonProperty("studentId")
  private String studentId;

  @JsonProperty("studentName")
  private String studentName;

  @JsonProperty("totalCredits")
  private int totalCredits;

  @JsonProperty("courseNames")
  private String[] courseNames;

  @JsonProperty("totalTimes")
  private Integer[] totalTimes;

  @JsonProperty("courseCredits")
  private Integer[] courseCredits;

  @JsonProperty("instructorNames")
  private String[] instructorNames;

  @JsonProperty("completionDate")
  @JsonSerialize(using = TimestampSerializer.class)
  @JsonDeserialize(using = TimestampDeserializer.class)
  private Timestamp completionDate;

  // Add getters and setters for all fields

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public int getTotalCredits() {
    return totalCredits;
  }

  public void setTotalCredits(int totalCredits) {
    this.totalCredits = totalCredits;
  }

  public String[] getCourseNames() {
    return courseNames;
  }

  public void setCourseNames(String[] courseNames) {
    this.courseNames = courseNames;
  }

  public Integer[] getTotalTimes() {
    return totalTimes;
  }

  public void setTotalTimes(Integer[] totalTimes) {
    this.totalTimes = totalTimes;
  }

  public Integer[] getCourseCredits() {
    return courseCredits;
  }

  public void setCourseCredits(Integer[] courseCredits) {
    this.courseCredits = courseCredits;
  }

  public String[] getInstructorNames() {
    return instructorNames;
  }

  public void setInstructorNames(String[] instructorNames) {
    this.instructorNames = instructorNames;
  }

  public Timestamp getCompletionDate() {
    return completionDate;
  }

  public void setCompletionDate(Timestamp completionDate) {
    this.completionDate = completionDate;
  }
}
