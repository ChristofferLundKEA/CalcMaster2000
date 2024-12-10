package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
import com.alpha.solutions.calcmaster2000.service.SubtaskService;
import com.alpha.solutions.calcmaster2000.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final TaskService taskService;
    private final EmployeeService employeeService; // Tilføj EmployeeService

    public SubtaskController(SubtaskService subtaskService, TaskService taskService, EmployeeService employeeService) {
        this.subtaskService = subtaskService;
        this.taskService = taskService;
        this.employeeService = employeeService; // Initialiser EmployeeService
    }

    // viser formularen til at oprette en ny subtask
    @GetMapping("/subtask/addSubtask")
    public String showCreateSubtaskForm(@RequestParam("taskID") int taskID, Model model) {
        List<Employee> employees = employeeService.getAllEmployees(); // Hent alle medarbejdere

        model.addAttribute("subtask", new Subtask());
        model.addAttribute("taskID", taskID);
        model.addAttribute("employees", employees); // Tilføj medarbejdere til modellen
        model.addAttribute("status", Status.values());
        model.addAttribute("priorities", Priority.values());
        return "addSubtask"; // Thymeleaf-skabelon til at oprette en subtask
    }

    // håndterer oprettelsen af en ny subtask
    @PostMapping("/subtask/addSubtask")
    public String createSubtask(@ModelAttribute Subtask subtask, @RequestParam("employeeID") int employeeID) {
        subtaskService.createSubtask(subtask); // Gem subtask i databasen
        subtaskService.assignEmployeeToSubtask(subtask.getSubtaskID(), employeeID); // Tildel medarbejder
        return "redirect:/tasks/" + subtask.getTaskID();
    }

    // sletter en subtask
    @GetMapping("/subtasks/delete/{subtaskID}")
    public String deleteSubtask(@PathVariable int subtaskID, @RequestParam("taskID") int taskID) {
        subtaskService.deleteSubtaskById(subtaskID);
        return "redirect:/tasks/" + taskID; // Redirect tilbage til den tilhørende task
    }

    // Opdaterer en subtask
    @GetMapping("/subtasks/edit/{subtaskID}")
    public String showEditSubtaskForm(@PathVariable int subtaskID, Model model) {
        Subtask subtask = subtaskService.getSubtaskById(subtaskID);
        List<Employee> employees = employeeService.getAllEmployees();
        Integer currentEmployeeID = subtaskService.getAssignedEmployeeID(subtaskID); // Få aktuelt tildelt medarbejder

        model.addAttribute("subtask", subtask);
        model.addAttribute("employees", employees); // Tilføj medarbejdere til modellen
        model.addAttribute("currentEmployeeID", currentEmployeeID); // Tilføj aktuelt tildelt medarbejder
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("status", Status.values());
        return "editSubtask"; // Returner Thymeleaf-skabelonen
    }

    // håndterer opdateringen af en subtask
    @PostMapping("/subtasks/edit")
    public String updateSubtask(@ModelAttribute Subtask subtask, @RequestParam("employeeID") int employeeID) {
        subtaskService.updateSubtask(subtask); // Opdater subtask i databasen
        subtaskService.assignEmployeeToSubtask(subtask.getSubtaskID(), employeeID); // Tildel eller opdater medarbejder
        return "redirect:/tasks/" + subtask.getTaskID();
    }
}
