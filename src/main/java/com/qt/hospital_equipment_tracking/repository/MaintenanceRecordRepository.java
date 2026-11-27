package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.MaintenanceRecord;
import com.qt.hospital_equipment_tracking.model.enums.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {

    List<MaintenanceRecord> findByEquipmentIdOrderByCreatedAtDesc(Long equipmentId);

    boolean existsByEquipmentIdAndMaintenanceStatus(Long equipmentId, MaintenanceStatus maintenanceStatus);
}