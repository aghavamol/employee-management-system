package com.company.ems.repository;
import com.company.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Page<Employee> findByDepartment(String department, Pageable pageable);
    Page<Employee> findByFirstName(String firstName, Pageable pageable);
    Page<Employee> findByLastName(String lastName, Pageable pageable);
}
