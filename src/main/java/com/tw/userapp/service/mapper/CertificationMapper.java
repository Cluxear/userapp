package com.tw.userapp.service.mapper;


import com.tw.userapp.domain.*;
import com.tw.userapp.service.dto.CertificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certification} and its DTO {@link CertificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface CertificationMapper extends EntityMapper<CertificationDTO, Certification> {

    @Mapping(source = "candidate.id", target = "candidateId")
    CertificationDTO toDto(Certification certification);

    @Mapping(source = "candidateId", target = "candidate")
    Certification toEntity(CertificationDTO certificationDTO);

    default Certification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Certification certification = new Certification();
        certification.setId(id);
        return certification;
    }
}
