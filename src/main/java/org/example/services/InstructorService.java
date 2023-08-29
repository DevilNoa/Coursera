package org.example.services;

import org.example.core.Instructor;
import org.example.db.InstructorDatabase;

import java.util.List;

public class InstructorService {
    InstructorDatabase instructorDatabase;

    public InstructorService(InstructorDatabase instructorDatabase) {
        this.instructorDatabase = instructorDatabase;
    }


    public List<Instructor> getAllInstructorsASList() {
        return instructorDatabase.getAllInstructorsAsList();
    }
}
