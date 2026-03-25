package com.company.ems.controller;

import com.company.ems.dto.EmployeeRequestDTO;
import com.company.ems.dto.EmployeeUpdateRequestDTO;
import com.company.ems.dto.PageResponseDTO;
import com.company.ems.entity.Employee;
import com.company.ems.dto.EmployeeResponseDTO;
import com.company.ems.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponseDTO<EmployeeResponseDTO> getAll(@RequestParam(required = false) String department, String firstName, String lastName, Pageable pageable) {
        return service.getAllEmployees(department, firstName, lastName, pageable);
    }

    @PostMapping
    public EmployeeResponseDTO create(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
        return service.createEmployee(requestDTO);
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEmployeeById(id);
    }

    @PutMapping("/{id}")
    public EmployeeResponseDTO update(@PathVariable Long id,
                           @Valid @RequestBody EmployeeUpdateRequestDTO employeeUpdateRequestDTO) {
        return service.updateEmployee(id, employeeUpdateRequestDTO);
    }
}
