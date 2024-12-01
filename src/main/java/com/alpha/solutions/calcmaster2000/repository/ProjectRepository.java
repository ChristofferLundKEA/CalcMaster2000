package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Project;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                project.setStartDate(rs.getDate("StartDate"));
                project.setEndDate(rs.getDate("EndDate"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

}
