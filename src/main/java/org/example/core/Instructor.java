package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Instructor {
    private final Integer id;

    @JsonProperty("firstName")
    private final String firstName;

    @JsonProperty("lastName")
    private final String lastName;
    private final String timeCreated;

    public Instructor(@JsonProperty("id") Integer id, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("timeCreated") String timeCreated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    public Integer getId() {
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

