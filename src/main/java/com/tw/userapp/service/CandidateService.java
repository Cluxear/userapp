package com.tw.userapp.service;

import com.tw.userapp.service.dto.CandidateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.userapp.domain.Candidate}.
 */
public interface CandidateService {

    /**
     * Save a candidate.
     *
     * @param candidateDTO the entity to save.
     * @return the persisted entity.
     */
    CandidateDTO save(CandidateDTO candidateDTO);

    /**
     * Get all the candidates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" candidate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidateDTO> findOne(String id);

    /**
     * Delete the "id" candidate.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Optional<CandidateDTO> findOneByUserLogin(String login);
}
