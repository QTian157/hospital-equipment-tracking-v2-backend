package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // Find all active staff members for the assignment dropdown.
    List<Person> findByActiveTrue();

    // Find all staff members in a specific department.
    List<Person> findByDepartmentId(Long departmentId);

    // Find a staff member by employee ID.
    Optional<Person> findByEmployeeId(String employeeId);

    // Check if the employee ID already exists to prevent duplicates.
    boolean existsByEmployeeId(String employeeId);
}