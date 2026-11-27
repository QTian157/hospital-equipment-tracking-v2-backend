package com.qt.hospital_equipment_tracking.dto.mapper;

import com.qt.hospital_equipment_tracking.dto.response.DepartmentResponse;
import com.qt.hospital_equipment_tracking.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentResponse toResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getDepartmentType()
        );
    }
}