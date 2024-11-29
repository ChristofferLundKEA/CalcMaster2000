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

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        Admin admin = adminService.validateLogin(username, password);
        if (admin != null) {
            session.setAttribute("adminUsername", admin.getUsername());
            session.setAttribute("isAdminLoggedIn", true);
            return "redirect:/allProjects"; // Send admin til overview
        } else {
            model.addAttribute("error", "Forkert brugernavn eller adgangskode");
            return "login"; // Gå tilbage til login-siden med fejlmeddelelse
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate(); // Slet session-data
        return "redirect:/login"; // Tilbage til login-siden
    }

    @GetMapping("/allProjects")
    public String showAllProjects() {
        // Returner navnet på Thymeleaf-templaten (fx "allProjects.html")
        return "allProjects";
    }
}
