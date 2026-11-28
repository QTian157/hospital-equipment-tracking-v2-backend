package com.qt.hospital_equipment_tracking.mapper;

import com.qt.hospital_equipment_tracking.dto.response.MaintenanceListResponse;
import com.qt.hospital_equipment_tracking.model.Equipment;
import com.qt.hospital_equipment_tracking.model.MaintenanceRecord;
import com.qt.hospital_equipment_tracking.model.Person;
import com.qt.hospital_equipment_tracking.util.MaintenanceOverdueUtil;
import org.springframework.stereotype.Component;
import com.qt.hospital_equipment_tracking.dto.response.MaintenanceDetailResponse;

import java.time.LocalDateTime;

@Component
public class MaintenanceMapper {

    public MaintenanceListResponse toListResponse(
            MaintenanceRecord maintenanceRecord) {

        Equipment equipment = maintenanceRecord.getEquipment();

        Person requestedByPerson =
                maintenanceRecord.getRequestedByPerson();

        String requestedByPersonName =
                requestedByPerson.getFirstName() + " "
                        + requestedByPerson.getLastName();

        Boolean overdue = MaintenanceOverdueUtil.isOverdue(
                maintenanceRecord.getMaintenanceStatus(),
                maintenanceRecord.getScheduledAt(),
                LocalDateTime.now()
        );

        return new MaintenanceListResponse(
                maintenanceRecord.getId(),
                equipment.getId(),
                equipment.getName(),
                equipment.getAssetTag(),
                equipment.getType(),
                equipment.getDepartment().getId(),
                equipment.getDepartment().getName(),
                maintenanceRecord.getMaintenanceType(),
                maintenanceRecord.getMaintenanceStatus(),
                maintenanceRecord.getScheduledAt(),
                maintenanceRecord.getStartedAt(),
                maintenanceRecord.getCompletedAt(),
                maintenanceRecord.getCanceledAt(),
                overdue,
                requestedByPerson.getId(),
                requestedByPersonName,
                maintenanceRecord.getPerformedBy()
        );
    }

    public MaintenanceDetailResponse toDetailResponse(
            MaintenanceRecord maintenanceRecord) {

        MaintenanceListResponse listResponse =
                toListResponse(maintenanceRecord);

        return new MaintenanceDetailResponse(
                listResponse.getId(),
                listResponse.getEquipmentId(),
                listResponse.getEquipmentName(),
                listResponse.getEquipmentAssetTag(),
                listResponse.getEquipmentType(),
                listResponse.getDepartmentId(),
                listResponse.getDepartmentName(),
                listResponse.getMaintenanceType(),
                listResponse.getMaintenanceStatus(),
                listResponse.getScheduledAt(),
                listResponse.getStartedAt(),
                listResponse.getCompletedAt(),
                listResponse.getCanceledAt(),
                listResponse.getOverdue(),
                listResponse.getRequestedByPersonId(),
                listResponse.getRequestedByPersonName(),
                listResponse.getPerformedBy(),
                maintenanceRecord.getDescription(),
                maintenanceRecord.getNotes(),
                maintenanceRecord.getCreatedAt(),
                maintenanceRecord.getUpdatedAt()
        );
    }
}