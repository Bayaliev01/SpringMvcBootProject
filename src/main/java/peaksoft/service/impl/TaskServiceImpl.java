package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Lesson;
import peaksoft.model.Task;
import peaksoft.repository.LessonRepository;
import peaksoft.repository.TaskRepository;
import peaksoft.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, LessonRepository lessonRepository) {
        this.taskRepository = taskRepository;
        this.lessonRepository = lessonRepository;
    }


    @Override
    public List<Task> getAllTasks(Long id) {
        return taskRepository.findAllTaskById(id);
    }

    @Override
    public void addTask(Long id, Task task) {
        Lesson lesson = lessonRepository.findById(id).get();
        lesson.addTask(task);
        task.setLesson(lesson);
        lessonRepository.save(lesson);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public void updateTask(Task task, Long id) {
        Task task1 = getTaskById(id);
        task1.setTaskName(task.getTaskName());
        task1.setTaskText(task.getTaskText());
        task1.setDeadLine(task.getDeadLine());
        taskRepository.save(task1);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
