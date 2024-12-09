package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubtaskRepository {

    private String url = "jdbc:mysql://calcmaster2000.mysql.database.azure.com:3306/calcmaster2000";
    private String username = "Celinelundm";
    private String password = "fredagsbar1234!";

    //(C)Opret en subtask til en task
    public void createSubtask(Subtask subtask) {
        String sql = "INSERT INTO Subtask (TaskID, Name, Description, Priority, TimeEstimate, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subtask.getTaskID());
            stmt.setString(2, subtask.getName());
            stmt.setString(3, subtask.getDescription());
            stmt.setString(4, subtask.getPriority().name());
            stmt.setInt(5, subtask.getTimeEstimate());
            stmt.setString(6, subtask.getStatus().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved oprettelse af subtask", e);
        }
    }

    //(R)Se alle subtasks for en specifik task
    public List<Subtask> getSubtasksByTaskID(int taskID) {
        List<Subtask> subtasks = new ArrayList<>();
        String sql = "SELECT * FROM Subtask WHERE TaskID = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, taskID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Subtask subtask = new Subtask(
                            rs.getInt("SubtaskID"),
                            rs.getInt("TaskID"),
                            rs.getString("Name"),
                            rs.getString("Description"),
                            Priority.valueOf(rs.getString("Priority").toUpperCase()),
                            rs.getInt("TimeEstimate"),
                            Status.valueOf(rs.getString("Status").toUpperCase())
                    );
                    subtasks.add(subtask);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af subtasks", e);
        }
        return subtasks;
    }

    //(U)Opdater en subtask fra databasen
    public void updateSubtask(Subtask subtask) {
        String sql = "UPDATE Subtask SET Name = ?, Description = ?, Priority = ?, TimeEstimate = ?, Status = ? WHERE SubtaskID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, subtask.getName());
            stmt.setString(2, subtask.getDescription());
            stmt.setString(3, subtask.getPriority().name());
            stmt.setInt(4, subtask.getTimeEstimate());
            stmt.setString(5, subtask.getStatus().name());
            stmt.setInt(6, subtask.getSubtaskID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved opdatering af subtask med ID " + subtask.getSubtaskID(), e);
        }
    }

    //(D)Slet en subtask fra databasen
    public void deleteSubtaskById(int subtaskID) {
        String sql = "DELETE FROM Subtask WHERE SubtaskID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subtaskID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved sletning af subtask med ID " + subtaskID, e);
        }
    }

    //henter en  subtask baseret på ID
    public Subtask getSubtaskById(int subtaskID) {
        String sql = "SELECT * FROM Subtask WHERE SubtaskID = ?";
        Subtask subtask = null;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subtaskID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                subtask = new Subtask(
                        rs.getInt("SubtaskID"),
                        rs.getInt("TaskID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        Priority.valueOf(rs.getString("Priority").toUpperCase()),
                        rs.getInt("TimeEstimate"),
                        Status.valueOf(rs.getString("Status"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af subtask med ID " + subtaskID, e);
        }

        return subtask;
    }
}
