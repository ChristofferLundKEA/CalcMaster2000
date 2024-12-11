package com.alpha.solutions.calcmaster2000.model;

import java.util.Objects;

public class Skill {
    private int skillID;
    private String name;
    private String description;

    public Skill() {
    }

    public Skill(int skillID, String name, String description) {
        this.skillID = skillID;
        this.name = name;
        this.description = description;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    // vigigt for at thymeleaf kan sammenligne skills med hinanden. Som default kigger den på "pladsen i hukommelsen" -
    // dette vil altid være false. nu kigger den på skillID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return skillID == skill.skillID; // sammenligner skillID i stedet for pladsen i hukommelsen.
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillID);
    }

}
