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

    private final String url = "jdbc:mysql://calcmaster2000.mysql.database.azure.com:3306/calcmaster2000";
    private final String username = "Celinelundm";
    private final String password = "fredagsbar1234!";

    // opretter en subtask til en task
    public void createSubtask(Subtask subtask) {
        String sql = "INSERT INTO Subtask (TaskID, Name, Description, Priority, TimeEstimate, Status, Price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, subtask.getTaskID());
            stmt.setString(2, subtask.getName());
            stmt.setString(3, subtask.getDescription());
            stmt.setString(4, subtask.getPriority().name());
            stmt.setInt(5, subtask.getTimeEstimate());
            stmt.setString(6, subtask.getStatus().name());
            stmt.setDouble(7, subtask.getPrice());
            stmt.executeUpdate();

            // henter genereret ID og sæt det på subtask-objektet
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subtask.setSubtaskID(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved oprettelse af subtask", e);
        }
    }

    //her kan man se subtasks for en specifik task
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
                            Status.valueOf(rs.getString("Status").toUpperCase()),
                            rs.getDouble("Price")
                    );
                    subtasks.add(subtask);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af subtasks", e);
        }
        return subtasks;
    }

    // opdaterer en subtask fra databasen
    public void updateSubtask(Subtask subtask) {
        String sql = "UPDATE Subtask SET Name = ?, Description = ?, Priority = ?, TimeEstimate = ?, Status = ?, Price = ? WHERE SubtaskID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, subtask.getName());
            stmt.setString(2, subtask.getDescription());
            stmt.setString(3, subtask.getPriority().name());
            stmt.setInt(4, subtask.getTimeEstimate());
            stmt.setString(5, subtask.getStatus().name());
            stmt.setDouble(6, subtask.getPrice());
            stmt.setInt(7, subtask.getSubtaskID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved opdatering af subtask med ID " + subtask.getSubtaskID(), e);
        }
    }

    // sletter en subtask fra databasen
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

    // henter en subtask baseret på ID
    public Subtask getSubtaskById(int subtaskID) {
        String sql = "SELECT * FROM Subtask WHERE SubtaskID = ?";
        Subtask subtask = null;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subtaskID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    subtask = new Subtask(
                            rs.getInt("SubtaskID"),
                            rs.getInt("TaskID"),
                            rs.getString("Name"),
                            rs.getString("Description"),
                            Priority.valueOf(rs.getString("Priority").toUpperCase()),
                            rs.getInt("TimeEstimate"),
                            Status.valueOf(rs.getString("Status").toUpperCase()),
                            rs.getDouble("Price")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af subtask med ID " + subtaskID, e);
        }

        return subtask;
    }

    // tildeler en medarbejder til en subtask
    public void assignEmployeeToSubtask(int subtaskID, int employeeID) {
        String checkSql = "SELECT COUNT(*) FROM Subtask_Assignment WHERE SubtaskID = ? AND EmployeeID = ?";
        String insertSql = "INSERT INTO Subtask_Assignment (SubtaskID, EmployeeID) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Tjek, om kombinationen allerede findes
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, subtaskID);
                checkStmt.setInt(2, employeeID);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // Kombinationsposten findes allerede, så vi gør ingenting
                    return;
                }
            }

            // Indsæt en ny kombination, hvis den ikke findes
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, subtaskID);
                insertStmt.setInt(2, employeeID);
                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved tildeling af medarbejder til subtask", e);
        }
    }


    // henter  medarbejderen, der er tildelt en subtask
    public Integer getAssignedEmployeeID(int subtaskID) {
        String sql = "SELECT EmployeeID FROM Subtask_Assignment WHERE SubtaskID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subtaskID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("EmployeeID");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af medarbejder tildelt subtask", e);
        }
        return null;
    }
}
