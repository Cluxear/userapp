package com.tw.userapp.service.impl;

import com.tw.userapp.service.AcademicExperienceService;
import com.tw.userapp.domain.AcademicExperience;
import com.tw.userapp.repository.AcademicExperienceRepository;
import com.tw.userapp.service.dto.AcademicExperienceDTO;
import com.tw.userapp.service.mapper.AcademicExperienceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AcademicExperience}.
 */
@Service
@Transactional
public class AcademicExperienceServiceImpl implements AcademicExperienceService {

    private final Logger log = LoggerFactory.getLogger(AcademicExperienceServiceImpl.class);

    private final AcademicExperienceRepository academicExperienceRepository;

    private final AcademicExperienceMapper academicExperienceMapper;

    public AcademicExperienceServiceImpl(AcademicExperienceRepository academicExperienceRepository, AcademicExperienceMapper academicExperienceMapper) {
        this.academicExperienceRepository = academicExperienceRepository;
        this.academicExperienceMapper = academicExperienceMapper;
    }

    @Override
    public AcademicExperienceDTO save(AcademicExperienceDTO academicExperienceDTO) {
        log.debug("Request to save AcademicExperience : {}", academicExperienceDTO);
        AcademicExperience academicExperience = academicExperienceMapper.toEntity(academicExperienceDTO);
        academicExperience = academicExperienceRepository.save(academicExperience);
        return academicExperienceMapper.toDto(academicExperience);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicExperienceDTO> findAll() {
        log.debug("Request to get all AcademicExperiences");
        return academicExperienceRepository.findAll().stream()
            .map(academicExperienceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicExperienceDTO> findOne(Long id) {
        log.debug("Request to get AcademicExperience : {}", id);
        return academicExperienceRepository.findById(id)
            .map(academicExperienceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcademicExperience : {}", id);
        academicExperienceRepository.deleteById(id);
    }
}
