package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
import com.alpha.solutions.calcmaster2000.service.SubtaskService;
import com.alpha.solutions.calcmaster2000.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final TaskService taskService;
    private final EmployeeService employeeService;

    public SubtaskController(SubtaskService subtaskService, TaskService taskService, EmployeeService employeeService) {
        this.subtaskService = subtaskService;
        this.taskService = taskService;
        this.employeeService = employeeService;
    }

    // Viser formularen til at oprette en ny subtask
    @GetMapping("/subtask/addSubtask")
    public String showCreateSubtaskForm(@RequestParam("taskID") int taskID, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        List<Employee> employees = employeeService.getAllEmployees();
        session.setAttribute("currentTaskID", taskID);

        model.addAttribute("subtask", new Subtask());
        model.addAttribute("taskID", taskID);
        model.addAttribute("employees", employees);
        model.addAttribute("status", Status.values());
        model.addAttribute("priorities", Priority.values());
        return "addSubtask";
    }

    // Håndterer oprettelsen af en ny subtask
    @PostMapping("/subtask/addSubtask")
    public String createSubtask(@ModelAttribute Subtask subtask, @RequestParam("employeeID") int employeeID, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Integer currentTaskID = (Integer) session.getAttribute("currentTaskID");
        if (currentTaskID != null) {
            subtask.setTaskID(currentTaskID);
        }
        subtaskService.createSubtask(subtask);
        subtaskService.assignEmployeeToSubtask(subtask.getSubtaskID(), employeeID);
        return "redirect:/tasks/" + subtask.getTaskID();
    }

    // Sletter en subtask
    @GetMapping("/subtasks/delete/{subtaskID}")
    public String deleteSubtask(@PathVariable int subtaskID, @RequestParam("taskID") int taskID, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        subtaskService.deleteSubtaskById(subtaskID);
        session.removeAttribute("currentSubtaskID");
        return "redirect:/tasks/" + taskID;
    }

    // Opdaterer en subtask
    @GetMapping("/subtasks/edit/{subtaskID}")
    public String showEditSubtaskForm(@PathVariable int subtaskID, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Subtask subtask = subtaskService.getSubtaskById(subtaskID);
        List<Employee> employees = employeeService.getAllEmployees();
        Integer currentEmployeeID = subtaskService.getAssignedEmployeeID(subtaskID);

        session.setAttribute("currentSubtaskID", subtaskID);
        model.addAttribute("subtask", subtask);
        model.addAttribute("employees", employees);
        model.addAttribute("currentEmployeeID", currentEmployeeID);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("status", Status.values());
        return "editSubtask";
    }

    // Håndterer opdateringen af en subtask
    @PostMapping("/subtasks/edit")
    public String updateSubtask(@ModelAttribute Subtask subtask, @RequestParam("employeeID") int employeeID, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Integer currentTaskID = (Integer) session.getAttribute("currentTaskID");
        if (currentTaskID != null) {
            subtask.setTaskID(currentTaskID);
        }
        subtaskService.updateSubtask(subtask);
        subtaskService.assignEmployeeToSubtask(subtask.getSubtaskID(), employeeID);
        return "redirect:/tasks/" + subtask.getTaskID();
    }
}
