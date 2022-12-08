package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Course;
import peaksoft.model.Instructor;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.InstructorRepository;
import peaksoft.service.InstructorService;

import java.io.IOException;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository, CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
    }


    @Override
    public List<Instructor> getAllInstructor(Long courseId) {
        return instructorRepository.findAllInstructor(courseId);
    }

    @Override
    public void addInstructor(Long id, Instructor instructor) throws IOException {
        List<Instructor> instructors = instructorRepository.addInstructor();
        for (Instructor i : instructors) {
            if (i.getEmail().equals(instructor.getEmail())) {
                throw new IOException("Инструктор с электронной почтой уже существует!");
            }
        }
        Course course = courseRepository.findById(id).get();
        course.addInstructors(instructor);
        instructor.setCourse(course);
        courseRepository.save(course);
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id).get();
    }

    @Override
    public void updateInstructor(Instructor instructor, Long id) throws IOException {
        Instructor instructor1 = instructorRepository.findById(id).get();
        instructor1.setFirstName(instructor.getFirstName());
        instructor1.setLastName(instructor.getLastName());
        instructor1.setEmail(instructor.getEmail());
        instructor1.setSpecialization(instructor.getSpecialization());
        instructor1.setPhoneNumber(instructor.getPhoneNumber());
        instructorRepository.save(instructor1);
    }

    @Override
    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }

    @Override
    public void assignInstructor(Long courseId, Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId).get();
        Course course = courseRepository.findById(courseId).get();
        instructor.setCourse(course);
        course.addInstructors(instructor);
        instructorRepository.save(instructor);
        courseRepository.save(course);

    }

    @Override
    public List<Instructor> listAllInstructors() {
        return instructorRepository.addInstructor();
    }
}
