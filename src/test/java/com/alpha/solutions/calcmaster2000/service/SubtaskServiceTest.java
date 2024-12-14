package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import com.alpha.solutions.calcmaster2000.repository.SubtaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SubtaskServiceTest {

    @Autowired
    private SubtaskService subtaskService;

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Test
    void testDeleteSubtaskById_ShouldRemoveSubtask() {
        // Arrange
        Subtask subtask = new Subtask();
        subtask.setTaskID(1);
        subtask.setName("Subtask to Delete");
        subtask.setDescription("This is a subtask to delete");
        subtask.setPriority(Priority.MEDIUM);
        subtask.setTimeEstimate(120);
        subtask.setStatus(Status.IN_PROGRESS);
        subtask.setPrice(500.0);
        subtaskRepository.createSubtask(subtask);

        // Act
        subtaskService.deleteSubtaskById(subtask.getSubtaskID());

        // Assert
        Subtask deletedSubtask = subtaskService.getSubtaskById(subtask.getSubtaskID());
        assertNull(deletedSubtask);
    }

    @Test
    void testAssignEmployeeToSubtask_ShouldAddAssignment() {
        int subtaskID = 1;
        int employeeID = 1;


        subtaskService.assignEmployeeToSubtask(subtaskID, employeeID);


        Integer assignedEmployeeID = subtaskService.getAssignedEmployeeID(subtaskID);
        assertNotNull(assignedEmployeeID, "An employee should be assigned to the subtask");
        assertEquals(employeeID, assignedEmployeeID, "The correct employee should be assigned to the subtask");
    }

}