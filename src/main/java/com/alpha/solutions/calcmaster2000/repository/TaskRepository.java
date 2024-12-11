package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
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

    public void createTask(Task task) {
        String sql = "INSERT INTO Task (ProjectID, Name, Description, Priority, TimeEstimate, Status, UseSubtaskTime, Price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, task.getProjectID());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getPriority().name());
            stmt.setInt(5, task.getTimeEstimate());
            stmt.setString(6, task.getStatus().name());
            stmt.setBoolean(7, task.isUseSubtaskTime());
            stmt.setDouble(8, task.getPrice());
            stmt.executeUpdate();

            // Hent det genererede ID og opdater task-objektet
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    task.setTaskID(rs.getInt(1)); // Sæt det genererede ID i task-objektet
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved oprettelse af task", e);
        }
    }


    // her kan man se alle tasks for et specifikt projekt
    public List<Task> getTasksByProjectID(int projectID) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE ProjectID = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, projectID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task();
                            task.setTaskID(rs.getInt("TaskID"));
                            task.setProjectID(rs.getInt("ProjectID"));
                            task.setName(rs.getString("Name"));
                            task.setDescription(rs.getString("Description"));
                            task.setPriority(Priority.valueOf(rs.getString("Priority").toUpperCase()));
                            task.setTimeEstimate(rs.getInt("TimeEstimate"));
                            task.setStatus(Status.valueOf(rs.getString("Status")));
                            task.setUseSubtaskTime(rs.getBoolean("useSubtaskTime"));
                            task.setPrice(rs.getDouble("Price"));
                    tasks.add(task);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af tasks", e);
        }

        return tasks;
    }

    // opdaterer en task fra databasen
    public void updateTask(Task task) {
        String sql = "UPDATE Task SET Name = ?, Description = ?, Priority = ?, TimeEstimate = ?, Status = ?, UseSubtaskTime = ?, Price = ? WHERE TaskID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriority().name());
            stmt.setInt(4, task.getTimeEstimate());
            stmt.setString(5, task.getStatus().name());
            stmt.setBoolean(6, task.isUseSubtaskTime());
            stmt.setDouble(7, task.getPrice());
            stmt.setInt(8, task.getTaskID());


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
                task = new Task();
                task.setTaskID(rs.getInt("TaskID"));
                task.setProjectID(rs.getInt("ProjectID"));
                task.setName(rs.getString("Name"));
                task.setDescription(rs.getString("Description"));
                task.setPriority(Priority.valueOf(rs.getString("Priority").toUpperCase()));
                task.setTimeEstimate(rs.getInt("TimeEstimate"));
                task.setStatus(Status.valueOf(rs.getString("Status")));
                task.setUseSubtaskTime(rs.getBoolean("useSubtaskTime"));
                task.setPrice(rs.getDouble("Price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af task med ID " + taskID, e);
        }

        return task;
    }

    public void assignEmployeeToTask(int taskID, int employeeID) {
        String checkSql = "SELECT COUNT(*) FROM Task_Assignment WHERE TaskID = ? AND EmployeeID = ?";
        String insertSql = "INSERT INTO Task_Assignment (TaskID, EmployeeID) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Tjek, om kombinationen allerede findes
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, taskID);
                checkStmt.setInt(2, employeeID);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // Kombinationsposten findes allerede, så vi gør ingenting
                    return;
                }
            }

            // Indsæt en ny kombination, hvis den ikke findes
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, taskID);
                insertStmt.setInt(2, employeeID);
                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved tildeling af medarbejder til task", e);
        }
    }


    public Integer getAssignedEmployeeID(int taskID) {
        String sql = "SELECT EmployeeID FROM Task_Assignment WHERE TaskID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("EmployeeID");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af tildelt medarbejder for task " + taskID, e);
        }
        return null; // Ingen medarbejder tildelt
    }
}
