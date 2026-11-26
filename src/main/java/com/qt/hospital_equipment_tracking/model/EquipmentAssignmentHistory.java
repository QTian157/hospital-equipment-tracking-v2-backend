package com.qt.hospital_equipment_tracking.model;

import com.qt.hospital_equipment_tracking.model.enums.AssignmentAction;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipment_assignment_histories")
public class EquipmentAssignmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "from_person_id")
    private Person fromPerson;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "to_person_id")
    private Person toPerson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AssignmentAction action;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "changed_by_user_id", nullable = false)
    private UserAccount changedByUser;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;
    @Column(length = 500)
    private String notes;

    public EquipmentAssignmentHistory() {}

    public EquipmentAssignmentHistory(Equipment equipment, Person fromPerson, Person toPerson, AssignmentAction action, UserAccount changedByUser, LocalDateTime changedAt, String notes) {
        this.equipment = equipment;
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.action = action;
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

    public Person getFromPerson() {
        return fromPerson;
    }

    public void setFromPerson(Person fromPerson) {
        this.fromPerson = fromPerson;
    }

    public Person getToPerson() {
        return toPerson;
    }

    public void setToPerson(Person toPerson) {
        this.toPerson = toPerson;
    }

    public AssignmentAction getAction() {
        return action;
    }

    public void setAction(AssignmentAction action) {
        this.action = action;
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
