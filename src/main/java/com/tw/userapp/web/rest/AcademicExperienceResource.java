package com.tw.userapp.web.rest;

import com.tw.userapp.service.AcademicExperienceService;
import com.tw.userapp.web.rest.errors.BadRequestAlertException;
import com.tw.userapp.service.dto.AcademicExperienceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tw.userapp.domain.AcademicExperience}.
 */
@RestController
@RequestMapping("/api")
public class AcademicExperienceResource {

    private final Logger log = LoggerFactory.getLogger(AcademicExperienceResource.class);

    private static final String ENTITY_NAME = "userappAcademicExperience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicExperienceService academicExperienceService;

    public AcademicExperienceResource(AcademicExperienceService academicExperienceService) {
        this.academicExperienceService = academicExperienceService;
    }

    /**
     * {@code POST  /academic-experiences} : Create a new academicExperience.
     *
     * @param academicExperienceDTO the academicExperienceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicExperienceDTO, or with status {@code 400 (Bad Request)} if the academicExperience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academic-experiences")
    public ResponseEntity<AcademicExperienceDTO> createAcademicExperience(@Valid @RequestBody AcademicExperienceDTO academicExperienceDTO) throws URISyntaxException {
        log.debug("REST request to save AcademicExperience : {}", academicExperienceDTO);
        if (academicExperienceDTO.getId() != null) {
            throw new BadRequestAlertException("A new academicExperience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicExperienceDTO result = academicExperienceService.save(academicExperienceDTO);
        return ResponseEntity.created(new URI("/api/academic-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-experiences} : Updates an existing academicExperience.
     *
     * @param academicExperienceDTO the academicExperienceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicExperienceDTO,
     * or with status {@code 400 (Bad Request)} if the academicExperienceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicExperienceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academic-experiences")
    public ResponseEntity<AcademicExperienceDTO> updateAcademicExperience(@Valid @RequestBody AcademicExperienceDTO academicExperienceDTO) throws URISyntaxException {
        log.debug("REST request to update AcademicExperience : {}", academicExperienceDTO);
        if (academicExperienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcademicExperienceDTO result = academicExperienceService.save(academicExperienceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicExperienceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /academic-experiences} : get all the academicExperiences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicExperiences in body.
     */
    @GetMapping("/academic-experiences")
    public List<AcademicExperienceDTO> getAllAcademicExperiences() {
        log.debug("REST request to get all AcademicExperiences");
        return academicExperienceService.findAll();
    }

    /**
     * {@code GET  /academic-experiences/:id} : get the "id" academicExperience.
     *
     * @param id the id of the academicExperienceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicExperienceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academic-experiences/{id}")
    public ResponseEntity<AcademicExperienceDTO> getAcademicExperience(@PathVariable Long id) {
        log.debug("REST request to get AcademicExperience : {}", id);
        Optional<AcademicExperienceDTO> academicExperienceDTO = academicExperienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicExperienceDTO);
    }

    /**
     * {@code DELETE  /academic-experiences/:id} : delete the "id" academicExperience.
     *
     * @param id the id of the academicExperienceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academic-experiences/{id}")
    public ResponseEntity<Void> deleteAcademicExperience(@PathVariable Long id) {
        log.debug("REST request to delete AcademicExperience : {}", id);
        academicExperienceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
