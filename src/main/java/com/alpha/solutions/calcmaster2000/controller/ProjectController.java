package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Project;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.service.ProjectService;
import com.alpha.solutions.calcmaster2000.service.SubtaskService;
import com.alpha.solutions.calcmaster2000.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final SubtaskService subtaskService;

    public ProjectController(ProjectService projectService, TaskService taskService, SubtaskService subtaskService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.subtaskService = subtaskService;
    }

    // GET: Vis oversigten over projekter
    @GetMapping("/allProjects")
    public String showAllProjects(Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("lastViewedProject", session.getAttribute("lastViewedProject")); // Session-attribut
        return "allProjects";
    }

    // GET: Vis formularen til at oprette et nyt projekt
    @GetMapping("/addproject")
    public String showAddProjectForm(Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        model.addAttribute("project", new Project());
        return "addProject";
    }

    // POST: Håndter oprettelse af et nyt projekt
    @PostMapping("/addproject")
    public String handleAddProjectForm(@ModelAttribute Project project, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        try {
            projectService.addProject(project);
            session.setAttribute("lastAddedProject", project); // Gem sidst tilføjede projekt i sessionen
            return "redirect:/allProjects";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "addProject";
        }
    }

    // GET: Vis detaljer om et projekt
    @GetMapping("/project/{projectID}")
    public String getProjectDetails(@PathVariable int projectID, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        try {
            Project project = projectService.getProjectById(projectID);

            // Hent tasks, der tilhører projektet
            List<Task> tasks = taskService.getTasksByProjectID(projectID);
            if (tasks == null) tasks = new ArrayList<>();

            for (Task task : tasks) { // looper over tasks som hører til projektet
                if (task.isUseSubtaskTime()) { // hvis fluebenet er sat til
                    List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(task.getTaskID()); // lav List af alle subtasks som hører til tasken
                    int sum = 0;
                    for (Subtask subtask : subtasks) sum += subtask.getTimeEstimate(); // looper over alle subtasks og lægger alle tidsestimeringer sammen i "int sum"
                    task.setCalculatedTimeEstimate(sum); // den enkelte task får "int sum" som sin tidsestimering.
                } else {
                    task.setCalculatedTimeEstimate(task.getTimeEstimate());
                }
            }

            model.addAttribute("project", project);
            model.addAttribute("tasks", tasks);

 

            session.setAttribute("lastViewedProject", project); // Gem sidste sete projekt i sessionen
            return "project";// Returner Thymeleaf-skabelonen "project.html"

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
    }

    // GET: Slet et projekt
    @GetMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable("id") int id, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        projectService.deleteProject(id);
        session.removeAttribute("lastViewedProject"); // Fjern session-attribut, hvis relevant
        return "redirect:/allProjects";
    }

    // GET: Vis formularen til at redigere et projekt
    @GetMapping("/project/edit/{id}")
    public String showEditProjectForm(@PathVariable("id") int id, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Project project = projectService.getProjectById(id);
        if (project == null) {
            return "redirect:/allProjects";
        }
        model.addAttribute("project", project);
        return "editProject";
    }

    // POST: Håndter opdatering af projekt
    @PostMapping("/project/edit/{id}")
    public String handleEditProjectForm(@PathVariable("id") int id, @ModelAttribute Project project, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        project.setProjectID(id);
        projectService.updateProject(project);
        session.setAttribute("lastEditedProject", project); // Gem opdateret projekt i sessionen
        return "redirect:/project/" + id;
    }
}
