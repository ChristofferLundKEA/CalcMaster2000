package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
import com.alpha.solutions.calcmaster2000.service.SkillService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private SkillService skillService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController(employeeService, skillService);
    }

    @Test
    void listAllEmployees_whenLoggedIn_returnsAllEmployeesView() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(true);

        List<Employee> employees = Arrays.asList(
                new Employee(1, "Morten Nielsen", "mn@alpha-solutions.com"),
                new Employee(2, "Sara Jensen", "sj@alpha-solutions.com")
        );
        when(employeeService.getAllEmployeeWithSkills()).thenReturn(employees);

        String viewName = employeeController.listAllEmployees(model, session);

        assertEquals("allEmployees", viewName);
        verify(model).addAttribute("employees", employees);
    }

    @Test
    void listAllEmployees_whenNotLoggedIn_redirectsToLogin() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(false);

        String viewName = employeeController.listAllEmployees(model, session);

        assertEquals("redirect:/login", viewName);
    }

    @Test
    void employeeProfile_whenLoggedIn_returnsProfileView() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(true);

        Employee employee = new Employee(1, "Morten Nielsen", "mn@alpha-solutions.com");
        when(employeeService.getEmployeeByID(1)).thenReturn(employee);

        String viewName = employeeController.employeeProfile(1, model, session);

        assertEquals("profile", viewName);
        verify(model).addAttribute("employee", employee);
        verify(session).setAttribute("lastViewedEmployee", employee);
    }

    @Test
    void employeeProfileWhenNotLoggedInRedirectsToLogin() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(false);

        String viewName = employeeController.employeeProfile(1, model, session);

        assertEquals("redirect:/login", viewName);
    }

    @Test
    void deleteEmployee() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(true);

        Employee lastViewed = new Employee(1, "Morten Nielsen", "mn@alpha-solutions.com");
        when(session.getAttribute("lastViewedEmployee")).thenReturn(lastViewed);

        String viewName = employeeController.deleteEmployeeById(1, session);

        assertEquals("redirect:/employees", viewName);
        verify(employeeService).deleteEmployeeById(1);
        verify(session).removeAttribute("lastViewedEmployee");
    }

    @Test
    void updateEmployeeWithSkills() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(true);

        Employee employee = new Employee();
        employee.setEmployeeID(1);
        List<Integer> skillIds = Arrays.asList(1, 2);

        String viewName = employeeController.updateEmployee(employee, skillIds, session);

        assertEquals("redirect:/employees", viewName);
        verify(employeeService).updateEmployee(employee);
        verify(employeeService).updateEmployeeSkills(1, skillIds);
        verify(session).setAttribute("lastUpdatedEmployee", employee);
    }
}
