package com.qt.hospital_equipment_tracking.dto.request;

import jakarta.validation.constraints.Size;

/**
 * Request to cancel an existing scheduled maintenance record.
 * Transitions maintenance status from SCHEDULED to CANCELED
 * without changing any equipment lifecycle status.
 * The maintenance record is retained for historical tracking.
 */
public class CancelMaintenanceRequest {

    @Size(max = 500)
    private String notes;

    public CancelMaintenanceRequest() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}