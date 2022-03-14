package org.generation.TodoList.controller;

import org.generation.TodoList.entity.Task;
import org.generation.TodoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
Setup MockMvc to Test Your Spring MVC @Controller and @RestController in Isolation

 */

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(@Autowired TaskService taskService) {
        this.taskService = taskService;
    }


    @CrossOrigin
    @GetMapping("/all")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @CrossOrigin
    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id")Integer Id){
        Task deletedTask=new Task();
        deletedTask.setId(Id);
        taskService.delete(deletedTask);
        return new RedirectView("/");
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/add")
    public RedirectView save(@RequestParam(name="title", required = true) String title,
                     @RequestParam(name="description", required = true) String description,
                     @RequestParam(name="targetDateTime", required = true)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime)
    {
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);

//        LocalDateTime date = LocalDateTime.parse(targetDateTime, DateTimeFormatter.ISO_DATE_TIME);

        t.setTargetDateTime(targetDateTime);
        t.setLastUpdateDateTime(LocalDateTime.now());
        taskService.save(t);
        return new RedirectView("/all");
    }

    @GetMapping
    public List<Task> getTasks(Model model) {
        return taskService.getTasks();
    }

    @PostMapping
    public Task save(Task task) {
        return taskService.save(task);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable("id")Integer Id){
        Task deletedTask=new Task();
        deletedTask.setId(Id);
        taskService.delete(deletedTask);
    }


}
