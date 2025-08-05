package com.example.employeeservice.controller;

import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

        private final EmployeeService service;

        public EmployeeController(EmployeeService service) {
            this.service = service;
        }

        @GetMapping("/{id}")
        public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
            return ResponseEntity.ok(service.getEmployee(id));
        }

        @GetMapping
        public ResponseEntity<Page<Employee>> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size) {
            return ResponseEntity.ok(service.getAllEmployees(PageRequest.of(page, size)));
        }

        @PostMapping
        public ResponseEntity<Employee> create(@RequestBody Employee employee) {
            return ResponseEntity.ok(service.addEmployee(employee));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
            return ResponseEntity.ok(service.updateEmployee(id, employee));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deactivate(@PathVariable Long id) {
            service.deactivateEmployee(id);
            return ResponseEntity.noContent().build();
        }
}
