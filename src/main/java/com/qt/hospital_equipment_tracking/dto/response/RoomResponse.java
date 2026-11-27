package com.qt.hospital_equipment_tracking.dto.response;

public class RoomResponse {

    private Long id;
    private String name;
    private Long departmentId;
    private String departmentName;

    public RoomResponse(Long id, String name,
                        Long departmentId, String departmentName) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}