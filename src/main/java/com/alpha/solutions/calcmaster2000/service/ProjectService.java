package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.model.Project;
import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    // Constructor til Dependency Injection
    public ProjectService(ProjectRepository projectRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    // opretter nyt projekt
    public void addProject(Project project) {
        if (isValidProject(project)) { // Validering før tilføjelse
            projectRepository.addProject(project);
        } else {
            throw new IllegalArgumentException("Invalid project data");
        }
    }

    // Henter alle projekter
    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.getAllProjects();
        for (Project project : projects) {
            double totalPrice = calculateTotalPrice(project.getProjectID());
            project.setTotalPrice(totalPrice); // Sæt samlet pris på hvert projekt
        }
        return projects;
    }

    // validering af projektdata
    private boolean isValidProject(Project project) {
        return project.getName() != null && !project.getName().isEmpty()
                && project.getDescription() != null && !project.getDescription().isEmpty()
                && project.getStartDate() != null
                && project.getEndDate() != null; // Ingen sammenligning af datoer
    }

    public Project getProjectById(int id) {
        Project project = projectRepository.getProjectById(id);
        if (project == null) {
            throw new RuntimeException("Project with ID: " + id + ", was not found in our database");
        }
        // Beregn totalPrice for projektet
        double totalPrice = calculateTotalPrice(id);
        project.setTotalPrice(totalPrice);

        return project;
    }

    public void deleteProject(int id) {
        projectRepository.deleteProject(id);
    }

    public void updateProject(Project project) {
        projectRepository.updateProject(project); // Kald repository-laget for opdatering
    }

    public double calculateTotalPrice(int projectID) {
        List<Task> tasks = taskService.getTasksByProjectID(projectID);
        double totalPrice = 0.0;

        for (Task task : tasks) {
            totalPrice += task.getPrice(); // Kun pris for tasken
        }
        return totalPrice;
    }

}

