package peaksoft.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task , Long> {


    @Query("select t from Task  t where t.lesson.id = :id")
    List<Task> findAllTaskById(Long id);
}
