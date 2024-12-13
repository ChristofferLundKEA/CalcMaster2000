package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.enums.Priority;
import com.alpha.solutions.calcmaster2000.enums.Status;
import com.alpha.solutions.calcmaster2000.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//integrationsteste som tester interaktionen mellem repository-metoderne og databasen
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setProjectID(1);
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setPriority(Priority.MEDIUM);
        task.setTimeEstimate(120);
        task.setStatus(Status.IN_PROGRESS);
        task.setUseSubtaskTime(false);
        task.setPrice(500.0);

        taskRepository.createTask(task);

        assertNotNull(task.getTaskID()); // Bekræft, at Task ID blev genereret
    }

    @Test
    void testGetTasksByProjectID() {
        List<Task> tasks = taskRepository.getTasksByProjectID(1);

        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.get(0).getProjectID());
    }

    @Test
    void testDeleteTaskById() {
        taskRepository.deleteTaskById(1);

        Task deletedTask = taskRepository.getTaskById(1);
        assertNull(deletedTask); // Bekræft, at tasken blev slettet
    }


    @Test
    void testAssignEmployeeToTask() {
        taskRepository.assignEmployeeToTask(1, 1);

        Integer employeeID = taskRepository.getAssignedEmployeeID(1);
        assertEquals(1, employeeID);
    }


    @Test
    void testUpdateTask() {
        Task task = new Task();
        task.setTaskID(1); // Forudsæt, at task med ID 1 eksisterer
        task.setName("Updated Task");
        task.setDescription("Updated description");
        task.setPriority(Priority.HIGH);
        task.setTimeEstimate(180);
        task.setStatus(Status.DONE);
        task.setUseSubtaskTime(true);
        task.setPrice(700.0);

        taskRepository.updateTask(task);

        Task updatedTask = taskRepository.getTaskById(1);
        assertEquals("Updated Task", updatedTask.getName());
        assertEquals(Status.DONE, updatedTask.getStatus());
    }

}
