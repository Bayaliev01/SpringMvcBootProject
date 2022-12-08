package peaksoft.service;

import peaksoft.model.Instructor;

import java.io.IOException;
import java.util.List;

public interface InstructorService {
    List<Instructor> getAllInstructor(Long courseId);

    void addInstructor(Long id, Instructor instructor) throws IOException;

    Instructor getInstructorById(Long id);

    void updateInstructor(Instructor instructor, Long id) throws IOException;

    void deleteInstructor(Long id);

    void assignInstructor(Long courseId, Long instructorId);

    List<Instructor> listAllInstructors();
}