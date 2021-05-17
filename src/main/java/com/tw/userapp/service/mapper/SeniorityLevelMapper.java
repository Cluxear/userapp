package com.tw.userapp.service.mapper;


import com.tw.userapp.domain.*;
import com.tw.userapp.service.dto.SeniorityLevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SeniorityLevel} and its DTO {@link SeniorityLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeniorityLevelMapper extends EntityMapper<SeniorityLevelDTO, SeniorityLevel> {


    @Mapping(target = "employes", ignore = true)
    @Mapping(target = "removeEmploye", ignore = true)
    @Mapping(target = "candidates", ignore = true)
    @Mapping(target = "removeCandidate", ignore = true)
    SeniorityLevel toEntity(SeniorityLevelDTO seniorityLevelDTO);

    default SeniorityLevel fromId(Long id) {
        if (id == null) {
            return null;
        }
        SeniorityLevel seniorityLevel = new SeniorityLevel();
        seniorityLevel.setId(id);
        return seniorityLevel;
    }
}
