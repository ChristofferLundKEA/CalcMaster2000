package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
import com.alpha.solutions.calcmaster2000.model.Subtask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")

public class SubtaskRepositoryTest {

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Test
    void testCreatSubtask() {
        Subtask subtask = new Subtask();
        subtask.setTaskID(1);
        subtask.setName("Test Task");
        subtask.setDescription("This is a test task");
        subtask.setPriority(Priority.MEDIUM);
        subtask.setTimeEstimate(120);
        subtask.setStatus(Status.IN_PROGRESS);
        subtask.setPrice(500.0);

        subtaskRepository.createSubtask(subtask);

        assertNotNull(subtask.getSubtaskID());
    }

    @Test
    void testGetSubtasksByTaskID() {
        List<Subtask> subtasks = subtaskRepository.getSubtasksByTaskID(1);

        assertFalse(subtasks.isEmpty(), "There should be subtasks for TaskID 1");
        assertEquals(1, subtasks.get(0).getTaskID());
    }


    @Test
    void testDeleteSubtaskById() {
        subtaskRepository.deleteSubtaskById(1);

        Subtask deletedSubtask = subtaskRepository.getSubtaskById(1);
        assertNull(deletedSubtask); // Bekræft, at tasken blev slettet
    }

    @Test
    void testUpdateSubtask() {
        Subtask subtask = new Subtask();
        subtask.setSubtaskID(1); // Forudsæt, at task med ID 1 eksisterer
        subtask.setName("Updated Subtask");
        subtask.setDescription("Updated description");
        subtask.setPriority(Priority.HIGH);
        subtask.setTimeEstimate(50);
        subtask.setStatus(Status.DONE);
        subtask.setPrice(440.0);

        subtaskRepository.updateSubtask(subtask);

        Subtask updatedSubtask = subtaskRepository.getSubtaskById(1);
        assertEquals("Updated Subtask", updatedSubtask.getName());
        assertEquals(Status.DONE, updatedSubtask.getStatus());
    }


    @Test
    void testGetAssignedEmployeeID() {
        Integer employeeID = subtaskRepository.getAssignedEmployeeID(1);
        assertNotNull(employeeID, "EmployeeID should be retrieved for SubtaskID 1");
        assertEquals(1, employeeID);
    }
}
