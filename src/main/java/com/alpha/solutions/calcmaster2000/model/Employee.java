package com.alpha.solutions.calcmaster2000.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int employeeID;
    private String name;
    private int phone;
    private String email;
    private List<Skill> skill = new ArrayList<>();

    public Employee(){}

    public Employee(int employeeID, String name, int phone, String email, List<Skill> skill) {
        this.employeeID = employeeID;
        this.skill = skill;
        this.email = email;
        this.phone = phone;
        this.name = name;
    }

    public Employee(int employeeID, String name, String email){
        this.employeeID = employeeID;
        this.name = name;
        this.email = email;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public List<Skill> getSkill() {
        return skill;
    }

    public void setSkill(List<Skill> skill) {
        this.skill = skill;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
