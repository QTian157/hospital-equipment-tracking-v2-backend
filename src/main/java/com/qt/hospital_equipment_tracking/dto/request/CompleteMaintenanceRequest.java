package com.qt.hospital_equipment_tracking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request to complete an in-progress maintenance record.
 * Transitions maintenance status from IN_PROGRESS to COMPLETED
 * and equipment repair status from IN_REPAIR to ACTIVE,
 * while preserving the existing usage and cleaning statuses.
 */
public class CompleteMaintenanceRequest {

    @NotBlank
    @Size(max = 150)
    private String performedBy;

    @Size(max = 500)
    private String notes;

    public CompleteMaintenanceRequest() {
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}