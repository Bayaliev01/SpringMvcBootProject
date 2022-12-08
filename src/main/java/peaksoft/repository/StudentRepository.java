package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s where  s.groups.id = :id")
    List<Student> findAllStudent(Long id);


    @Query("select s from Student s ")
    List<Student> findAllStudentById();
}
