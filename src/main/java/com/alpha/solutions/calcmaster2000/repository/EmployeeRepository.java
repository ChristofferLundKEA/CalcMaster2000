package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.model.Skill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public Employee getEmployeeByID(int id) {
        String query = "SELECT EmployeeID, Name, PhoneNumber, Email FROM Employee WHERE EmployeeID = ?";
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
                emp.setSkill(getSkillForEmployee(id));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> listOfEmployees = new ArrayList<>();
        String query = "SELECT EmployeeID, Name, PhoneNumber, Email FROM Employee";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeID(rs.getInt("EmployeeID"));
                employee.setName(rs.getString("Name"));
                employee.setPhone(rs.getInt("PhoneNumber"));
                employee.setEmail(rs.getString("Email"));
                employee.setSkill(new ArrayList<>());
                listOfEmployees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfEmployees;
    }

    public List<Skill> getSkillForEmployee(int employeeID){
        List<Skill> skills = new ArrayList<>();
        String query = "SELECT s.SkillID, s.Name, s.Description " +
                "FROM Skill s " +
                "JOIN Employee_Skill es ON s.SkillID = es.SkillID " +
                "WHERE es.EmployeeID = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, employeeID);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Skill skill = new Skill();
                    skill.setSkillID(rs.getInt("SkillID"));
                    skill.setName(rs.getString("Name"));
                    skill.setDescription(rs.getString("Description"));
                    skills.add(skill);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return skills;
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
        String query = "UPDATE Employee SET Name = ?, Email = ?, PhoneNumber = ? WHERE EmployeeID = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setInt(3, employee.getPhone());
            stmt.setInt(4, employee.getEmployeeID());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addSkillToEmployee(int employeeID, int skillID) {
        String query = "INSERT INTO Employee_Skill (EmployeeID, SkillID) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, employeeID);
            stmt.setInt(2, skillID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addNewEmployeeAndGetIdBack(Employee employee) {
        String query = "INSERT INTO Employee (Name, Email, PhoneNumber) VALUES (?, ?, ?)";
        String getLastInsertedIdQuery = "SELECT LAST_INSERT_ID() AS EmployeeID";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setInt(3, employee.getPhone());
            stmt.executeUpdate();

            try (PreparedStatement idStmt = con.prepareStatement(getLastInsertedIdQuery);
                 ResultSet rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("EmployeeID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteEmployeeSkills(int employeeID) {
        String query = "DELETE FROM Employee_Skill WHERE EmployeeID = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, employeeID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}