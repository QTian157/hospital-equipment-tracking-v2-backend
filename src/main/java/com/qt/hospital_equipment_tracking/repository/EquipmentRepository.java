package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.Equipment;
import com.qt.hospital_equipment_tracking.model.enums.CleaningStatus;
import com.qt.hospital_equipment_tracking.model.enums.RepairStatus;
import com.qt.hospital_equipment_tracking.model.enums.UsageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByAssetTag(String assetTag);

    Optional<Equipment> findBySerialNumber(String serialNumber);

    boolean existsByAssetTag(String assetTag);

    boolean existsBySerialNumber(String serialNumber);

    List<Equipment> findByDepartmentId(Long departmentId);

    List<Equipment> findByUsageStatus(UsageStatus usageStatus);

    List<Equipment> findByCleaningStatus(CleaningStatus cleaningStatus);

    List<Equipment> findByRepairStatus(RepairStatus repairStatus);

}
