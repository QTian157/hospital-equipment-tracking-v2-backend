package com.qt.hospital_equipment_tracking.model;


import com.qt.hospital_equipment_tracking.model.enums.DepartmentType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "department_type", nullable = false, length = 50)
    private DepartmentType departmentType;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Equipment> equipments = new ArrayList<>();

    public Department() {}

    public Department(String name, DepartmentType departmentType) {
        this.name = name;
        this.departmentType = departmentType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(DepartmentType departmentType) {
        this.departmentType = departmentType;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }
}
