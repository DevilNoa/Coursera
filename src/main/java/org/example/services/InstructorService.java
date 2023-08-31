package org.example.services;

import org.example.core.Instructor;
import org.example.db.InstructorDatabase;

import java.sql.SQLException;
import java.util.List;

public class InstructorService {
    InstructorDatabase instructorDatabase;

    public InstructorService(InstructorDatabase instructorDatabase) {
        this.instructorDatabase = instructorDatabase;
    }


    public List<Instructor> getAllInstructorsASList() {
        return instructorDatabase.getAllInstructorsAsList();
    }

    public void addInstructor(Instructor newInstructor) {
        instructorDatabase.createInstructor(newInstructor.getId(), newInstructor.getFirstName(), newInstructor.getLastName());
    }

    public Instructor getInstructorByID(int id) {
        try {
            return instructorDatabase.getInstructorByID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving instructor by ID", e);
        }
    }

    public Instructor updateInstructor(int id, Instructor updatedInstructor) {
        try {
            return instructorDatabase.updateInstructor(id, updatedInstructor);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating instructor by ID", e);
        }
    }
}
