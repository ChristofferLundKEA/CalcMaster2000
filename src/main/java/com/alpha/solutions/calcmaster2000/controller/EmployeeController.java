package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.model.Skill;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
import com.alpha.solutions.calcmaster2000.service.SkillService;
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
    public String listAllEmployees(Model model){
        List<Employee> employees = employeeService.getAllEmployeeWithSkills();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/profile/{employeeID}")
    public String employeeProfile(@PathVariable int employeeID, Model model){
        Employee emp = employeeService.getEmployeeByID(employeeID);
        model.addAttribute("employee", emp);
        return "profile";
    }

    @GetMapping("/createEmployee")
    public String createEmployeeForm(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("skills", skillService.getAllSkills());
        return "addEmployee";
    }

    @PostMapping("/createEmployee")
    public String handleCreateEmployeeForm(@ModelAttribute Employee employee,
                                           @RequestParam List<Integer> skillIds) {
        int empID = employeeService.addNewEmployeeAndGetIdBack(employee);
        for (int skillIDs: skillIds) employeeService.addSkillToEmployee(empID, skillIDs);
        return "redirect:/employees";
    }

    @PostMapping("/deleteEmployee/{id}")
    public String deleteEmployeeById(@PathVariable int id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }

    @GetMapping("/updateEmployee/{id}")
    public String editEmployee(@PathVariable int id,
                               Model model){
        Employee employee = employeeService.getEmployeeByID(id);
        List<Skill> allSkills = skillService.getAllSkills();

        model.addAttribute("employee", employee);
        model.addAttribute("allSkills", allSkills);

        return "editEmployee";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute Employee employee,
                                 @RequestParam List<Integer> skillIds) {
        employeeService.updateEmployee(employee);
        employeeService.updateEmployeeSkills(employee.getEmployeeID(), skillIds);
        return "redirect:/employees";
    }
}
