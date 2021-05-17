package com.tw.userapp.service.mapper;


import com.tw.userapp.domain.*;
import com.tw.userapp.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PositionMapper.class, DegreeLevelMapper.class, SeniorityLevelMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "position.id", target = "positionId")
    @Mapping(source = "degree.id", target = "degreeId")
    @Mapping(source = "seniorityLevel.id", target = "seniorityLevelId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "positionId", target = "position")
    @Mapping(source = "degreeId", target = "degree")
    @Mapping(source = "seniorityLevelId", target = "seniorityLevel")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(String id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
