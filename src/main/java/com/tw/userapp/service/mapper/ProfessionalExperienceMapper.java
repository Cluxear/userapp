package com.tw.userapp.service.mapper;


import com.tw.userapp.domain.*;
import com.tw.userapp.service.dto.ProfessionalExperienceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProfessionalExperience} and its DTO {@link ProfessionalExperienceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class, PositionMapper.class})
public interface ProfessionalExperienceMapper extends EntityMapper<ProfessionalExperienceDTO, ProfessionalExperience> {

	@Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source= "position.id", target="positionId")
    @Mapping(source= "position.jobTitle", target="positionTitle")
    ProfessionalExperienceDTO toDto(ProfessionalExperience professionalExperience);

	@Mapping(source = "candidateId", target = "candidate.id")
    @Mapping(source = "positionId", target = "position")
    ProfessionalExperience toEntity(ProfessionalExperienceDTO professionalExperienceDTO);

    default ProfessionalExperience fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfessionalExperience professionalExperience = new ProfessionalExperience();
        professionalExperience.setId(id);
        return professionalExperience;
    }
}
