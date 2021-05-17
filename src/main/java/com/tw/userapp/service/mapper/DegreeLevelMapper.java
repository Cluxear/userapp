package com.tw.userapp.service.mapper;


import com.tw.userapp.domain.*;
import com.tw.userapp.service.dto.DegreeLevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DegreeLevel} and its DTO {@link DegreeLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DegreeLevelMapper extends EntityMapper<DegreeLevelDTO, DegreeLevel> {


    @Mapping(target = "candidates", ignore = true)
    @Mapping(target = "removeCandidate", ignore = true)
    @Mapping(target = "employes", ignore = true)
    @Mapping(target = "removeEmploye", ignore = true)
    DegreeLevel toEntity(DegreeLevelDTO degreeLevelDTO);

    default DegreeLevel fromId(Long id) {
        if (id == null) {
            return null;
        }
        DegreeLevel degreeLevel = new DegreeLevel();
        degreeLevel.setId(id);
        return degreeLevel;
    }
}
