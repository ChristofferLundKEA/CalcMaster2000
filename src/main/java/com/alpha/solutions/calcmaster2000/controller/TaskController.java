package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Project;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.service.ProjectService;
import com.alpha.solutions.calcmaster2000.service.SubtaskService;
import com.alpha.solutions.calcmaster2000.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final SubtaskService subtaskService;

    public TaskController(TaskService taskService, ProjectService projectService, SubtaskService subtaskService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.subtaskService = subtaskService;
    }

    // Viser en formular til at oprette en ny task
    @GetMapping("/tasks/addTask")
    public String showCreateTaskForm(@RequestParam("projectID") int projectID, Model model) {
        Project project = projectService.getProjectById(projectID); // Hent projekt baseret på ID
        model.addAttribute("task", new Task(0, 0, "", "", "", 0, "", false, 0));
        model.addAttribute("project", project);
        return "addTask"; // Thymeleaf-skabelon til at oprette en task
    }

    // Håndterer oprettelsen af en ny task
    @PostMapping("/tasks/addTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.createTask(task); // Gem task i databasen
        return "redirect:/project/" + task.getProjectID(); //
    }

    //Sletter en task
    @GetMapping("/tasks/delete/{taskID}")
    public String deleteTask(@PathVariable int taskID, @RequestParam int projectID) {
        taskService.deleteTaskById(taskID);
        return "redirect:/project/" + projectID;
    }

    //opdatere en task
    @GetMapping("/tasks/edit/{taskID}")
    public String showEditTaskForm(@PathVariable int taskID, Model model) {
        Task task = taskService.getTaskById(taskID); // Hent task baseret på ID
        model.addAttribute("task", task); //Tilføj task  til modellen
        return "editTask"; // Thymeleaf-skabelon til at redigere task
    }

    @PostMapping("/tasks/edit")
    public String updateTask(@ModelAttribute Task task) {
        taskService.updateTask(task); // Opdater task i databasen
        return "redirect:/project/" + task.getProjectID();
    }

    @GetMapping("/tasks/{taskID}")
    public String getTaskDetails(@PathVariable int taskID, Model model) {
        try {
            // Hent tasken baseret på task-ID
            Task task = taskService.getTaskById(taskID);

            // Hent subtasks for denne task
            List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(taskID);
            if (subtasks == null) {
                subtasks = new ArrayList<>();
            }

            // Tilføj data til modellen for Thymeleaf
            model.addAttribute("task", task);
            model.addAttribute("subtasks", subtasks);

            return "taskDetails"; // Returner Thymeleaf-skabelonen "taskDetails.html"
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Returner en fejlside, hvis der opstår en fejl
        }
    }



}
