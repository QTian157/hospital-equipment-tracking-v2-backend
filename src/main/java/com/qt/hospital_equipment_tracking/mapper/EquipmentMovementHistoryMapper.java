package com.qt.hospital_equipment_tracking.mapper;

import com.qt.hospital_equipment_tracking.dto.response.EquipmentMovementHistoryResponse;
import com.qt.hospital_equipment_tracking.model.EquipmentMovementHistory;
import com.qt.hospital_equipment_tracking.model.Person;
import com.qt.hospital_equipment_tracking.model.Room;
import com.qt.hospital_equipment_tracking.model.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMovementHistoryMapper {
    public EquipmentMovementHistoryResponse toResponse(EquipmentMovementHistory equipmentMovementHistory) {
        Room fromRoom = equipmentMovementHistory.getFromRoom();
        Long  fromRoomId = fromRoom != null ? fromRoom.getId() : null;
        String  fromRoomName = fromRoom != null ? fromRoom.getName() : null;
        Long fromDepartmentId = fromRoom != null ? fromRoom.getDepartment().getId() : null;
        String  fromDepartmentName = fromRoom != null ? fromRoom.getDepartment().getName() : null;

        UserAccount movedByUserAccount = equipmentMovementHistory.getMovedByUser();

        Long movedByUserId = movedByUserAccount.getId();
        String movedByUsername = movedByUserAccount.getUsername();

        Person movedByPerson = movedByUserAccount.getPerson();
        String movedByPersonName = movedByPerson != null ? movedByPerson.getFirstName() + " " + movedByPerson.getLastName() : movedByUsername;


        return new EquipmentMovementHistoryResponse(
                equipmentMovementHistory.getId(),
                equipmentMovementHistory.getEquipment().getId(),
                fromRoomId,
                fromRoomName,
                fromDepartmentId,
                fromDepartmentName,
                equipmentMovementHistory.getToRoom().getId(),
                equipmentMovementHistory.getToRoom().getName(),
                equipmentMovementHistory.getToRoom().getDepartment().getId(),
                equipmentMovementHistory.getToRoom().getDepartment().getName(),
                movedByUserId,
                movedByPersonName,
                equipmentMovementHistory.getMovedAt(),
                equipmentMovementHistory.getNotes()
        );
    }
}
