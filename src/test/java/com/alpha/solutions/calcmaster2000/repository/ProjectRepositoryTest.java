package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/schema.sql") // Sikrer, at H2-databasen bruger det korrekte schema
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
        assertThat(projects).hasSize(1);
        assertThat(projects.get(0).getDescription()).isEqualTo("This is a test project.");
    }

    @Test
    public void testGetProjectById() {
        projectRepository.addProject(testProject);
        Project fetchedProject = projectRepository.getProjectById(1); // Antag ID 1

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
        projectRepository.addProject(testProject);

        List<Project> projectsBefore = projectRepository.getAllProjects();
        assertThat(projectsBefore).hasSize(1);

        projectRepository.deleteProject(1);

        List<Project> projectsAfter = projectRepository.getAllProjects();
        assertThat(projectsAfter).isEmpty();
    }
}
