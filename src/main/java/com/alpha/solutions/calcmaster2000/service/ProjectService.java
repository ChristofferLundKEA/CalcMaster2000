package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.model.Project;
import com.alpha.solutions.calcmaster2000.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    // Constructor til Dependency Injection
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
        return projectRepository.getAllProjects();
    }

    // validering af projektdata
    private boolean isValidProject(Project project) {
        return project.getName() != null && !project.getName().isEmpty()
                && project.getDescription() != null && !project.getDescription().isEmpty()
                && project.getStartDate() != null
                && project.getEndDate() != null; // Ingen sammenligning af datoer
    }

    public Project getProjectById(int id) {
        return projectRepository.getProjectById(id); // Kalder repository for data
    }

}

