package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.model.Project;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
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
    private final EmployeeService employeeService; // Tilføjet EmployeeService

    public TaskController(TaskService taskService, ProjectService projectService, SubtaskService subtaskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.subtaskService = subtaskService;
        this.employeeService = employeeService;
    }

    // viser formular til at oprette en ny task
    @GetMapping("/tasks/addTask")
    public String showCreateTaskForm(@RequestParam("projectID") int projectID, Model model) {
        Project project = projectService.getProjectById(projectID);
        List<Employee> employees = employeeService.getAllEmployees(); // Hent alle medarbejdere

        model.addAttribute("task", new Task());
        model.addAttribute("project", project);
        model.addAttribute("employees", employees); // Tilføj medarbejdere til modellen
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("status", Status.values());
        return "addTask";
    }

    // håndterer oprettelsen og redigeringen af en task
    @PostMapping("/tasks/edit")
    public String updateTask(@ModelAttribute Task task, @RequestParam("employeeID") int employeeID) {
        taskService.updateTask(task); // Opdater task-data
        taskService.assignEmployeeToTask(task.getTaskID(), employeeID); // Tildel eller opdater medarbejder
        return "redirect:/project/" + task.getProjectID();
    }

    // Sletter en task
    @GetMapping("/tasks/delete/{taskID}")
    public String deleteTask(@PathVariable int taskID, @RequestParam int projectID) {
        taskService.deleteTaskById(taskID);
        return "redirect:/project/" + projectID;
    }

    // viser formularen til at redigere en task
    @GetMapping("/tasks/edit/{taskID}")
    public String showEditTaskForm(@PathVariable int taskID, Model model) {
        Task task = taskService.getTaskById(taskID);
        List<Employee> employees = employeeService.getAllEmployees();
        Integer currentEmployeeID = taskService.getAssignedEmployeeID(taskID); // Få aktuelt tildelt medarbejder

        model.addAttribute("task", task);
        model.addAttribute("employees", employees);
        model.addAttribute("currentEmployeeID", currentEmployeeID); // Tilføj aktuelt tildelt medarbejder til modellen
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("status", Status.values());
        return "editTask";
    }

    @GetMapping("/tasks/{taskID}")
    public String getTaskDetails(@PathVariable int taskID, Model model) {
        try {
            Task task = taskService.getTaskById(taskID);
            List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(taskID);
            if (subtasks == null) {
                subtasks = new ArrayList<>();
            }
            model.addAttribute("task", task);
            model.addAttribute("subtasks", subtasks);
            return "taskDetails";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
    }
}
