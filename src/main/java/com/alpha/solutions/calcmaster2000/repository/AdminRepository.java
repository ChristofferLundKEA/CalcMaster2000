package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Admin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


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
