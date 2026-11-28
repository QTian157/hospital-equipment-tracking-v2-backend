package com.qt.hospital_equipment_tracking.dto.response;

import com.qt.hospital_equipment_tracking.model.enums.EquipmentType;
import com.qt.hospital_equipment_tracking.model.enums.MaintenanceStatus;
import com.qt.hospital_equipment_tracking.model.enums.MaintenanceType;

import java.time.LocalDateTime;

public class MaintenanceDetailResponse extends MaintenanceListResponse {

    private String description;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MaintenanceDetailResponse(
            Long id,
            Long equipmentId,
            String equipmentName,
            String equipmentAssetTag,
            EquipmentType equipmentType,
            Long departmentId,
            String departmentName,
            MaintenanceType maintenanceType,
            MaintenanceStatus maintenanceStatus,
            LocalDateTime scheduledAt,
            LocalDateTime startedAt,
            LocalDateTime completedAt,
            LocalDateTime canceledAt,
            Boolean overdue,
            Long requestedByPersonId,
            String requestedByPersonName,
            String performedBy,
            String description,
            String notes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        super(
                id,
                equipmentId,
                equipmentName,
                equipmentAssetTag,
                equipmentType,
                departmentId,
                departmentName,
                maintenanceType,
                maintenanceStatus,
                scheduledAt,
                startedAt,
                completedAt,
                canceledAt,
                overdue,
                requestedByPersonId,
                requestedByPersonName,
                performedBy
        );

        this.description = description;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}