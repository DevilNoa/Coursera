package org.example.core;

public class Instructor {
    private int id;

    private String firstName;
    private String lastName;
    private String timeCreated;

    public Instructor() {
    }

    public Instructor(Integer id, String firstName, String lastName, String timeCreated) {
        this.id = Integer.parseInt(String.valueOf(id));
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    public int getId() {
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
        return String.format("Instructor[id=%d, firstName='%s', lastName='%s', timeCreated='%s']", id, firstName, lastName, timeCreated);
    }
}

