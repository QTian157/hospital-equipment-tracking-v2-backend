package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
