package com.qt.hospital_equipment_tracking.dto.response;

import com.qt.hospital_equipment_tracking.model.enums.*;

public class EquipmentListResponse {

    private Long id;
    private String name;
    private String assetTag;
    private EquipmentType type;
    private Boolean mobile;

    private Long departmentId;
    private String departmentName;

    private Long currentRoomId;
    private String currentRoomName;
    private String currentRoomDepartmentName;

    private UsageStatus usageStatus;
    private CleaningStatus cleaningStatus;
    private RepairStatus repairStatus;

    private EquipmentAvailability availability;

    private Long assignedPersonId;
    private String assignedPersonName;

    public EquipmentListResponse(Long id, String name, String assetTag, EquipmentType type, Boolean mobile, Long departmentId, String departmentName, Long currentRoomId, String currentRoomName, String currentRoomDepartmentName, UsageStatus usageStatus, CleaningStatus cleaningStatus, RepairStatus repairStatus, EquipmentAvailability availability, Long assignedPersonId, String assignedPersonName) {
        this.id = id;
        this.name = name;
        this.assetTag = assetTag;
        this.type = type;
        this.mobile = mobile;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.currentRoomId = currentRoomId;
        this.currentRoomName = currentRoomName;
        this.currentRoomDepartmentName = currentRoomDepartmentName;
        this.usageStatus = usageStatus;
        this.cleaningStatus = cleaningStatus;
        this.repairStatus = repairStatus;
        this.availability = availability;
        this.assignedPersonId = assignedPersonId;
        this.assignedPersonName = assignedPersonName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public EquipmentType getType() {
        return type;
    }

    public Boolean getMobile() {
        return mobile;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Long getCurrentRoomId() {
        return currentRoomId;
    }

    public String getCurrentRoomName() {
        return currentRoomName;
    }

    public String getCurrentRoomDepartmentName() {
        return currentRoomDepartmentName;
    }

    public UsageStatus getUsageStatus() {
        return usageStatus;
    }

    public CleaningStatus getCleaningStatus() {
        return cleaningStatus;
    }

    public RepairStatus getRepairStatus() {
        return repairStatus;
    }

    public EquipmentAvailability getAvailability() {
        return availability;
    }

    public Long getAssignedPersonId() {
        return assignedPersonId;
    }

    public String getAssignedPersonName() {
        return assignedPersonName;
    }
}