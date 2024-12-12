package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
import com.alpha.solutions.calcmaster2000.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final EmployeeService employeeService;

    @Autowired // Constructor injection
    public SubtaskService(SubtaskRepository subtaskRepository, EmployeeService employeeService) {
        this.subtaskRepository = subtaskRepository;
        this.employeeService = employeeService;
    }

    // Opretter en ny subtask
    public void createSubtask(Subtask subtask) {
        subtaskRepository.createSubtask(subtask);
    }

    // Henter alle subtasks for en specifik task
    public List<Subtask> getSubtasksByTaskID(int taskID) {
        return subtaskRepository.getSubtasksByTaskID(taskID);
    }

    // Opdaterer en subtask
    public void updateSubtask(Subtask subtask) {
        subtaskRepository.updateSubtask(subtask);
    }

    // Sletter en subtask
    public void deleteSubtaskById(int subtaskID) {
        subtaskRepository.deleteSubtaskById(subtaskID);
    }

    // Henter en specifik subtask baseret på ID
    public Subtask getSubtaskById(int subtaskID) {
        return subtaskRepository.getSubtaskById(subtaskID);
    }

    // tildeler en medarbejder til en subtask
    public void assignEmployeeToSubtask(int subtaskID, int employeeID) {
        subtaskRepository.assignEmployeeToSubtask(subtaskID, employeeID);
    }

    public Integer getAssignedEmployeeID(int subtaskID) {
        return subtaskRepository.getAssignedEmployeeID(subtaskID);
    }


    public String getAssignedEmployeeName(int subtaskID) {
        Integer employeeID = subtaskRepository.getAssignedEmployeeID(subtaskID);
        if (employeeID != null) {
            Employee employee = employeeService.getEmployeeByID(employeeID);
            return employee.getName();
        }
        return "None"; // Ingen medarbejder tildelt
    }
}
