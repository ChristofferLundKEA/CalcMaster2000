package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.model.Skill;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
import com.alpha.solutions.calcmaster2000.service.SkillService;
import jakarta.servlet.http.HttpSession; // Import af Jakarta-session
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final SkillService skillService;

    public EmployeeController(EmployeeService employeeService, SkillService skillService) {
        this.employeeService = employeeService;
        this.skillService = skillService;
    }

    @GetMapping("/employees")
    public String listAllEmployees(Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        List<Employee> employees = employeeService.getAllEmployeeWithSkills();
        model.addAttribute("employees", employees);
        model.addAttribute("lastViewedEmployee", session.getAttribute("lastViewedEmployee")); // Session-data
        return "allEmployees";
    }

    @GetMapping("/profile/{employeeID}")
    public String employeeProfile(@PathVariable int employeeID, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Employee emp = employeeService.getEmployeeByID(employeeID);
        model.addAttribute("employee", emp);
        session.setAttribute("lastViewedEmployee", emp); // Gem sidst sete medarbejder i sessionen
        return "profile";
    }

    @GetMapping("/createEmployee")
    public String createEmployeeForm(Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        model.addAttribute("employee", new Employee());
        model.addAttribute("skills", skillService.getAllSkills());
        return "addEmployee";
    }

    @PostMapping("/createEmployee")
    public String handleCreateEmployeeForm(@ModelAttribute Employee employee,
                                           @RequestParam List<Integer> skillIds,
                                           HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        int empID = employeeService.addNewEmployeeAndGetIdBack(employee);
        for (int skillID : skillIds) {
            employeeService.addSkillToEmployee(empID, skillID);
        }

        session.setAttribute("lastAddedEmployee", employee); // Gem sidst tilføjede medarbejder
        return "redirect:/employees";
    }

    @PostMapping("/deleteEmployee/{id}")
    public String deleteEmployeeById(@PathVariable int id, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        employeeService.deleteEmployeeById(id);

        // Fjern session-attribut, hvis slettet medarbejder var sidst set
        Employee lastViewedEmployee = (Employee) session.getAttribute("lastViewedEmployee");
        if (lastViewedEmployee != null && lastViewedEmployee.getEmployeeID() == id) {
            session.removeAttribute("lastViewedEmployee");
        }

        return "redirect:/employees";
    }

    @GetMapping("/updateEmployee/{id}")
    public String editEmployee(@PathVariable int id, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        Employee employee = employeeService.getEmployeeByID(id);
        List<Skill> allSkills = skillService.getAllSkills();

        model.addAttribute("employee", employee);
        model.addAttribute("allSkills", allSkills);
        session.setAttribute("lastEditedEmployee", employee); // Gem sidst redigerede medarbejder
        return "editEmployee";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute Employee employee,
                                 @RequestParam List<Integer> skillIds,
                                 HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdminLoggedIn"))) {
            return "redirect:/login";
        }

        employeeService.updateEmployee(employee);
        employeeService.updateEmployeeSkills(employee.getEmployeeID(), skillIds);

        session.setAttribute("lastUpdatedEmployee", employee); // Gem sidst opdaterede medarbejder
        return "redirect:/employees";
    }
}

