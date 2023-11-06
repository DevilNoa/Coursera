package org.example.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
  private String id, firstName, lastName, timeCreated;

  @JsonCreator
  public Student(
      @JsonProperty("id") String id,
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName,
      @JsonProperty("timeCreated") String timeCreated) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.timeCreated = timeCreated;
  }

  public Student() {}

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public String toString() {
    return String.format(
        "Student[id=%s, firstName='%s', lastName='%s', timeCreated='%s']",
        id, firstName, lastName, timeCreated);
  }
}
