package org.example.core;

public class Instructor {
    private final Integer id;
    private final String firstName, lastName, timeCreated;

    public Instructor(Integer id, String firstName, String lastName, String timeCreated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return String.format("Instructor[id=%d, firstName='%s', lastName='%s', timeCreated='%s']", id, firstName, lastName, timeCreated);
    }
}

