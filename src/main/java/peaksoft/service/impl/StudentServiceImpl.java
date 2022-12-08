package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Course;
import peaksoft.model.Groups;
import peaksoft.model.Instructor;
import peaksoft.model.Student;
import peaksoft.repository.GroupsRepository;
import peaksoft.repository.StudentRepository;
import peaksoft.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupsRepository groupsRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, GroupsRepository groupsRepository) {
        this.studentRepository = studentRepository;
        this.groupsRepository = groupsRepository;
    }

    @Override
    public List<Student> getAllStudents(Long id) {
        return studentRepository.findAllStudent(id);
    }

    @Override
    public void addStudent(Long id, Student student) {
        Groups group = groupsRepository.findById(id).get();
        group.addStudent(student);
        student.setGroups(group);
        studentRepository.save(student);
        for (Course c : student.getGroups().getCompany().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.plus();
            }
        }
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public void updateStudent(Student student, Long id) {
        Student student1 = studentRepository.findById(id).get();
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setEmail(student.getEmail());
        student1.setStudyFormat(student.getStudyFormat());
        studentRepository.save(student1);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        student.getGroups().getCompany().minusStudent();
        for (Course c : student.getGroups().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.minus();
            }
        }
        student.setGroups(null);
        studentRepository.deleteById(id);
    }

    @Override
    public void assignStudent(Long groupId, Long studentId) {
        Student student = studentRepository.findById(studentId).get();
        Groups group = groupsRepository.findById(groupId).get();
        group.addStudent(student);
        student.setGroups(group);
        studentRepository.save(student);
    }

    @Override
    public List<Student> listAllStudents() {
        return studentRepository.findAllStudentById();
    }
}
