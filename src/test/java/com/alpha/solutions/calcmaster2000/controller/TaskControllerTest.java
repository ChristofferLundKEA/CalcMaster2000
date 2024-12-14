package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Task;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
import com.alpha.solutions.calcmaster2000.service.ProjectService;
import com.alpha.solutions.calcmaster2000.service.SubtaskService;
import com.alpha.solutions.calcmaster2000.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Mock
    private ProjectService projectService;

    @Mock
    private SubtaskService subtaskService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskController = new TaskController(taskService, projectService, subtaskService, employeeService);
    }

    @Test
    void whenNotLoggedInRedirectsToLogin() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(false);

        String viewName = taskController.showCreateTaskForm(1, model, session);

        assertEquals("redirect:/login", viewName);
    }

    @Test
    void savesTaskAndRedirectsToProject() {
        Task task = new Task();
        task.setProjectID(1);

        String viewName = taskController.createTask(task);

        assertEquals("redirect:/project/1", viewName);
        verify(taskService).createTask(task);
    }

    @Test
    void deletesTaskAndRedirectsToProject() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(true);

        String viewName = taskController.deleteTask(1, 1, session);

        assertEquals("redirect:/project/1", viewName);
        verify(taskService).deleteTaskById(1);
        verify(session).removeAttribute("currentTaskID");
    }

    @Test
    void loggedInReturnsEditTaskView() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(true);

        int taskID = 1;
        Task task = new Task();
        when(taskService.getTaskById(taskID)).thenReturn(task);
        when(employeeService.getAllEmployees()).thenReturn(List.of());

        String viewName = taskController.showEditTaskForm(taskID, model, session);

        assertEquals("editTask", viewName);
        verify(model).addAttribute("task", task);
    }

    @Test
    void getTaskDetails_whenLoggedIn_returnsTaskDetailsView() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(true);

        int taskID = 1;
        Task task = new Task();
        when(taskService.getTaskById(taskID)).thenReturn(task);

        String viewName = taskController.getTaskDetails(taskID, model, session);

        assertEquals("taskDetails", viewName);
        verify(model).addAttribute("task", task);
    }

    @Test
    void getTaskDetails_whenNotLoggedIn_redirectsToLogin() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(false);

        String viewName = taskController.getTaskDetails(1, model, session);

        assertEquals("redirect:/login", viewName);
    }
}
