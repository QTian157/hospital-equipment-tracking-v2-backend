package com.qt.hospital_equipment_tracking.util;

import com.qt.hospital_equipment_tracking.model.enums.CleaningStatus;
import com.qt.hospital_equipment_tracking.model.enums.EquipmentAvailability;
import com.qt.hospital_equipment_tracking.model.enums.RepairStatus;
import com.qt.hospital_equipment_tracking.model.enums.UsageStatus;

public final class EquipmentAvailabilityUtil {

    public static EquipmentAvailability calculateAvailability(
            UsageStatus usageStatus,
            CleaningStatus cleaningStatus,
            RepairStatus repairStatus) {

        boolean ready = repairStatus == RepairStatus.ACTIVE
                && (cleaningStatus == CleaningStatus.NOT_REQUIRED
                || cleaningStatus == CleaningStatus.CLEAN);

        if (ready && usageStatus == UsageStatus.IDLE) {
            return EquipmentAvailability.AVAILABLE;
        }

        if (ready && usageStatus == UsageStatus.IN_USE) {
            return EquipmentAvailability.IN_USE;
        }

        return EquipmentAvailability.UNAVAILABLE;
    }
}