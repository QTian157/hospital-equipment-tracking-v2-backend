package com.qt.hospital_equipment_tracking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class MovementRequest {

    @NotNull
    @Positive
    private Long toRoomId;

    @Size(max = 500)
    private String notes;

    public MovementRequest() {
    }

    public Long getToRoomId() {
        return toRoomId;
    }

    public void setToRoomId(Long toRoomId) {
        this.toRoomId = toRoomId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}