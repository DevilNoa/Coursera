package org.example.services;

import java.sql.SQLException;
import java.util.List;
import org.example.core.Instructor;
import org.example.db.InstructorDatabase;

public class InstructorService {
  InstructorDatabase instructorDatabase;

  // Constructor to initialize InstructorService with InstructorDatabase instance
  public InstructorService(InstructorDatabase instructorDatabase) {
    this.instructorDatabase = instructorDatabase;
  }

  // Method for retrieving all the instructors as a list form the db
  public List<Instructor> getAllInstructorsASList() {
    return instructorDatabase.getAllInstructorsAsList();
  }

  // Method to add a new instructor to the db
  public void addInstructor(Instructor newInstructor) {
    instructorDatabase.createInstructor(
        newInstructor.getId(), newInstructor.getFirstName(), newInstructor.getLastName());
  }

  // Method to update an instructor by ID
  public Instructor updateInstructor(int id, Instructor updatedInstructor) {
    try {
      return instructorDatabase.updateInstructor(id, updatedInstructor);
    } catch (SQLException e) {
      throw new RuntimeException("Error updating instructor by ID", e);
    }
  }

  // Method to delete instructor by ID
  public boolean deleteInstructor(int id_instructors) {
    return instructorDatabase.deleteInstructor(id_instructors);
  }
}
