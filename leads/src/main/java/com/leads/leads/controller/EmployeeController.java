package com.leads.leads.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leads.leads.entity.Employee;
import com.leads.leads.service.EmployeeService;

import jakarta.xml.bind.JAXBException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // List all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.listEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveOrUpdateEmployee(employee);
    }

    // Update existing employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee employeeDetails) {
        return employeeService.findEmployeeById(id).map(existing -> {
            existing.setFirstName(employeeDetails.getFirstName());
            existing.setLastName(employeeDetails.getLastName());
            existing.setDivision(employeeDetails.getDivision());
            existing.setBuilding(employeeDetails.getBuilding());
            existing.setTitle(employeeDetails.getTitle());
            existing.setRoom(employeeDetails.getRoom());
            Employee updated = employeeService.saveOrUpdateEmployee(existing);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeService.findEmployeeById(id).isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/import")
    public ResponseEntity<List<Employee>> importEmployees(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            List<Employee> importedEmployees = employeeService.importEmployeesFromXML(file);
            return ResponseEntity.ok(importedEmployees);
        } catch (JAXBException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
