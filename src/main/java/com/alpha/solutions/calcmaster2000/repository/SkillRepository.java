package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Skill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SkillRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    public List<Skill> getAllSkills() {
        List<Skill> listOfSkills = new ArrayList<>();

        String query = "SELECT SkillID, Name, Description FROM Skill";

        try(Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = con.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Skill skill = new Skill();
                skill.setSkillID(rs.getInt("SkillID"));
                skill.setName(rs.getString("Name"));
                skill.setDescription(rs.getString("Description"));
                listOfSkills.add(skill);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listOfSkills;
    }

    public void addSkill(Skill skill) {
        String query = "INSERT INTO skill (Name, Description) VALUES (?,?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
        PreparedStatement stmt = con.prepareStatement(query)){

            stmt.setString(1, skill.getName());
            stmt.setString(2, skill.getDescription());
            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteSkillById(int skillId) {
        String query = "DELETE FROM skill WHERE SkillID = ?";

        try(Connection con = DriverManager.getConnection(url, username, password);
        PreparedStatement stmt = con.prepareStatement(query)){

        stmt.setInt(1, skillId);
        stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
