package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByDepartmentId(Long departmentId);
}