package com.qt.hospital_equipment_tracking.dto.request;

import com.qt.hospital_equipment_tracking.model.enums.*;
import jakarta.validation.constraints.*;

public class EquipmentSearchRequest {

    // Search by equipment name, asset tag, or serial number
    @Size(max = 150)
    private String keyword;

    @Positive
    private Long departmentId;

    private EquipmentType type;
    private EquipmentAvailability availability;
    private EquipmentCategory category;
    private Boolean mobile;

    @Positive
    private Long currentRoomId;

    @Positive
    private Long assignedPersonId;

    private UsageStatus usageStatus;
    private CleaningStatus cleaningStatus;
    private RepairStatus repairStatus;

    @NotNull
    @Min(0)
    private Integer page = 0;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer size = 20;

    @NotBlank
    private String sortBy = "name";

    @NotBlank
    @Pattern(regexp = "asc|desc",
            message = "Sort direction must be asc or desc.")
    private String sortDir = "asc";

    public EquipmentSearchRequest() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public EquipmentAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(EquipmentAvailability availability) {
        this.availability = availability;
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

    public UsageStatus getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(UsageStatus usageStatus) {
        this.usageStatus = usageStatus;
    }

    public CleaningStatus getCleaningStatus() {
        return cleaningStatus;
    }

    public void setCleaningStatus(CleaningStatus cleaningStatus) {
        this.cleaningStatus = cleaningStatus;
    }

    public RepairStatus getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(RepairStatus repairStatus) {
        this.repairStatus = repairStatus;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}
