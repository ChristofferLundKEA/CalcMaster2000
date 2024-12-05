package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    private String url = "jdbc:mysql://calcmaster2000.mysql.database.azure.com:3306/calcmaster2000";
    private String username = "Celinelundm";
    private String password = "fredagsbar1234!";

    //(C)Opret en task til et projekt
    public void createTask(Task task) {
        String sql = "INSERT INTO Task (ProjectID, Name, Description, Priority, TimeEstimate, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, task.getProjectID());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getPriority());
            stmt.setInt(5, task.getTimeEstimate());
            stmt.setString(6, task.getStatus());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved oprettelse af task", e);
        }
    }

    //(R)Se alle tasks for et specifikt projekt
    public List<Task> getTasksByProjectID(int projectID) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE ProjectID = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, projectID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(
                            rs.getInt("TaskID"),
                            rs.getInt("ProjectID"),
                            rs.getString("Name"),
                            rs.getString("Description"),
                            rs.getString("Priority"),
                            rs.getInt("TimeEstimate"),
                            rs.getString("Status")
                    );
                    tasks.add(task);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af tasks", e);
        }

        return tasks;
    }

    //(U)Opdater en task fra databasen
    public void updateTask(Task task) {
        String sql = "UPDATE Task SET Name = ?, Description = ?, Priority = ?, TimeEstimate = ?, Status = ? WHERE TaskID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriority());
            stmt.setInt(4, task.getTimeEstimate());
            stmt.setString(5, task.getStatus());
            stmt.setInt(6, task.getTaskID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved opdatering af task med ID " + task.getTaskID(), e);
        }
    }


    //(D)Slet en task fra databasen
    public void deleteTaskById(int taskID) {
        String sql = "DELETE FROM Task WHERE TaskID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved sletning af task med ID " + taskID, e);
        }
    }

    //henter en  task baseret på ID
    public Task getTaskById(int taskID) {
        String sql = "SELECT * FROM Task WHERE TaskID = ?";
        Task task = null;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, taskID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                task = new Task(
                        rs.getInt("TaskID"),
                        rs.getInt("ProjectID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Priority"),
                        rs.getInt("TimeEstimate"),
                        rs.getString("Status")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af task med ID " + taskID, e);
        }

        return task;
    }

}
