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

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final SubtaskService subtaskService;
    private final EmployeeService employeeService;

    public TaskController(TaskService taskService, ProjectService projectService, SubtaskService subtaskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.subtaskService = subtaskService;
        this.employeeService = employeeService;
    }

    // Viser formular til at oprette en ny task
    @GetMapping("/tasks/addTask")
    public String showCreateTaskForm(@RequestParam("projectID") int projectID, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Project project = projectService.getProjectById(projectID);
        List<Employee> employees = employeeService.getAllEmployees();

        // Tilføj en "None"-valgmulighed
        Employee noneOption = new Employee();
        noneOption.setEmployeeID(0); // Brug ID 0 som "None"
        noneOption.setName("None");
        employees.add(0, noneOption); // Tilføj "None" som første valgmulighed

        session.setAttribute("currentProjectID", projectID);
        model.addAttribute("task", new Task());
        model.addAttribute("project", project);
        model.addAttribute("employees", employees);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("status", Status.values());
        return "addTask";
    }


    // Håndterer oprettelsen af en task
    @PostMapping("/tasks/addTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.createTask(task); // Gem tasken med prisen i databasen
        return "redirect:/project/" + task.getProjectID(); // Tilbage til projektets side
    }

    // Håndterer oprettelsen og redigeringen af en task
    @PostMapping("/tasks/edit")
    public String updateTask(@ModelAttribute Task task, @RequestParam("employeeID") int employeeID, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Integer currentProjectID = (Integer) session.getAttribute("currentProjectID");
        if (currentProjectID != null) {
            task.setProjectID(currentProjectID);
        }
        taskService.updateTask(task);
        taskService.assignEmployeeToTask(task.getTaskID(), employeeID);
        return "redirect:/project/" + task.getProjectID();
    }

    // Sletter en task
    @GetMapping("/tasks/delete/{taskID}")
    public String deleteTask(@PathVariable int taskID, @RequestParam int projectID, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        taskService.deleteTaskById(taskID);
        session.removeAttribute("currentTaskID");
        return "redirect:/project/" + projectID;
    }

    // Viser formularen til at redigere en task
    @GetMapping("/tasks/edit/{taskID}")
    public String showEditTaskForm(@PathVariable int taskID, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Task task = taskService.getTaskById(taskID);
        List<Employee> employees = new ArrayList<>(employeeService.getAllEmployees());

        // Tilføj en "None"-valgmulighed
        Employee noneOption = new Employee();
        noneOption.setEmployeeID(0); // Brug ID 0 som "None"
        noneOption.setName("None");
        employees.add(0, noneOption); // Tilføj "None" som første valgmulighed

        Integer currentEmployeeID = taskService.getAssignedEmployeeID(taskID);

        session.setAttribute("currentTaskID", taskID);
        model.addAttribute("task", task);
        model.addAttribute("employees", employees);
        model.addAttribute("currentEmployeeID", currentEmployeeID);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("status", Status.values());
        return "editTask";
    }


    @GetMapping("/tasks/{taskID}")
    public String getTaskDetails(@PathVariable int taskID, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        try {
            // Hent tasken
            Task task = taskService.getTaskById(taskID);

            // Hent subtasks
            List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(taskID);
            if (subtasks == null) {
                subtasks = new ArrayList<>();
            }

            // Hent tildelt medarbejder til tasken
            Integer assignedEmployeeID = taskService.getAssignedEmployeeID(taskID);
            Employee assignedEmployee = null;
            if (assignedEmployeeID != null) {
                assignedEmployee = employeeService.getEmployeeByID(assignedEmployeeID);
            }

            // Tilføj data til modellen
            session.setAttribute("currentTaskID", taskID);
            model.addAttribute("task", task);
            model.addAttribute("subtasks", subtasks);
            model.addAttribute("assignedEmployee", assignedEmployee); // Tilføj taskens medarbejder

            return "taskDetails";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
    }


}

