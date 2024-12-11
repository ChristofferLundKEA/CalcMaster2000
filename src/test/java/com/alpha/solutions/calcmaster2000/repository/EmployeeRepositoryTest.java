package com.alpha.solutions.calcmaster2000.repository;

import com.alpha.solutions.calcmaster2000.model.Employee;
import com.alpha.solutions.calcmaster2000.model.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void getEmployeeByIDTest(){
        Employee employee = employeeRepository.getEmployeeByID(3);
        assertNotNull(employee);
        assertEquals("Maria Larsen", employee.getName());
        assertEquals(56781234, employee.getPhone());
        assertEquals("ML@alpha-solutions.com", employee.getEmail());
        assertFalse(employee.getSkill().isEmpty());
    }

    @Test
    void getAllEmployeesTest(){
        List<Employee> listOfAllTheEmployees = employeeRepository.getAllEmployees();
        assertFalse(listOfAllTheEmployees.isEmpty());
        assertEquals("Anna Hansen", listOfAllTheEmployees.getFirst().getName());
        assertEquals(56781234, listOfAllTheEmployees.get(2).getPhone());
        assertEquals(3, listOfAllTheEmployees.size());
    }

    @Test
    void getSkillForEmployeeTest(){
        List<Skill> listOfSkills = employeeRepository.getSkillForEmployee(3);
        assertEquals("Data Science", listOfSkills.getFirst().getName());
        assertEquals(3, listOfSkills.size());

        employeeRepository.addSkillToEmployee(3, 20);
        assertEquals("Technical Writing", employeeRepository.getSkillForEmployee(3).getLast().getName());
    }

    @Test
    void deleteEmployeeById(){
        List<Employee> list1OfEmployees = employeeRepository.getAllEmployees();
        int listSize1 = list1OfEmployees.size();

        employeeRepository.deleteEmployeeById(1);

        List<Employee> list2OfEmployees = employeeRepository.getAllEmployees();
        int listSize2 = list2OfEmployees.size();
        assertTrue(listSize1 > listSize2);
    }

    @Test
    void updateEmployee(){
        assertEquals("Peter Jensen", employeeRepository.getEmployeeByID(2).getName());
        Employee employee1 = employeeRepository.getEmployeeByID(2);
        employee1.setName("Anna Nielsen");
        employeeRepository.updateEmployee(employee1);
        assertEquals("Anna Nielsen", employeeRepository.getEmployeeByID(2).getName());
    }

}
