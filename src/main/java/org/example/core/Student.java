package org.example.core;

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private final String timeCreated;


    public Student(String id, String firstName, String lastName, String timeCreated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return String.format("Student[id=%s, firstName='%s', lastName='%s', timeCreated='%s']", id, firstName, lastName, timeCreated);
    }
}
