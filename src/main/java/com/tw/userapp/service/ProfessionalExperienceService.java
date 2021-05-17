package com.tw.userapp.service;

import com.tw.userapp.service.dto.ProfessionalExperienceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.userapp.domain.ProfessionalExperience}.
 */
public interface ProfessionalExperienceService {

    /**
     * Save a professionalExperience.
     *
     * @param professionalExperienceDTO the entity to save.
     * @return the persisted entity.
     */
    ProfessionalExperienceDTO save(ProfessionalExperienceDTO professionalExperienceDTO);

    /**
     * Get all the professionalExperiences.
     *
     * @return the list of entities.
     */
    List<ProfessionalExperienceDTO> findAll();


    /**
     * Get the "id" professionalExperience.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfessionalExperienceDTO> findOne(Long id);

    /**
     * Delete the "id" professionalExperience.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
