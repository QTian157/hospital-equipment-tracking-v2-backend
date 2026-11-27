package com.qt.hospital_equipment_tracking.dto.response;

import com.qt.hospital_equipment_tracking.model.enums.*;

import java.time.LocalDateTime;

public class EquipmentDetailResponse extends EquipmentListResponse {

    private String serialNumber;
    private EquipmentCategory category;
    private Boolean requiresCleaning;

    private Long currentRoomDepartmentId;

    private LocalDateTime assignedAt;
    private LocalDateTime lastMaintenanceAt;
    private LocalDateTime nextScheduledAt;

    public EquipmentDetailResponse(Long id, String name, String assetTag, EquipmentType type, Boolean mobile, Long departmentId, String departmentName, Long currentRoomId, String currentRoomName, String currentRoomDepartmentName, UsageStatus usageStatus, CleaningStatus cleaningStatus, RepairStatus repairStatus, EquipmentAvailability availability, Long assignedPersonId, String assignedPersonName, String serialNumber, EquipmentCategory category, Boolean requiresCleaning, Long currentRoomDepartmentId, LocalDateTime assignedAt, LocalDateTime lastMaintenanceAt, LocalDateTime nextScheduledAt) {
        super(id, name, assetTag, type, mobile, departmentId, departmentName, currentRoomId, currentRoomName, currentRoomDepartmentName, usageStatus, cleaningStatus, repairStatus, availability, assignedPersonId, assignedPersonName);
        this.serialNumber = serialNumber;
        this.category = category;
        this.requiresCleaning = requiresCleaning;
        this.currentRoomDepartmentId = currentRoomDepartmentId;
        this.assignedAt = assignedAt;
        this.lastMaintenanceAt = lastMaintenanceAt;
        this.nextScheduledAt = nextScheduledAt;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public EquipmentCategory getCategory() {
        return category;
    }

    public Boolean getRequiresCleaning() {
        return requiresCleaning;
    }

    public Long getCurrentRoomDepartmentId() {
        return currentRoomDepartmentId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public LocalDateTime getLastMaintenanceAt() {
        return lastMaintenanceAt;
    }

    public LocalDateTime getNextScheduledAt() {
        return nextScheduledAt;
    }
}