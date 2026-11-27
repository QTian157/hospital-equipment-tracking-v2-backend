package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.EquipmentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentStatusHistoryRepository extends JpaRepository<EquipmentStatusHistory, Long> {

    List<EquipmentStatusHistory> findByEquipmentIdOrderByChangedAtDesc(Long equipmentId);
}