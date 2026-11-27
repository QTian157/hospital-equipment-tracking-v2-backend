package com.qt.hospital_equipment_tracking.mapper;

import com.qt.hospital_equipment_tracking.dto.response.PersonResponse;
import com.qt.hospital_equipment_tracking.model.Department;
import com.qt.hospital_equipment_tracking.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonResponse toResponse(Person person) {

        Department department = person.getDepartment();

        return new PersonResponse(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmployeeId(),
                person.getActive(),
                department != null ? department.getId() : null,
                department != null ? department.getName() : null
        );
    }
}