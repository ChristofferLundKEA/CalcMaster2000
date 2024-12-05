package com.alpha.solutions.calcmaster2000.model;

public class Subtask {
    private Integer subtaskID;
    private int taskID;
    private String name;
    private String description;
    private String priority;
    private int timeEstimate;
    private String status;

    public Subtask(Integer subtaskID, int taskID, String name, String description, String priority, int timeEstimate, String status) {
        this.subtaskID = subtaskID;
        this.taskID = taskID;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.timeEstimate = timeEstimate;
        this.status = status;
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