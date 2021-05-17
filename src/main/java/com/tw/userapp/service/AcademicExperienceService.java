package com.tw.userapp.service;

import com.tw.userapp.service.dto.AcademicExperienceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.userapp.domain.AcademicExperience}.
 */
public interface AcademicExperienceService {

    /**
     * Save a academicExperience.
     *
     * @param academicExperienceDTO the entity to save.
     * @return the persisted entity.
     */
    AcademicExperienceDTO save(AcademicExperienceDTO academicExperienceDTO);

    /**
     * Get all the academicExperiences.
     *
     * @return the list of entities.
     */
    List<AcademicExperienceDTO> findAll();


    /**
     * Get the "id" academicExperience.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcademicExperienceDTO> findOne(Long id);

    /**
     * Delete the "id" academicExperience.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
