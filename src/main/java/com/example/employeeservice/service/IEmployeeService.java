package com.example.employeeservice.service;

import com.example.employeeservice.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEmployeeService {
    Optional<Employee> getEmployee(Long id);
    Page<Employee> getAllEmployees(Pageable pageable);
    Employee addEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    void deactivateEmployee(Long id);
}
