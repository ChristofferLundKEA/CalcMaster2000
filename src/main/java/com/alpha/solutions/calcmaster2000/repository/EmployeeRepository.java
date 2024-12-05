package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final String url = "jdbc:mysql://calcmaster2000.mysql.database.azure.com:3306/calcmaster2000";
    private final String username = "Celinelundm";
    private final String password = "fredagsbar1234!";

    public Employee getEmployeeByID(int id) {
        String query = "SELECT EmployeeID, Name, PhoneNumber, Email, Skills FROM Employee WHERE EmployeeID = ?";
        Employee emp = new Employee();
        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                emp.setEmployeeID(rs.getInt("EmployeeID"));
                emp.setName(rs.getString("Name"));
                emp.setPhone(rs.getInt("PhoneNumber"));
                emp.setEmail(rs.getString("Email"));
                emp.setSkill(rs.getString("Skills"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }

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

    public void addNewEmployee(Employee employee) {
        String query = "INSERT INTO Employee (Name, Email, PhoneNumber, Skills) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setInt(3, employee.getPhone());
            stmt.setString(4, employee.getSkill());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployeeById(int id) {
        String query = "DELETE FROM Employee WHERE EmployeeID = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setInt(1, id);
        stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        String query = "UPDATE Employee SET Name = ?, Email = ?, PhoneNumber = ?, Skills = ? WHERE EmployeeID = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setInt(3, employee.getPhone());
            stmt.setString(4, employee.getSkill());
            stmt.setInt(5, employee.getEmployeeID());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}