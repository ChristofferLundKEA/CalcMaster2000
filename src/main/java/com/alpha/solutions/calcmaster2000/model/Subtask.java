package com.alpha.solutions.calcmaster2000.model;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;

public class Subtask {
    private Integer subtaskID;
    private int taskID;
    private String name;
    private String description;
    private Priority priority;
    private int timeEstimate;
    private Status status;
    private Double price = 0.0;

    public Subtask(){}

    public Subtask(Integer subtaskID, int taskID, String name, String description, Priority priority, int timeEstimate, Status status, double Price) {
        this.subtaskID = subtaskID;
        this.taskID = taskID;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.timeEstimate = timeEstimate;
        this.status = status;
        this.price = price;
    }

    public Integer getSubtaskID() {
        return subtaskID;
    }

    public void setSubtaskID(Integer subtaskID) {
        this.subtaskID = subtaskID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}