package com.qt.hospital_equipment_tracking.dto.response;

import com.qt.hospital_equipment_tracking.model.enums.CleaningStatus;
import com.qt.hospital_equipment_tracking.model.enums.EquipmentType;
import com.qt.hospital_equipment_tracking.model.enums.RepairStatus;
import com.qt.hospital_equipment_tracking.model.enums.UsageStatus;

import java.util.List;
import java.util.Map;

/**
 * Aggregated dashboard response containing equipment and maintenance
 * summaries, lifecycle statistics, equipment distributions,
 * and recent activities across the hospital.
 */
public class DashboardResponse {

    private EquipmentSummaryResponse equipmentSummary;
    private MaintenanceSummaryResponse maintenanceSummary;
    private Double readinessPercentage;

    private Map<UsageStatus, Long> usageCounts;
    private Map<CleaningStatus, Long> cleaningCounts;
    private Map<RepairStatus, Long> repairCounts;

    private Map<String, Long> departmentCounts;
    private Map<EquipmentType, Long> typeCounts;

    private List<RecentActivityResponse> recentActivities;

    public DashboardResponse(
            EquipmentSummaryResponse equipmentSummary,
            MaintenanceSummaryResponse maintenanceSummary,
            Double readinessPercentage,
            Map<UsageStatus, Long> usageCounts,
            Map<CleaningStatus, Long> cleaningCounts,
            Map<RepairStatus, Long> repairCounts,
            Map<String, Long> departmentCounts,
            Map<EquipmentType, Long> typeCounts,
            List<RecentActivityResponse> recentActivities) {
        this.equipmentSummary = equipmentSummary;
        this.maintenanceSummary = maintenanceSummary;
        this.readinessPercentage = readinessPercentage;
        this.usageCounts = usageCounts;
        this.cleaningCounts = cleaningCounts;
        this.repairCounts = repairCounts;
        this.departmentCounts = departmentCounts;
        this.typeCounts = typeCounts;
        this.recentActivities = recentActivities;
    }

    public EquipmentSummaryResponse getEquipmentSummary() {
        return equipmentSummary;
    }

    public MaintenanceSummaryResponse getMaintenanceSummary() {
        return maintenanceSummary;
    }

    public Double getReadinessPercentage() {
        return readinessPercentage;
    }

    public Map<UsageStatus, Long> getUsageCounts() {
        return usageCounts;
    }

    public Map<CleaningStatus, Long> getCleaningCounts() {
        return cleaningCounts;
    }

    public Map<RepairStatus, Long> getRepairCounts() {
        return repairCounts;
    }

    public Map<String, Long> getDepartmentCounts() {
        return departmentCounts;
    }

    public Map<EquipmentType, Long> getTypeCounts() {
        return typeCounts;
    }

    public List<RecentActivityResponse> getRecentActivities() {
        return recentActivities;
    }
}