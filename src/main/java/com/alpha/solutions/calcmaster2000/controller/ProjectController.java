package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Project;
import com.alpha.solutions.calcmaster2000.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
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
    @GetMapping("/project/{id}")
    public String showProject(@PathVariable("id") int id, Model model) {
        // Hent projektet via ProjectService
        Project project = projectService.getProjectById(id);

        if (project == null) {
            // Hvis projektet ikke findes så vis en fejlside eller omdiriger til oversigten
            return "redirect:/allProjects";
        }

        // Tilføjer projektet til modellen
        model.addAttribute("project", project);
        return "project"; // Thymeleaf-side for detaljer
    }

}
