package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Company;
import peaksoft.model.Course;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.service.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final CompanyRepository companyRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository repository, CompanyRepository companyRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<Course> getAllCourses(Long id) {
        return repository.findAllCourses(id);
    }

    @Override
    public void addCourse(Long id, Course course) {
        Company company = companyRepository.findById(id).get();
        company.addCourse(course);
        course.setCompany(company);
        repository.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void updateCourse(Course course, Long id) {
        Course course1 = repository.findById(id).get();
        course1.setCourseName(course.getCourseName());
        course1.setDuration(course.getDuration());
        course1.setDescription(course.getDescription());
        repository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        course.setCompany(null);
        repository.delete(course);
    }
}
