package org.generation.TodoList.service;

import org.generation.TodoList.entity.Task;
import org.generation.TodoList.entity.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(org.generation.TodoList.entity.TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks(){
        return StreamSupport.stream(
                taskRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @Transactional
    public Task save(Task t){
        return taskRepository.save(t);
    }

    public void delete(Task t){
        taskRepository.delete(t);
    }
}
