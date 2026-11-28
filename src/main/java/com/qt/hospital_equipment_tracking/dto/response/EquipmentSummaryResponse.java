package com.qt.hospital_equipment_tracking.dto.response;

/**
 * Summary of equipment availability across the hospital.
 * Shared by the equipment inventory and dashboard.
 */
public class EquipmentSummaryResponse {

    private Long total;
    private Long available;
    private Long inUse;
    private Long unavailable;

    public EquipmentSummaryResponse(
            Long total,
            Long available,
            Long inUse,
            Long unavailable) {
        this.total = total;
        this.available = available;
        this.inUse = inUse;
        this.unavailable = unavailable;
    }

    public Long getTotal() {
        return total;
    }

    public Long getAvailable() {
        return available;
    }

    public Long getInUse() {
        return inUse;
    }

    public Long getUnavailable() {
        return unavailable;
    }
}
