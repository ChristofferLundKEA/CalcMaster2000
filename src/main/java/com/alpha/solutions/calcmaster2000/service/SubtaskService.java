package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {

    private SubtaskRepository subtaskRepository;

    @Autowired // Constructor injection
    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    // Opretter en ny subtask
    public void createSubtask(Subtask subtask) {
        subtaskRepository.createSubtask(subtask);
    }

    // Henter alle subtasks for en specifik task
    public List<Subtask> getSubtasksByTaskID(int taskID) {
        return subtaskRepository.getSubtasksByTaskID(taskID);
    }

    //Opdatere en subtask
    public void updateSubtask(Subtask subtask) {
        subtaskRepository.updateSubtask(subtask);
    }

    //Sletter en subtask
    public void deleteSubtaskById(int subtaskID) {
        subtaskRepository.deleteSubtaskById(subtaskID);
    }

    public Subtask getSubtaskById(int subtaskID) {
        return subtaskRepository.getSubtaskById(subtaskID);
    }
}
