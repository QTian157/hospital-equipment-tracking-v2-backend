package com.qt.hospital_equipment_tracking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AssignmentRequest {

    @NotNull
    @Positive
    private Long personId;

    @Size(max = 500)
    private String notes;

    public AssignmentRequest() {
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
