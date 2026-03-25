package com.company.ems.service;

import com.company.ems.dto.EmployeeRequestDTO;
import com.company.ems.dto.EmployeeUpdateRequestDTO;
import com.company.ems.dto.PageResponseDTO;
import com.company.ems.entity.Employee;
import com.company.ems.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.company.ems.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.company.ems.dto.EmployeeResponseDTO;
import com.company.ems.mapper.EmployeeMapper;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository repo;
    private final EmployeeMapper employeeMapper;

    private EmployeeResponseDTO mapToDTO(Employee employee) {
        return new EmployeeResponseDTO(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getDepartment());
    }

    public EmployeeService(EmployeeRepository repo, EmployeeMapper employeeMapper) {
        this.repo = repo;
        this.employeeMapper = employeeMapper;
    }

    public PageResponseDTO<EmployeeResponseDTO> getAllEmployees(String department, String firstName, String lastName, Pageable pageable) {

        Page<Employee> page;

        if (department != null && !department.isBlank()) {
            page = repo.findByDepartment(department, pageable);
        } else if (firstName != null && !firstName.isBlank()) {
            page = repo.findByFirstName(firstName, pageable);
        } else if (lastName !=null && !lastName.isBlank()) {
            page = repo.findByLastName(lastName, pageable);
        } else {
            page = repo.findAll(pageable);
        }

        Page<EmployeeResponseDTO> dtoPage = page.map(employeeMapper::toResponse);

        return new PageResponseDTO<>(
                dtoPage.getContent(),
                dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages()
        );


    }

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = employeeMapper.toEntity((requestDTO));
        Employee savedEmployee = repo.save(employee);
        return employeeMapper.toResponse(savedEmployee);
    }

    public Employee getEmployeeById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id: " + id + " not found"));
    }

    public void deleteEmployeeById(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Employee with id: " + id + " not found");
        }
        repo.deleteById(id);
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateRequestDTO employeeUpdateRequestDTO) {
        Employee existingEmployee = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id: " + id + " not found"));
        employeeMapper.updateEntity(existingEmployee, employeeUpdateRequestDTO);
        Employee updatedEmployee = repo.save(existingEmployee);
        return employeeMapper.toResponse(updatedEmployee);
    }
}
