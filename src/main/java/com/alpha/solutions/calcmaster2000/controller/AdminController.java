package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Admin;
import com.alpha.solutions.calcmaster2000.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Viser login-siden
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Sender brugeren til login.html
    }


    // Håndterer login-logik
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        // Validerer admin-login via AdminService
        Admin admin = adminService.validateLogin(username, password);

        if (admin != null) {
            // Hvis login er korrekt, gem admin-info i sessionen
            session.setAttribute("adminUsername", admin.getUsername());
            session.setAttribute("isAdminLoggedIn", true);

            // Omdirigér til /allProjects
            return "redirect:/allProjects";
        } else {
            // Hvis login fejler, vis en fejlbesked på login-siden
            model.addAttribute("error", "Forkert brugernavn eller adgangskode");
            return "login"; // Bliv på login-siden
        }
    }

    // Håndterer logout
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        // Invalider sessionen for at logge brugeren ud
        session.invalidate();
        return "redirect:/login"; // Send brugeren tilbage til login-siden
    }

    // Viser profile-siden
    @GetMapping("/adminProfile")
    public String showProfile() {
        return "adminProfile"; // Sender brugeren til profile.html
    }
}


