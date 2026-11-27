package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.EquipmentAssignmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentAssignmentHistoryRepository extends JpaRepository<EquipmentAssignmentHistory, Long> {

    List<EquipmentAssignmentHistory> findByEquipmentIdOrderByChangedAtDesc(Long equipmentId);
}