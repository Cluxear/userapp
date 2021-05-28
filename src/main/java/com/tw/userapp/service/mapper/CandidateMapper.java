package com.tw.userapp.service.mapper;


import com.tw.userapp.domain.*;
import com.tw.userapp.repository.UserRepository;
import com.tw.userapp.service.dto.CandidateDTO;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Candidate} and its DTO {@link CandidateDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DegreeLevelMapper.class, ExperienceDurationMapper.class})
public interface CandidateMapper extends EntityMapper<CandidateDTO, Candidate> {




    @Mapping(source ="user.firstName", target="firstName")
    @Mapping(source ="user.lastName", target="lastName")
    @Mapping(source ="user.email", target="email")
    @Mapping(source ="user.login", target="login")
    @Mapping(source ="user.id", target="id")
    @Mapping(source ="degree.id", target="degreeId")
    @Mapping(source ="experienceDuration.id", target="experienceDurationId")
    @Mapping(source ="experienceDuration.value", target="experienceDurationName")
    @Mapping(source ="degree.name", target="degreeName")
    CandidateDTO toDto(Candidate candidate);

    @Mapping(source = "id", target = "user")
    @Mapping(source ="firstName", target="user.firstName")
    @Mapping(source ="lastName", target="user.lastName")
    @Mapping(source ="email", target="user.email")
    @Mapping(source ="degreeId", target="degree")
    @Mapping(source="experienceDurationId", target = "experienceDuration")
    Candidate toEntity(CandidateDTO candidateDTO);

    default Candidate fromId(String id) {
        if (id == null) {
            return null;
        }
        Candidate candidate = new Candidate();
        candidate.setId(id);
        return candidate;
    }

}
