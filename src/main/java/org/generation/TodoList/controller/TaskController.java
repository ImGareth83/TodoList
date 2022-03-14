package org.generation.TodoList.controller;

import org.generation.TodoList.entity.Task;
import org.generation.TodoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class TaskController {

    private TaskService taskService;

    public TaskController(@Autowired TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getTasks(Model model){
        model.addAttribute("tasks",taskService.getTasks());
        model.addAttribute("newTask", new Task());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer Id){
        Task deletedTask=new Task();
        deletedTask.setId(Id);
        taskService.delete(deletedTask);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String save(@RequestParam(name="title", required = true) String title,
                     @RequestParam(name="description", required = true) String description,
                     @RequestParam(name="targetDateTime", required = true)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime)
    {
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setTargetDateTime(targetDateTime);
        t.setLastUpdateDateTime(LocalDateTime.now());
        taskService.save(t);
        return "redirect:/";
    }
}
