package com.qt.hospital_equipment_tracking.mapper;

import com.qt.hospital_equipment_tracking.dto.response.EquipmentAssignmentHistoryResponse;
import com.qt.hospital_equipment_tracking.model.EquipmentAssignmentHistory;
import com.qt.hospital_equipment_tracking.model.Person;
import com.qt.hospital_equipment_tracking.model.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class EquipmentAssignmentHistoryMapper {
    public EquipmentAssignmentHistoryResponse toResponse(EquipmentAssignmentHistory equipmentAssignmentHistory) {
        Person fromPerson = equipmentAssignmentHistory.getFromPerson();
        Person toPerson = equipmentAssignmentHistory.getToPerson();
        Long fromPersonId = fromPerson != null ? fromPerson.getId() : null;
        Long toPersonId = toPerson != null ? toPerson.getId() : null;
        String fromPersonName = fromPerson != null ? fromPerson.getFirstName() + " " + fromPerson.getLastName() : null;
        String toPersonName = toPerson != null ? toPerson.getFirstName() + " " + toPerson.getLastName() : null;

        UserAccount changedByUser = equipmentAssignmentHistory.getChangedByUser();
        Long changedByUserId = changedByUser.getId();

        Person changedByPerson = changedByUser.getPerson();
        String changedByName = changedByPerson != null
                ? changedByPerson.getFirstName() + " " + changedByPerson.getLastName()
                : changedByUser.getUsername();

        return new EquipmentAssignmentHistoryResponse(
                equipmentAssignmentHistory.getId(),
                equipmentAssignmentHistory.getEquipment().getId(),
                fromPersonId,
                fromPersonName,
                toPersonId,
                toPersonName,
                equipmentAssignmentHistory.getAction(),
                changedByUserId,
                changedByName,
                equipmentAssignmentHistory.getChangedAt(),
                equipmentAssignmentHistory.getNotes()

        );
    }
}
