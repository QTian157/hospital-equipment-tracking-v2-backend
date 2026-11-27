package com.qt.hospital_equipment_tracking.dto.request;

import jakarta.validation.constraints.Size;

public class LifecycleActionRequest {

    @Size(max = 500)
    private String notes;

    public LifecycleActionRequest() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}