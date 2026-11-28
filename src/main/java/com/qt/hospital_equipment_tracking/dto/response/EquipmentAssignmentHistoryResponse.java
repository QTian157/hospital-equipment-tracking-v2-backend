package com.qt.hospital_equipment_tracking.dto.response;

import com.qt.hospital_equipment_tracking.model.enums.AssignmentAction;

import java.time.LocalDateTime;

public class EquipmentAssignmentHistoryResponse {

    private Long id;
    private Long equipmentId;

    private Long fromPersonId;
    private String fromPersonName;

    private Long toPersonId;
    private String toPersonName;

    private AssignmentAction action;

    private Long changedByUserId;
    private String changedByName;

    private LocalDateTime changedAt;
    private String notes;

    public EquipmentAssignmentHistoryResponse(
            Long id,
            Long equipmentId,
            Long fromPersonId,
            String fromPersonName,
            Long toPersonId,
            String toPersonName,
            AssignmentAction action,
            Long changedByUserId,
            String changedByName,
            LocalDateTime changedAt,
            String notes) {

        this.id = id;
        this.equipmentId = equipmentId;
        this.fromPersonId = fromPersonId;
        this.fromPersonName = fromPersonName;
        this.toPersonId = toPersonId;
        this.toPersonName = toPersonName;
        this.action = action;
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

    public Long getFromPersonId() {
        return fromPersonId;
    }

    public String getFromPersonName() {
        return fromPersonName;
    }

    public Long getToPersonId() {
        return toPersonId;
    }

    public String getToPersonName() {
        return toPersonName;
    }

    public AssignmentAction getAction() {
        return action;
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