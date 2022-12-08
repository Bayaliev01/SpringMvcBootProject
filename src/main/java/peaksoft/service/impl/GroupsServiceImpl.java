package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import peaksoft.model.*;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.GroupsRepository;
import peaksoft.repository.StudentRepository;
import peaksoft.service.GroupsService;

import java.io.IOException;
import java.util.List;

@Service
public class GroupsServiceImpl implements GroupsService {
    private final GroupsRepository groupsRepository;
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GroupsServiceImpl(GroupsRepository groupsRepository, CourseRepository courseRepository, CompanyRepository companyRepository, StudentRepository studentRepository) {
        this.groupsRepository = groupsRepository;
        this.courseRepository = courseRepository;
        this.companyRepository = companyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Groups> getAllGroup(Long id) {
        return groupsRepository.findAllGroups(id);
    }

    @Override
    public List<Groups> getAllGroupsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).get();
        List<Groups> groups = course.getGroups();
        groups.forEach(System.out::println);
        return groups;
    }

    @Override
    public void addGroupByCourseId(Long id, Long courseId, Groups group) {
        Company company = companyRepository.findById(id).get();
        Course course = courseRepository.findById(courseId).get();
        company.addGroup(group);
        group.setCompany(company);
        group.addCourse(course);
        course.addGroup(group);
        companyRepository.save(company);
        courseRepository.save(course);
    }

    @Override
    public void addGroup(Long id, Groups group) {
        Company company = companyRepository.findById(id).get();
        company.addGroup(group);
        group.setCompany(company);
        groupsRepository.save(group);
    }

    @Override
    public Groups getGroupById(Long id) {
        return groupsRepository.findById(id).orElseThrow(() -> new RuntimeException(" Group with id : " + id + "Not Found"));
    }

    @Override
    public void updateGroup(Groups group, Long id) {
        Groups group1 = getGroupById(id);
        group1.setGroupName(group.getGroupName());
        group1.setDateOfStart(group.getDateOfStart());
        group1.setImage(group.getImage());
        groupsRepository.save(group1);
    }

    @Override
    public void deleteGroup(Long id) {
        Groups group = groupsRepository.findById(id).get();
        for (Student s : group.getStudents()) {
            group.getCompany().minusStudent();
        }
        for (Course c : group.getCourses()) {
            for (Student student : group.getStudents()) {
                for (Instructor i : c.getInstructors()) {
                    i.minus();
                }
            }
        }
        for (Course c : group.getCourses()) {
            c.getGroups().remove(group);
            group.minusCount();
        }
        studentRepository.deleteAll(group.getStudents());
        group.setCourses(null);
        groupsRepository.delete(group);
    }


    @Override
    public void assignGroup(Long courseId, Long groupId) throws IOException {
        Groups group = getGroupById(groupId);
        Course course = courseRepository.findById(courseId).get();
        if (course.getGroups() != null) {
            for (Groups g : course.getGroups()) {
                if (g.getId() == groupId) {
                    throw new IOException("This group already exists!");
                }
            }
        }
        group.addCourse(course);
        course.addGroup(group);
        groupsRepository.save(group);
        courseRepository.save(course);
    }

}
