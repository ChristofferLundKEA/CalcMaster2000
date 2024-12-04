package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeByID(int id) {
        return employeeRepository.getEmployeeByID(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public void addNewEmployee(Employee employee) {
        employeeRepository.addNewEmployee(employee);
    }

    public void deleteEmployeeById(int id) {
        employeeRepository.deleteEmployeeById(id);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.updateEmployee(employee);
    }
}
