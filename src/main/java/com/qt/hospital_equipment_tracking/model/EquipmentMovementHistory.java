package com.qt.hospital_equipment_tracking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipment_movement_histories")
public class EquipmentMovementHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "from_room_id")
    private Room fromRoom;
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_room_id", nullable = false)
    private Room toRoom;
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "moved_by_user_id", nullable = false)
    private UserAccount movedByUser;

    @Column(name = "moved_at", nullable = false)
    private LocalDateTime movedAt;
    @Column(length = 500)
    private String notes;

    public EquipmentMovementHistory() {}

    public EquipmentMovementHistory(Equipment equipment, Room fromRoom, Room toRoom, UserAccount movedByUser, LocalDateTime movedAt, String notes) {
        this.equipment = equipment;
        this.fromRoom = fromRoom;
        this.toRoom = toRoom;
        this.movedByUser = movedByUser;
        this.movedAt = movedAt;
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

    public Room getFromRoom() {
        return fromRoom;
    }

    public void setFromRoom(Room fromRoom) {
        this.fromRoom = fromRoom;
    }

    public Room getToRoom() {
        return toRoom;
    }

    public void setToRoom(Room toRoom) {
        this.toRoom = toRoom;
    }

    public UserAccount getMovedByUser() {
        return movedByUser;
    }

    public void setMovedByUser(UserAccount movedByUser) {
        this.movedByUser = movedByUser;
    }

    public LocalDateTime getMovedAt() {
        return movedAt;
    }

    public void setMovedAt(LocalDateTime movedAt) {
        this.movedAt = movedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
