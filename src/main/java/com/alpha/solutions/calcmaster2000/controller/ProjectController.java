package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Project;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.service.ProjectService;
import com.alpha.solutions.calcmaster2000.service.SubtaskService;
import com.alpha.solutions.calcmaster2000.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String showAllProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "allProjects";
    }

    // GET: Vis formularen til at oprette et nyt projekt
    @GetMapping("/addproject")
    public String showAddProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "addProject";
    }

    // POST: Håndter oprettelse af et nyt projekt
    @PostMapping("/addproject")
    public String handleAddProjectForm(@ModelAttribute Project project, Model model) {
        try {
            // Valider og gem projektet ved hjælp af service
            projectService.addProject(project);
            return "redirect:/allProjects"; // Går tilbage til oversigten efter oprettelse
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // Tilføjer fejlmeddelelse
            return "addProject"; // Gå tilbage til formularen med fejl
        }
    }

    //viser både detaljer om projekt og tasks
    @GetMapping("/project/{projectID}")
    public String getProjectDetails(@PathVariable int projectID, Model model) {
        try {
            // Hent projektet baseret på projekt-ID
            Project project = projectService.getProjectById(projectID);

            // Hent tasks, der tilhører projektet
            List<Task> tasks = taskService.getTasksByProjectID(projectID);
            if (tasks == null) {
                tasks = new ArrayList<>(); // Initialiser en tom liste, hvis der ikke findes tasks
            }

            for (Task task : tasks) { // looper over tasks som hører til projektet
                if (task.isUseSubtaskTime()) { // hvis fluebenet er sat til
                    List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(task.getTaskID()); // lav List af alle subtasks som hører til tasken
                    int sum = 0;
                    for (Subtask subtask : subtasks) sum += subtask.getTimeEstimate(); // looper over alle subtasks og lægger alle tidsestimeringer sammen i "int sum"
                    task.setCalculatedTimeEstimate(sum); // den enkelte task får "int sum" som sin tidsestimering.
                } else {
                    task.setCalculatedTimeEstimate(task.getTimeEstimate()); // hvis ikke fluebenet er sat til, vil den oprindelige tidsestimering blive brugt.
                }
            }

            // Tilføj data til modellen for Thymeleaf
            model.addAttribute("project", project);
            model.addAttribute("tasks", tasks);

            return "project"; // Returner Thymeleaf-skabelonen "project.html"
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Returner en fejlside, hvis der opstår en fejl
        }
    }


    @GetMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable("id") int id) {
        // Kald service-laget for at slette projektet
        projectService.deleteProject(id);
        return "redirect:/allProjects"; // Tilbage til oversigten efter sletning
    }

    @GetMapping("/project/edit/{id}")
    public String showEditProjectForm(@PathVariable("id") int id, Model model) {
        Project project = projectService.getProjectById(id); // Hent projektet via service

        if (project == null) {
            return "redirect:/allProjects"; // Hvis projektet ikke findes, omdiriger til allProjects
        }

        model.addAttribute("project", project); // Send projektet til formularen
        return "editProject"; // vises til redigeringsformular
    }

    @PostMapping("/project/edit/{id}")
    public String handleEditProjectForm(@PathVariable("id") int id, @ModelAttribute Project project) {
        project.setProjectID(id); // Sikrer, at det korrekte ID opdateres
        projectService.updateProject(project); // Opdater projektet via service
        return "redirect:/project/" + id; // Send brugeren tilbage til projektets side
    }


}

