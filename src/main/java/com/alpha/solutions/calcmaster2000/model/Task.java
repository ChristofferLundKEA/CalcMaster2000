package com.alpha.solutions.calcmaster2000.model;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private Integer taskID;
    private int projectID;
    private String name;
    private String description;
    private String priority;
    private int timeEstimate;
    private String status;

    // Constructor
    public Task() {}

    public Task(Integer taskID, int projectID, String name, String description, String priority, int timeEstimate, String status) {
        this.taskID = taskID;
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.timeEstimate = timeEstimate;
        this.status = status;
    }

    // Getters and Setters
    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
