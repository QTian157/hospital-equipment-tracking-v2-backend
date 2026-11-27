package com.qt.hospital_equipment_tracking.dto.response;


public class PersonResponse {
    private Long id;

    private String firstName;
    private String lastName;
    private String employeeId;
    private Boolean active;
    private Long departmentId;
    private String departmentName;

    public PersonResponse(Long id, String firstName, String lastName, String employeeId, Boolean active, Long departmentId, String departmentName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.active = active;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Boolean getActive() {
        return active;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
