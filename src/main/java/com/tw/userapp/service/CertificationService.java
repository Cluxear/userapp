package com.tw.userapp.service;

import com.tw.userapp.service.dto.CertificationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.userapp.domain.Certification}.
 */
public interface CertificationService {

    /**
     * Save a certification.
     *
     * @param certificationDTO the entity to save.
     * @return the persisted entity.
     */
    CertificationDTO save(CertificationDTO certificationDTO);

    /**
     * Get all the certifications.
     *
     * @return the list of entities.
     */
    List<CertificationDTO> findAll();


    /**
     * Get the "id" certification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CertificationDTO> findOne(Long id);

    /**
     * Delete the "id" certification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
