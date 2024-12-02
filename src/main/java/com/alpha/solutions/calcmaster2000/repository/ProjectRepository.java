package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Project;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ProjectRepository {

    private String url = "jdbc:mysql://calcmaster2000.mysql.database.azure.com:3306/calcmaster2000";
    private String username = "Celinelundm";
    private String password = "fredagsbar1234!";

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT ProjectID, Name, Description, StartDate, EndDate FROM Project";

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



}
