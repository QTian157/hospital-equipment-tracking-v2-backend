package com.qt.hospital_equipment_tracking.mapper;

import com.qt.hospital_equipment_tracking.dto.response.EquipmentStatusHistoryResponse;
import com.qt.hospital_equipment_tracking.model.EquipmentStatusHistory;
import com.qt.hospital_equipment_tracking.model.Person;
import com.qt.hospital_equipment_tracking.model.UserAccount;

import com.qt.hospital_equipment_tracking.dto.response.EquipmentStatusHistoryResponse;
import com.qt.hospital_equipment_tracking.model.EquipmentStatusHistory;
import com.qt.hospital_equipment_tracking.model.Person;
import com.qt.hospital_equipment_tracking.model.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class EquipmentStatusHistoryMapper {

    public EquipmentStatusHistoryResponse toResponse(
            EquipmentStatusHistory equipmentStatusHistory) {

        UserAccount changedByUser =
                equipmentStatusHistory.getChangedByUser();

        Person person = changedByUser.getPerson();

        String changedByName = person != null
                ? person.getFirstName() + " " + person.getLastName()
                : changedByUser.getUsername();

        return new EquipmentStatusHistoryResponse(
                equipmentStatusHistory.getId(),
                equipmentStatusHistory.getEquipment().getId(),
                equipmentStatusHistory.getAction(),
                equipmentStatusHistory.getFromUsageStatus(),
                equipmentStatusHistory.getToUsageStatus(),
                equipmentStatusHistory.getFromCleaningStatus(),
                equipmentStatusHistory.getToCleaningStatus(),
                equipmentStatusHistory.getFromRepairStatus(),
                equipmentStatusHistory.getToRepairStatus(),
                changedByUser.getId(),
                changedByName,
                equipmentStatusHistory.getChangedAt(),
                equipmentStatusHistory.getNotes()
        );
    }
}
