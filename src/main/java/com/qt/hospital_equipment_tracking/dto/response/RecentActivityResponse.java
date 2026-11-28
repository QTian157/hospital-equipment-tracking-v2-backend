package com.qt.hospital_equipment_tracking.dto.response;

import java.time.LocalDateTime;

/**
 * Unified activity summary for the dashboard.
 * Combines lifecycle, movement and assignment history records
 * into a consistent display format.
 */
public class RecentActivityResponse {

    private Long sourceId;
    private String sourceType;
    private Long equipmentId;
    private String equipmentName;
    private String equipmentAssetTag;
    private String action;
    private String description;
    private String actorName;
    private LocalDateTime occurredAt;

    public RecentActivityResponse(
            Long sourceId,
            String sourceType,
            Long equipmentId,
            String equipmentName,
            String equipmentAssetTag,
            String action,
            String description,
            String actorName,
            LocalDateTime occurredAt) {
        this.sourceId = sourceId;
        this.sourceType = sourceType;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentAssetTag = equipmentAssetTag;
        this.action = action;
        this.description = description;
        this.actorName = actorName;
        this.occurredAt = occurredAt;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getEquipmentAssetTag() {
        return equipmentAssetTag;
    }

    public String getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }

    public String getActorName() {
        return actorName;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}