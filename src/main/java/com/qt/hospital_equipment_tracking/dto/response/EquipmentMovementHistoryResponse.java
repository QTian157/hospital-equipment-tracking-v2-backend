package com.qt.hospital_equipment_tracking.dto.response;

import java.time.LocalDateTime;

public class EquipmentMovementHistoryResponse {

    private Long id;
    private Long equipmentId;

    private Long fromRoomId;
    private String fromRoomName;
    private Long fromDepartmentId;
    private String fromDepartmentName;

    private Long toRoomId;
    private String toRoomName;
    private Long toDepartmentId;
    private String toDepartmentName;

    private Long movedByUserId;
    private String movedByName;

    private LocalDateTime movedAt;
    private String notes;

    public EquipmentMovementHistoryResponse(
            Long id,
            Long equipmentId,
            Long fromRoomId,
            String fromRoomName,
            Long fromDepartmentId,
            String fromDepartmentName,
            Long toRoomId,
            String toRoomName,
            Long toDepartmentId,
            String toDepartmentName,
            Long movedByUserId,
            String movedByName,
            LocalDateTime movedAt,
            String notes) {

        this.id = id;
        this.equipmentId = equipmentId;
        this.fromRoomId = fromRoomId;
        this.fromRoomName = fromRoomName;
        this.fromDepartmentId = fromDepartmentId;
        this.fromDepartmentName = fromDepartmentName;
        this.toRoomId = toRoomId;
        this.toRoomName = toRoomName;
        this.toDepartmentId = toDepartmentId;
        this.toDepartmentName = toDepartmentName;
        this.movedByUserId = movedByUserId;
        this.movedByName = movedByName;
        this.movedAt = movedAt;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public Long getFromRoomId() {
        return fromRoomId;
    }

    public String getFromRoomName() {
        return fromRoomName;
    }

    public Long getFromDepartmentId() {
        return fromDepartmentId;
    }

    public String getFromDepartmentName() {
        return fromDepartmentName;
    }

    public Long getToRoomId() {
        return toRoomId;
    }

    public String getToRoomName() {
        return toRoomName;
    }

    public Long getToDepartmentId() {
        return toDepartmentId;
    }

    public String getToDepartmentName() {
        return toDepartmentName;
    }

    public Long getMovedByUserId() {
        return movedByUserId;
    }

    public String getMovedByName() {
        return movedByName;
    }

    public LocalDateTime getMovedAt() {
        return movedAt;
    }

    public String getNotes() {
        return notes;
    }
}