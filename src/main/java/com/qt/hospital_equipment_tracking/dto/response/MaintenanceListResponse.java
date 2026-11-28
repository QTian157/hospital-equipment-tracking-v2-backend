package com.qt.hospital_equipment_tracking.dto.response;

import com.qt.hospital_equipment_tracking.model.enums.EquipmentType;
import com.qt.hospital_equipment_tracking.model.enums.MaintenanceStatus;
import com.qt.hospital_equipment_tracking.model.enums.MaintenanceType;

import java.time.LocalDateTime;

public class MaintenanceListResponse {

    private Long id;

    private Long equipmentId;
    private String equipmentName;
    private String equipmentAssetTag;
    private EquipmentType equipmentType;

    private Long departmentId;
    private String departmentName;

    private MaintenanceType maintenanceType;
    private MaintenanceStatus maintenanceStatus;

    private LocalDateTime scheduledAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime canceledAt;

    private Boolean overdue;

    private Long requestedByPersonId;
    private String requestedByPersonName;
    private String performedBy;

    public MaintenanceListResponse(Long id, Long equipmentId, String equipmentName, String equipmentAssetTag, EquipmentType equipmentType, Long departmentId, String departmentName, MaintenanceType maintenanceType, MaintenanceStatus maintenanceStatus, LocalDateTime scheduledAt, LocalDateTime startedAt, LocalDateTime completedAt, LocalDateTime canceledAt, Boolean overdue, Long requestedByPersonId, String requestedByPersonName, String performedBy) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentAssetTag = equipmentAssetTag;
        this.equipmentType = equipmentType;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.maintenanceType = maintenanceType;
        this.maintenanceStatus = maintenanceStatus;
        this.scheduledAt = scheduledAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.canceledAt = canceledAt;
        this.overdue = overdue;
        this.requestedByPersonId = requestedByPersonId;
        this.requestedByPersonName = requestedByPersonName;
        this.performedBy = performedBy;
    }

    public Long getId() {
        return id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getEquipmentAssetTag() {
        return equipmentAssetTag;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public MaintenanceStatus getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public Long getRequestedByPersonId() {
        return requestedByPersonId;
    }

    public String getRequestedByPersonName() {
        return requestedByPersonName;
    }

    public String getPerformedBy() {
        return performedBy;
    }
}