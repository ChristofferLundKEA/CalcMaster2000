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

    @GetMapping("")
    public String noEndpoint(){
        return "login";
    }

    // Viser login-siden
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        // Hvis brugeren allerede er logget ind, omdiriger til /allProjects
        if (Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/allProjects";
        }
        return "login";
    }

    // Håndterer login-logik
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
            session.setAttribute("adminEmail", admin.getEmail());
            session.setAttribute("isAdminLoggedIn", true);
            return "redirect:/allProjects";
        } else {
            model.addAttribute("error", "Forkert brugernavn eller adgangskode");
            return "login";
        }
    }

    // Håndterer logout
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Viser admin-profilen
    @GetMapping("/adminProfile")
    public String showProfile(HttpSession session, Model model) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Admin admin = new Admin();
        admin.setUsername((String) session.getAttribute("adminUsername"));
        admin.setEmail((String) session.getAttribute("adminEmail"));

        model.addAttribute("admin", admin);
        return "adminProfile";
    }
}
