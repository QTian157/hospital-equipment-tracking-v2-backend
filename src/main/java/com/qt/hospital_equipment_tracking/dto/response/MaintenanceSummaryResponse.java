package com.qt.hospital_equipment_tracking.dto.response;

/**
 * Summary of maintenance records across the hospital,
 * including upcoming, overdue, in-progress and completed maintenance.
 */
public class MaintenanceSummaryResponse {

    private Long upcoming;
    private Long overdue;
    private Long inProgress;
    private Long completed;

    public MaintenanceSummaryResponse(
            Long upcoming,
            Long overdue,
            Long inProgress,
            Long completed) {
        this.upcoming = upcoming;
        this.overdue = overdue;
        this.inProgress = inProgress;
        this.completed = completed;
    }

    public Long getUpcoming() {
        return upcoming;
    }

    public Long getOverdue() {
        return overdue;
    }

    public Long getInProgress() {
        return inProgress;
    }

    public Long getCompleted() {
        return completed;
    }
}