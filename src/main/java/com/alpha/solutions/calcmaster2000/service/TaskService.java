package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired // Constructor injection
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Opretter en ny task
    public void createTask(Task task) {
        taskRepository.createTask(task);
    }


    // Henter alle tasks for et specifikt projekt
    public List<Task> getTasksByProjectID(int projectID) {
        List<Task> tasks = taskRepository.getTasksByProjectID(projectID);
        return tasks != null ? tasks : new ArrayList<>();
    }

    // Opdaterer en task
    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    // Sletter en task
    public void deleteTaskById(int taskID) {
        taskRepository.deleteTaskById(taskID);
    }

    // Henter en task baseret på ID
    public Task getTaskById(int taskID) {
        return taskRepository.getTaskById(taskID);
    }

    // Tildeler en medarbejder til en task
    public void assignEmployeeToTask(int taskID, int employeeID) {
        taskRepository.assignEmployeeToTask(taskID, employeeID); // Kalder repository-metoden
    }

    public Integer getAssignedEmployeeID(int taskID) {
        return taskRepository.getAssignedEmployeeID(taskID);
    }



}
