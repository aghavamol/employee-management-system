package com.company.ems.mapper;

import com.company.ems.dto.EmployeeRequestDTO;
import com.company.ems.dto.EmployeeResponseDTO;
import com.company.ems.dto.EmployeeUpdateRequestDTO;
import com.company.ems.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequestDTO.getFirstName());
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setDepartment(employeeRequestDTO.getDepartment());
        employee.setSalary(employeeRequestDTO.getSalary());
        return employee;
    }

    public void updateEntity(Employee employee, EmployeeUpdateRequestDTO employeeUpdateRequestDTO) {
        employee.setFirstName(employeeUpdateRequestDTO.getFirstName());
        employee.setLastName(employeeUpdateRequestDTO.getLastName());
        employee.setEmail(employeeUpdateRequestDTO.getEmail());
        employee.setDepartment(employeeUpdateRequestDTO.getDepartment());
        employee.setSalary(employeeUpdateRequestDTO.getSalary());
    }

    public EmployeeResponseDTO toResponse(Employee employee) {
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment()
        );
    }
}
