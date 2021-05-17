package com.tw.userapp.service.impl;

import com.tw.userapp.service.ProfessionalExperienceService;
import com.tw.userapp.domain.ProfessionalExperience;
import com.tw.userapp.repository.ProfessionalExperienceRepository;
import com.tw.userapp.service.dto.ProfessionalExperienceDTO;
import com.tw.userapp.service.mapper.ProfessionalExperienceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProfessionalExperience}.
 */
@Service
@Transactional
public class ProfessionalExperienceServiceImpl implements ProfessionalExperienceService {

    private final Logger log = LoggerFactory.getLogger(ProfessionalExperienceServiceImpl.class);

    private final ProfessionalExperienceRepository professionalExperienceRepository;

    private final ProfessionalExperienceMapper professionalExperienceMapper;

    public ProfessionalExperienceServiceImpl(ProfessionalExperienceRepository professionalExperienceRepository, ProfessionalExperienceMapper professionalExperienceMapper) {
        this.professionalExperienceRepository = professionalExperienceRepository;
        this.professionalExperienceMapper = professionalExperienceMapper;
    }

    @Override
    public ProfessionalExperienceDTO save(ProfessionalExperienceDTO professionalExperienceDTO) {
        log.debug("Request to save ProfessionalExperience : {}", professionalExperienceDTO);
        ProfessionalExperience professionalExperience = professionalExperienceMapper.toEntity(professionalExperienceDTO);
        professionalExperience = professionalExperienceRepository.save(professionalExperience);
        return professionalExperienceMapper.toDto(professionalExperience);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalExperienceDTO> findAll() {
        log.debug("Request to get all ProfessionalExperiences");
        return professionalExperienceRepository.findAll().stream()
            .map(professionalExperienceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionalExperienceDTO> findOne(Long id) {
        log.debug("Request to get ProfessionalExperience : {}", id);
        return professionalExperienceRepository.findById(id)
            .map(professionalExperienceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfessionalExperience : {}", id);
        professionalExperienceRepository.deleteById(id);
    }
}
