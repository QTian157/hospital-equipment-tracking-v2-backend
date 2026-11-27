package com.qt.hospital_equipment_tracking.dto.response;

import com.qt.hospital_equipment_tracking.model.enums.*;

import java.time.LocalDateTime;

public class EquipmentStatusHistoryResponse {

    private Long id;
    private Long equipmentId;

    private EquipmentStatusAction action;

    private UsageStatus fromUsageStatus;
    private UsageStatus toUsageStatus;

    private CleaningStatus fromCleaningStatus;
    private CleaningStatus toCleaningStatus;

    private RepairStatus fromRepairStatus;
    private RepairStatus toRepairStatus;

    private Long changedByUserId;
    private String changedByName;

    private LocalDateTime changedAt;
    private String notes;

    public EquipmentStatusHistoryResponse(
            Long id,
            Long equipmentId,
            EquipmentStatusAction action,
            UsageStatus fromUsageStatus,
            UsageStatus toUsageStatus,
            CleaningStatus fromCleaningStatus,
            CleaningStatus toCleaningStatus,
            RepairStatus fromRepairStatus,
            RepairStatus toRepairStatus,
            Long changedByUserId,
            String changedByName,
            LocalDateTime changedAt,
            String notes) {

        this.id = id;
        this.equipmentId = equipmentId;
        this.action = action;
        this.fromUsageStatus = fromUsageStatus;
        this.toUsageStatus = toUsageStatus;
        this.fromCleaningStatus = fromCleaningStatus;
        this.toCleaningStatus = toCleaningStatus;
        this.fromRepairStatus = fromRepairStatus;
        this.toRepairStatus = toRepairStatus;
        this.changedByUserId = changedByUserId;
        this.changedByName = changedByName;
        this.changedAt = changedAt;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public EquipmentStatusAction getAction() {
        return action;
    }

    public UsageStatus getFromUsageStatus() {
        return fromUsageStatus;
    }

    public UsageStatus getToUsageStatus() {
        return toUsageStatus;
    }

    public CleaningStatus getFromCleaningStatus() {
        return fromCleaningStatus;
    }

    public CleaningStatus getToCleaningStatus() {
        return toCleaningStatus;
    }

    public RepairStatus getFromRepairStatus() {
        return fromRepairStatus;
    }

    public RepairStatus getToRepairStatus() {
        return toRepairStatus;
    }

    public Long getChangedByUserId() {
        return changedByUserId;
    }

    public String getChangedByName() {
        return changedByName;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public String getNotes() {
        return notes;
    }
}