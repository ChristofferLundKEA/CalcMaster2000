package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private String url = "jdbc:mysql://calcmaster2000.mysql.database.azure.com:3306/calcmaster2000";
    private String username = "Celinelundm";
    private String password = "fredagsbar1234!";

    public List<Employee> getAllEmployees() {
        List<Employee> listOfEmployees = new ArrayList<>();
        String query = "SELECT EmployeeID, Name, PhoneNumber, Email, Skills FROM Employee";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeID(rs.getInt("EmployeeID"));
                employee.setName(rs.getString("Name"));
                employee.setPhone(rs.getInt("PhoneNumber"));
                employee.setEmail(rs.getString("Email"));
                employee.setSkill(rs.getString("Skills"));
                listOfEmployees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfEmployees;
    }
}