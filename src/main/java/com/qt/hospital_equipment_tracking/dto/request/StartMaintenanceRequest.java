package com.qt.hospital_equipment_tracking.dto.request;

import jakarta.validation.constraints.Size;

/**
 * Request to start an existing scheduled maintenance record.
 * Transitions maintenance status from SCHEDULED to IN_PROGRESS
 * and equipment repair status from ACTIVE to IN_REPAIR.
 * Does not create a new maintenance record.
 */
public class StartMaintenanceRequest {

    @Size(max = 500)
    private String notes;

    public StartMaintenanceRequest() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}