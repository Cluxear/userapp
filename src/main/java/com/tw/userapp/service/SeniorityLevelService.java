package com.tw.userapp.service;

import com.tw.userapp.service.dto.SeniorityLevelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.userapp.domain.SeniorityLevel}.
 */
public interface SeniorityLevelService {

    /**
     * Save a seniorityLevel.
     *
     * @param seniorityLevelDTO the entity to save.
     * @return the persisted entity.
     */
    SeniorityLevelDTO save(SeniorityLevelDTO seniorityLevelDTO);

    /**
     * Get all the seniorityLevels.
     *
     * @return the list of entities.
     */
    List<SeniorityLevelDTO> findAll();


    /**
     * Get the "id" seniorityLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeniorityLevelDTO> findOne(Long id);

    /**
     * Delete the "id" seniorityLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
