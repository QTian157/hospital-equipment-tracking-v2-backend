package com.qt.hospital_equipment_tracking.dto.request;

import jakarta.validation.constraints.Size;

public class UnassignRequest {

    @Size(max = 500)
    private String notes;

    public UnassignRequest() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}