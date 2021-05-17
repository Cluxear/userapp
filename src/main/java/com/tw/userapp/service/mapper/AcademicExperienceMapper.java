package com.tw.userapp.service.mapper;


import com.tw.userapp.domain.*;
import com.tw.userapp.service.dto.AcademicExperienceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AcademicExperience} and its DTO {@link AcademicExperienceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface AcademicExperienceMapper extends EntityMapper<AcademicExperienceDTO, AcademicExperience> {

    @Mapping(source = "candidate.id", target = "candidateId")
    AcademicExperienceDTO toDto(AcademicExperience academicExperience);

    @Mapping(source = "candidateId", target = "candidate")
    AcademicExperience toEntity(AcademicExperienceDTO academicExperienceDTO);

    default AcademicExperience fromId(Long id) {
        if (id == null) {
            return null;
        }
        AcademicExperience academicExperience = new AcademicExperience();
        academicExperience.setId(id);
        return academicExperience;
    }
}
