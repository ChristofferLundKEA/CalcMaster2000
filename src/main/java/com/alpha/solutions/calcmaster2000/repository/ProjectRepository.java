package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ProjectRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT ProjectID, Name, Description, StartDate, EndDate FROM Project ORDER BY EndDate ASC";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Project project = new Project();
                project.setProjectID(rs.getInt("ProjectID"));
                project.setName(rs.getString("Name"));
                project.setDescription(rs.getString("Description"));

                // Konverter java.sql.Date til java.time.LocalDate da datoer er skrevet anderledes på sql
                project.setStartDate(rs.getDate("StartDate").toLocalDate());
                project.setEndDate(rs.getDate("EndDate").toLocalDate());

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }


    public void addProject(Project project) {
        String query = "INSERT INTO Project (Name, Description, StartDate, EndDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, Date.valueOf(project.getStartDate())); // Konverter LocalDate til java.sql.Date
            stmt.setDate(4, Date.valueOf(project.getEndDate()));   // Konverter LocalDate til java.sql.Date

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Project getProjectById(int id) {
        String query = "SELECT ProjectID, Name, Description, StartDate, EndDate FROM Project WHERE ProjectID = ?";
        Project project = null;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id); // Sæt parameter i SQL-query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                project = new Project();
                project.setProjectID(rs.getInt("ProjectID"));
                project.setName(rs.getString("Name"));
                project.setDescription(rs.getString("Description"));
                project.setStartDate(rs.getDate("StartDate").toLocalDate());
                project.setEndDate(rs.getDate("EndDate").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return project;
    }

    public void deleteProject(int id) {
        String query = "DELETE FROM Project WHERE ProjectID = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id); // Sæt parameteren i query
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateProject(Project project) {
        String query = "UPDATE Project SET Name = ?, Description = ?, StartDate = ?, EndDate = ? WHERE ProjectID = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(project.getStartDate())); // Konverter LocalDate til SQL Date
            stmt.setDate(4, java.sql.Date.valueOf(project.getEndDate()));   // Konverter LocalDate til SQL Date
            stmt.setInt(5, project.getProjectID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
