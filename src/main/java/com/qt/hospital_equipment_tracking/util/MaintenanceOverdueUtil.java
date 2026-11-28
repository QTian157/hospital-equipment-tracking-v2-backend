package com.qt.hospital_equipment_tracking.util;

import com.qt.hospital_equipment_tracking.model.enums.MaintenanceStatus;

import java.time.LocalDateTime;

public final class MaintenanceOverdueUtil {
    public static boolean isOverdue(
            MaintenanceStatus maintenanceStatus,
            LocalDateTime scheduledAt,
            LocalDateTime now) {

        return maintenanceStatus == MaintenanceStatus.SCHEDULED
                && scheduledAt != null
                && scheduledAt.isBefore(now);
    }
}

