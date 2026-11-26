package com.qt.hospital_equipment_tracking.model;

import com.qt.hospital_equipment_tracking.model.enums.CleaningStatus;
import com.qt.hospital_equipment_tracking.model.enums.EquipmentStatusAction;
import com.qt.hospital_equipment_tracking.model.enums.RepairStatus;
import com.qt.hospital_equipment_tracking.model.enums.UsageStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipment_status_histories")
public class EquipmentStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EquipmentStatusAction action;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_usage_status", length = 30)
    private UsageStatus fromUsageStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_usage_status", length = 30)
    private UsageStatus toUsageStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_cleaning_status", length = 30)
    private CleaningStatus fromCleaningStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_cleaning_status", length = 30)
    private CleaningStatus toCleaningStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_repair_status", length = 30)
    private RepairStatus fromRepairStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_repair_status", length = 30)
    private RepairStatus toRepairStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "changed_by_user_id", nullable = false)
    private UserAccount changedByUser;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(length = 500)
    private String notes;

    public EquipmentStatusHistory() {
    }

    public EquipmentStatusHistory(Equipment equipment, EquipmentStatusAction action, UsageStatus fromUsageStatus, UsageStatus toUsageStatus, CleaningStatus fromCleaningStatus, CleaningStatus toCleaningStatus, RepairStatus fromRepairStatus, RepairStatus toRepairStatus, UserAccount changedByUser, LocalDateTime changedAt, String notes) {
        this.equipment = equipment;
        this.action = action;
        this.fromUsageStatus = fromUsageStatus;
        this.toUsageStatus = toUsageStatus;
        this.fromCleaningStatus = fromCleaningStatus;
        this.toCleaningStatus = toCleaningStatus;
        this.fromRepairStatus = fromRepairStatus;
        this.toRepairStatus = toRepairStatus;
        this.changedByUser = changedByUser;
        this.changedAt = changedAt;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public EquipmentStatusAction getAction() {
        return action;
    }

    public void setAction(EquipmentStatusAction action) {
        this.action = action;
    }

    public UsageStatus getFromUsageStatus() {
        return fromUsageStatus;
    }

    public void setFromUsageStatus(UsageStatus fromUsageStatus) {
        this.fromUsageStatus = fromUsageStatus;
    }

    public UsageStatus getToUsageStatus() {
        return toUsageStatus;
    }

    public void setToUsageStatus(UsageStatus toUsageStatus) {
        this.toUsageStatus = toUsageStatus;
    }

    public CleaningStatus getFromCleaningStatus() {
        return fromCleaningStatus;
    }

    public void setFromCleaningStatus(CleaningStatus fromCleaningStatus) {
        this.fromCleaningStatus = fromCleaningStatus;
    }

    public CleaningStatus getToCleaningStatus() {
        return toCleaningStatus;
    }

    public void setToCleaningStatus(CleaningStatus toCleaningStatus) {
        this.toCleaningStatus = toCleaningStatus;
    }

    public RepairStatus getFromRepairStatus() {
        return fromRepairStatus;
    }

    public void setFromRepairStatus(RepairStatus fromRepairStatus) {
        this.fromRepairStatus = fromRepairStatus;
    }

    public RepairStatus getToRepairStatus() {
        return toRepairStatus;
    }

    public void setToRepairStatus(RepairStatus toRepairStatus) {
        this.toRepairStatus = toRepairStatus;
    }

    public UserAccount getChangedByUser() {
        return changedByUser;
    }

    public void setChangedByUser(UserAccount changedByUser) {
        this.changedByUser = changedByUser;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}