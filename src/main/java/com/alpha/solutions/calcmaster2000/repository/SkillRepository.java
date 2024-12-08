package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Skill;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SkillRepository {

    private final String url = "jdbc:mysql://calcmaster2000.mysql.database.azure.com:3306/calcmaster2000";
    private final String username = "Celinelundm";
    private final String password = "fredagsbar1234!";

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
}
