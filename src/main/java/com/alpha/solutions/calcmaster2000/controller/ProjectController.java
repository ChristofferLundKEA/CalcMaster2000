package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Project;
import com.alpha.solutions.calcmaster2000.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/allProjects")
    public String showAllProjects(Model model) {
        // Hent projekter fra ProjectService
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects); // Send projekterne til Thymeleaf
        return "allProjects"; // Viser allProjects.html
    }
}

