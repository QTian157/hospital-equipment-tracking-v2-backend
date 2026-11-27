package com.qt.hospital_equipment_tracking.dto.response;

import com.qt.hospital_equipment_tracking.model.enums.DepartmentType;

public class DepartmentResponse {

    private Long id;
    private String name;
    private DepartmentType departmentType;

    public DepartmentResponse(Long id, String name,
                              DepartmentType departmentType) {
        this.id = id;
        this.name = name;
        this.departmentType = departmentType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }
}
