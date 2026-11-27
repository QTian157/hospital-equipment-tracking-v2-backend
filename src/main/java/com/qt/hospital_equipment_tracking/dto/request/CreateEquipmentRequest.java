package com.qt.hospital_equipment_tracking.dto.request;

import com.qt.hospital_equipment_tracking.model.enums.EquipmentCategory;
import com.qt.hospital_equipment_tracking.model.enums.EquipmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateEquipmentRequest {

    @NotBlank(message = "Equipment name is required.")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "Asset tag is required.")
    @Size(max = 100)
    private String assetTag;

    @NotBlank(message = "Serial number is required.")
    @Size(max = 100)
    private String serialNumber;

    @NotNull(message = "Equipment type is required.")
    private EquipmentType type;

    @NotNull(message = "Equipment category is required.")
    private EquipmentCategory category;

    @NotNull(message = "Mobility is required.")
    private Boolean mobile;

    @NotNull(message = "Cleaning requirement is required.")
    private Boolean requiresCleaning;

    @NotNull(message = "Department is required.")
    @Positive
    private Long departmentId;

    @Positive
    private Long currentRoomId;

    @Positive
    private Long assignedPersonId;

    public CreateEquipmentRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public EquipmentCategory getCategory() {
        return category;
    }

    public void setCategory(EquipmentCategory category) {
        this.category = category;
    }

    public Boolean getMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    public Boolean getRequiresCleaning() {
        return requiresCleaning;
    }

    public void setRequiresCleaning(Boolean requiresCleaning) {
        this.requiresCleaning = requiresCleaning;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoomId(Long currentRoomId) {
        this.currentRoomId = currentRoomId;
    }

    public Long getAssignedPersonId() {
        return assignedPersonId;
    }

    public void setAssignedPersonId(Long assignedPersonId) {
        this.assignedPersonId = assignedPersonId;
    }
}