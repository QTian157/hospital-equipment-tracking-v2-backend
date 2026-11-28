package com.qt.hospital_equipment_tracking.dto.request;

import com.qt.hospital_equipment_tracking.model.enums.MaintenanceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateMaintenanceRequest {

    @NotNull
    private MaintenanceType maintenanceType;

    @NotNull
    @Positive
    private Long requestedByPersonId;

    @NotNull
    private LocalDateTime scheduledAt;

    @Size(max = 2000)
    private String description;

    @Size(max = 500)
    private String notes;

    public UpdateMaintenanceRequest() {
    }

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public Long getRequestedByPersonId() {
        return requestedByPersonId;
    }

    public void setRequestedByPersonId(Long requestedByPersonId) {
        this.requestedByPersonId = requestedByPersonId;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}