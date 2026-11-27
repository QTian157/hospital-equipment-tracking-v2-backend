package com.qt.hospital_equipment_tracking.mapper;

import com.qt.hospital_equipment_tracking.dto.response.EquipmentListResponse;
import com.qt.hospital_equipment_tracking.model.Equipment;
import com.qt.hospital_equipment_tracking.model.Person;
import com.qt.hospital_equipment_tracking.model.Room;
import com.qt.hospital_equipment_tracking.model.enums.CleaningStatus;
import com.qt.hospital_equipment_tracking.model.enums.EquipmentAvailability;
import com.qt.hospital_equipment_tracking.model.enums.RepairStatus;
import com.qt.hospital_equipment_tracking.model.enums.UsageStatus;
import com.qt.hospital_equipment_tracking.util.EquipmentAvailabilityUtil;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    public EquipmentListResponse toListResponse(Equipment equipment){
        Room currentRoom = equipment.getCurrentRoom();

        Person assignedPerson = equipment.getAssignedPerson();
        String assignedPersonName = assignedPerson != null ? assignedPerson.getFirstName() + " " + assignedPerson.getLastName() : null;

        UsageStatus usageStatus = equipment.getUsageStatus();
        CleaningStatus cleaningStatus = equipment.getCleaningStatus();
        RepairStatus repairStatus = equipment.getRepairStatus();

        EquipmentAvailability availability = EquipmentAvailabilityUtil.calculateAvailability(usageStatus,cleaningStatus,repairStatus);

        return new EquipmentListResponse(
                equipment.getId(),
                equipment.getName(),
                equipment.getAssetTag(),
                equipment.getType(),
                equipment.getMobile(),
                equipment.getDepartment().getId(),
                equipment.getDepartment().getName(),
                currentRoom != null ? currentRoom.getId() : null,
                currentRoom != null ? currentRoom.getName() : null,
                currentRoom != null ? currentRoom.getDepartment().getName() : null,
                usageStatus,
                cleaningStatus,
                repairStatus,
                availability,
                assignedPerson != null ? assignedPerson.getId(): null,
                assignedPersonName
        );

    }

}
