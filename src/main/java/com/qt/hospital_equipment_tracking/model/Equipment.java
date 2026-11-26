package com.qt.hospital_equipment_tracking.model;

import com.qt.hospital_equipment_tracking.model.enums.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length =150)
    private String name;
    @Column(name = "serial_number", nullable = false, length = 100, unique = true)
    private String serialNumber;
    @Column(name = "asset_tag", nullable = false, length = 100, unique = true)
    private String assetTag;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private EquipmentType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private EquipmentCategory category;

    @Column(nullable = false)
    private Boolean mobile;
    @Column(name = "requires_cleaning", nullable = false)
    private Boolean requiresCleaning;

    @Enumerated(EnumType.STRING)
    @Column(name = "repair_status", nullable = false, length = 30)
    private RepairStatus repairStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "cleaning_status", nullable = false, length = 30)
    private CleaningStatus cleaningStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "usage_status", nullable = false, length = 30)
    private UsageStatus usageStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_room_id")
    private Room currentRoom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_person_id")
    private Person assignedPerson;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
    @Column(name = "last_maintenance_at")
    private LocalDateTime lastMaintenanceAt;
    @Version
    @Column(nullable = false)
    private Long version;

    public Equipment(){}

    public Equipment(String name, String assetTag, String serialNumber, EquipmentType type, EquipmentCategory category, Boolean mobile, Boolean requiresCleaning, RepairStatus repairStatus, CleaningStatus cleaningStatus, UsageStatus usageStatus, Room currentRoom, Department department, Person assignedPerson, LocalDateTime assignedAt, LocalDateTime lastMaintenanceAt) {
        this.name = name;
        this.assetTag = assetTag;
        this.serialNumber = serialNumber;
        this.type = type;
        this.category = category;
        this.mobile = mobile;
        this.requiresCleaning = requiresCleaning;
        this.repairStatus = repairStatus;
        this.cleaningStatus = cleaningStatus;
        this.usageStatus = usageStatus;
        this.currentRoom = currentRoom;
        this.department = department;
        this.assignedPerson = assignedPerson;
        this.assignedAt = assignedAt;
        this.lastMaintenanceAt =  lastMaintenanceAt;
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

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public EquipmentCategory getCategory() {
        return category;
    }

    public void setCategory(EquipmentCategory category) {
        this.category = category;
    }

    public Boolean getMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    public Boolean getRequiresCleaning() {
        return requiresCleaning;
    }

    public void setRequiresCleaning(Boolean requiresCleaning) {
        this.requiresCleaning = requiresCleaning;
    }

    public RepairStatus getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(RepairStatus repairStatus) {
        this.repairStatus = repairStatus;
    }

    public CleaningStatus getCleaningStatus() {
        return cleaningStatus;
    }

    public void setCleaningStatus(CleaningStatus cleaningStatus) {
        this.cleaningStatus = cleaningStatus;
    }

    public UsageStatus getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(UsageStatus usageStatus) {
        this.usageStatus = usageStatus;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Person getAssignedPerson() {
        return assignedPerson;
    }

    public void setAssignedPerson(Person assignedPerson) {
        this.assignedPerson = assignedPerson;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public LocalDateTime getLastMaintenanceAt() {
        return lastMaintenanceAt;
    }

    public void setLastMaintenanceAt(LocalDateTime lastMaintenanceAt) {
        this.lastMaintenanceAt = lastMaintenanceAt;
    }

    public Long getVersion() {
        return version;
    }

}
