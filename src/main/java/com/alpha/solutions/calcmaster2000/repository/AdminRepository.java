package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Admin;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {

    private String url = "jdbc:mysql://calcmaster2000.mysql.database.azure.com:3306/calcmaster2000";
    private String username = "Celinelundm";
    private String password = "fredagsbar1234!";

    // Henter alle admins fra databasen
    public List<Admin> fetchAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM Admin";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Admin admin = new Admin(
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Email")
                );
                admins.add(admin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Fejl ved hentning af admins", e);
        }

        return admins;
    }

    // Henter en specifik admin baseret på brugernavn
    public Admin getAdminByUsername(String username) {
        String sql = "SELECT * FROM Admin WHERE Username = ?";
        Admin admin = null;

        try (Connection con = DriverManager.getConnection(url, this.username, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    admin = new Admin(
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Email")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Fejl ved hentning af admin", e);
        }

        return admin;
    }

}
