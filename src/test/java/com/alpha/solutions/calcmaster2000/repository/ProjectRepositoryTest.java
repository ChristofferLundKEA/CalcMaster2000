package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    private Project testProject;

    @BeforeEach
    public void setUp() {
        testProject = new Project();
        testProject.setName("Test Project");
        testProject.setDescription("This is a test project.");
        testProject.setStartDate(LocalDate.of(2023, 1, 1));
        testProject.setEndDate(LocalDate.of(2023, 12, 31));
    }

    @Test
    public void testAddProject() {
        projectRepository.addProject(testProject);
        List<Project> projects = projectRepository.getAllProjects();
        assertThat(projects).isNotEmpty();
        assertThat(projects.get(0).getName()).isEqualTo("Test Project");
    }

    @Test
    public void testGetAllProjects() {
        projectRepository.addProject(testProject);
        List<Project> projects = projectRepository.getAllProjects();
        for (Project project:projects) System.out.println(project.getName());
        assertThat(projects).hasSize(3);
        assertThat(projects.get(0).getDescription()).isEqualTo("This is a test project.");
    }

    @Test
    public void testGetProjectById() {
        projectRepository.addProject(testProject);
        Project fetchedProject = projectRepository.getProjectById(3);
        assertThat(fetchedProject).isNotNull();
        assertThat(fetchedProject.getName()).isEqualTo("Test Project");
    }

    @Test
    public void testUpdateProject() {
        projectRepository.addProject(testProject);

        Project updatedProject = projectRepository.getProjectById(1);
        updatedProject.setName("Updated Project");
        updatedProject.setDescription("Updated Description");

        projectRepository.updateProject(updatedProject);

        Project fetchedProject = projectRepository.getProjectById(1);
        assertThat(fetchedProject.getName()).isEqualTo("Updated Project");
        assertThat(fetchedProject.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    public void testDeleteProject() {
        List<Project> projectsBefore = projectRepository.getAllProjects();
        projectRepository.addProject(testProject);
        assertThat(projectsBefore).hasSize(2);
        projectRepository.deleteProject(1);
        List<Project> projectsAfter = projectRepository.getAllProjects();
        assertThat(projectsAfter).hasSize(2);
    }
}
