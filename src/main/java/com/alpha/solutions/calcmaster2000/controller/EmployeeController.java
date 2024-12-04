package com.alpha.solutions.calcmaster2000.controller;

import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public String listAllEmployees(Model model){
        List<Employee> employees = employeeService.getAllEmployees();
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
        return "createEmployee";
    }

    @PostMapping("/createEmployee")
    public String handleCreateEmployeeForm(@ModelAttribute Employee employee){
        employeeService.addNewEmployee(employee);
        return "redirect:/employees";
    }

    @PostMapping("/deleteEmployee/{id}")
    public String deleteEmployeeById(@PathVariable int id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }

    @GetMapping("/updateEmployee/{id}")
    public String editEmployee(@PathVariable int id, Model model){
        Employee employee = employeeService.getEmployeeByID(id);
        model.addAttribute("employee", employee);
        return "editEmployee";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute Employee employee){
        employeeService.updateEmployee(employee);
        return "redirect:/employees";
    }
}
