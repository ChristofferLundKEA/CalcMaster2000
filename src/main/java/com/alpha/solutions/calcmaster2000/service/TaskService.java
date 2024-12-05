package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

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

        // Returner en tom liste, hvis der ikke findes tasks
        if (tasks == null) {
            return new ArrayList<>();
        }

        return tasks;
    }


    //Opdatere en task
    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    //Sletter en task
    public void deleteTaskById(int taskID) {
        taskRepository.deleteTaskById(taskID);
    }

    public Task getTaskById(int taskID) {
        return taskRepository.getTaskById(taskID);
    }

}
