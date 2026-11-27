package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.EquipmentMovementHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentMovementHistoryRepository extends JpaRepository<EquipmentMovementHistory, Long> {

    List<EquipmentMovementHistory> findByEquipmentIdOrderByMovedAtDesc(Long equipmentId);
}