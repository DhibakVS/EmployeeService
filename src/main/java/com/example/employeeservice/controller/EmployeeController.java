package com.example.employeeservice.controller;

import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

        private final EmployeeService service;

        public EmployeeController(EmployeeService service) {
            this.service = service;
        }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> getEmployee(@PathVariable Long id) {
        if (id == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be 0");
        }

        Optional<Employee> emp = service.getEmployee(id);
        if (emp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID " + id);
        }

        return ResponseEntity.ok(emp);
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size) {
        if (page < 0 || size <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page must be >= 0 and size must be > 0");
        }

        Page<Employee> employees = service.getAllEmployees(PageRequest.of(page, size));
        if (employees.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees found");
        }

        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee data is invalid");
        }

        Employee created = service.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        if (id == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be 0");
        }
        if (employee == null || employee.getName() == null || employee.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee data is invalid");
        }

        Optional<Employee> existing = service.getEmployee(id);
        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID " + id);
        }

        Employee updated = service.updateEmployee(id, employee);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        if (id == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be 0");
        }

        Optional<Employee> existing = service.getEmployee(id);
        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID " + id);
        }

        service.deactivateEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
