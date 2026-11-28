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

import com.qt.hospital_equipment_tracking.dto.response.EquipmentDetailResponse;
import java.time.LocalDateTime;

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

    /**
     * Converts an Equipment entity to a detail response.
     *
     * @param equipment the equipment entity
     * @param nextScheduledAt the next scheduled maintenance time,
     *                        retrieved by the service layer; may be null
     * @return the equipment detail response
     */
    public EquipmentDetailResponse toDetailResponse(
            Equipment equipment,
            LocalDateTime nextScheduledAt) {

        EquipmentListResponse listResponse = toListResponse(equipment);

        Room currentRoom = equipment.getCurrentRoom();

        return new EquipmentDetailResponse(
                listResponse.getId(),
                listResponse.getName(),
                listResponse.getAssetTag(),
                listResponse.getType(),
                listResponse.getMobile(),
                listResponse.getDepartmentId(),
                listResponse.getDepartmentName(),
                listResponse.getCurrentRoomId(),
                listResponse.getCurrentRoomName(),
                listResponse.getCurrentRoomDepartmentName(),
                listResponse.getUsageStatus(),
                listResponse.getCleaningStatus(),
                listResponse.getRepairStatus(),
                listResponse.getAvailability(),
                listResponse.getAssignedPersonId(),
                listResponse.getAssignedPersonName(),
                equipment.getSerialNumber(),
                equipment.getCategory(),
                equipment.getRequiresCleaning(),
                currentRoom != null ? currentRoom.getDepartment().getId() : null,
                equipment.getAssignedAt(),
                equipment.getLastMaintenanceAt(),
                nextScheduledAt
        );
    }

}
