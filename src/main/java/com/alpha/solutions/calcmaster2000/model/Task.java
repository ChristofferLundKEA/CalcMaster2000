package com.alpha.solutions.calcmaster2000.model;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;

public class Task {
    private Integer taskID;
    private int projectID;
    private String name;
    private String description;
    private Priority priority;
    private int timeEstimate;
    private Status status;
    private boolean useSubtaskTime;
    private int calculatedTimeEstimate; // er ikke i DB. Bliver brugt til at udregne samlet tid fra subtasks
    private Double price;

    // Constructor
    public Task() {}

    public Task(Integer taskID, int projectID, String name, String description, Priority priority,
                int timeEstimate, Status status, boolean useSubtaskTime, int calculatedTimeEstimate) {
        this.taskID = taskID;
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.timeEstimate = timeEstimate;
        this.status = status;
        this.useSubtaskTime = useSubtaskTime;
        this.calculatedTimeEstimate = calculatedTimeEstimate;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isUseSubtaskTime() {
        return useSubtaskTime;
    }

    public void setUseSubtaskTime(boolean useSubtaskTime) {
        this.useSubtaskTime = useSubtaskTime;
    }


    public void setCalculatedTimeEstimate(int calculatedTimeEstimate) {
        this.calculatedTimeEstimate = calculatedTimeEstimate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
