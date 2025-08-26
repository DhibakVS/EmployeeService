package com.example.employeeservice.service;

import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Employee> getEmployee(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    //@PreAuthorize("hasRole('HR')")
    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    //@PreAuthorize("hasRole('HR')")
    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = repository.findById(id).orElseThrow();
        existing.setRole(updated.getRole());
        existing.setStatus(updated.getStatus());
        return repository.save(existing);
    }

    @Override
    //@PreAuthorize("hasRole('HR')")
    public void deactivateEmployee(Long id) {
        Employee employee = repository.findById(id).orElseThrow();
//        employee.setStatus(Employee.Status.INACTIVE);
        repository.delete(employee);
        //repository.save(employee);
    }
}
