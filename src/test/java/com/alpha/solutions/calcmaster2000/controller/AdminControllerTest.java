package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Admin;
import com.alpha.solutions.calcmaster2000.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    private AdminController adminController;
    private AdminService adminService;
    private HttpSession session;
    private Model model;

    @BeforeEach
    void setUp() {
        adminService = mock(AdminService.class);
        session = mock(HttpSession.class);
        model = mock(Model.class);
        adminController = new AdminController(adminService);
    }

    @Test
    void whenLoggedInRedirectToAllProjects() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(true);

        String viewName = adminController.showLoginPage(session);

        assertEquals("redirect:/allProjects", viewName);
    }

    @Test
    void whenNotLoggedInReturnLoginView() {
        when(session.getAttribute("isAdminLoggedIn")).thenReturn(false);

        String viewName = adminController.showLoginPage(session);

        assertEquals("login", viewName);
    }

    @Test
    void handleLoginWithValidCredentials() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");

        when(adminService.validateLogin("admin", "password")).thenReturn(admin);

        String viewName = adminController.handleLogin("admin", "password", session, model);

        assertEquals("redirect:/allProjects", viewName);
        verify(session).setAttribute("adminUsername", "admin");
        verify(session).setAttribute("adminEmail", "admin@example.com");
        verify(session).setAttribute("isAdminLoggedIn", true);
    }

    @Test
    void invalidatesSessionAndRedirectsToLogin() {
        String viewName = adminController.handleLogout(session);

        assertEquals("redirect:/login", viewName);
        verify(session).invalidate();
    }
}
