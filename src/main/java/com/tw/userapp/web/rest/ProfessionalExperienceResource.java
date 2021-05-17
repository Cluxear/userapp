package com.tw.userapp.web.rest;

import com.tw.userapp.service.ProfessionalExperienceService;
import com.tw.userapp.web.rest.errors.BadRequestAlertException;
import com.tw.userapp.service.dto.ProfessionalExperienceDTO;

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
 * REST controller for managing {@link com.tw.userapp.domain.ProfessionalExperience}.
 */
@RestController
@RequestMapping("/api")
public class ProfessionalExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionalExperienceResource.class);

    private static final String ENTITY_NAME = "userappProfessionalExperience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionalExperienceService professionalExperienceService;

    public ProfessionalExperienceResource(ProfessionalExperienceService professionalExperienceService) {
        this.professionalExperienceService = professionalExperienceService;
    }

    /**
     * {@code POST  /professional-experiences} : Create a new professionalExperience.
     *
     * @param professionalExperienceDTO the professionalExperienceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionalExperienceDTO, or with status {@code 400 (Bad Request)} if the professionalExperience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professional-experiences")
    public ResponseEntity<ProfessionalExperienceDTO> createProfessionalExperience(@Valid @RequestBody ProfessionalExperienceDTO professionalExperienceDTO) throws URISyntaxException {
        log.debug("REST request to save ProfessionalExperience : {}", professionalExperienceDTO);
        if (professionalExperienceDTO.getId() != null) {
            throw new BadRequestAlertException("A new professionalExperience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionalExperienceDTO result = professionalExperienceService.save(professionalExperienceDTO);
        return ResponseEntity.created(new URI("/api/professional-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professional-experiences} : Updates an existing professionalExperience.
     *
     * @param professionalExperienceDTO the professionalExperienceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionalExperienceDTO,
     * or with status {@code 400 (Bad Request)} if the professionalExperienceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionalExperienceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professional-experiences")
    public ResponseEntity<ProfessionalExperienceDTO> updateProfessionalExperience(@Valid @RequestBody ProfessionalExperienceDTO professionalExperienceDTO) throws URISyntaxException {
        log.debug("REST request to update ProfessionalExperience : {}", professionalExperienceDTO);
        if (professionalExperienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionalExperienceDTO result = professionalExperienceService.save(professionalExperienceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionalExperienceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professional-experiences} : get all the professionalExperiences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionalExperiences in body.
     */
    @GetMapping("/professional-experiences")
    public List<ProfessionalExperienceDTO> getAllProfessionalExperiences() {
        log.debug("REST request to get all ProfessionalExperiences");
        return professionalExperienceService.findAll();
    }

    /**
     * {@code GET  /professional-experiences/:id} : get the "id" professionalExperience.
     *
     * @param id the id of the professionalExperienceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionalExperienceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professional-experiences/{id}")
    public ResponseEntity<ProfessionalExperienceDTO> getProfessionalExperience(@PathVariable Long id) {
        log.debug("REST request to get ProfessionalExperience : {}", id);
        Optional<ProfessionalExperienceDTO> professionalExperienceDTO = professionalExperienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(professionalExperienceDTO);
    }

    /**
     * {@code DELETE  /professional-experiences/:id} : delete the "id" professionalExperience.
     *
     * @param id the id of the professionalExperienceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professional-experiences/{id}")
    public ResponseEntity<Void> deleteProfessionalExperience(@PathVariable Long id) {
        log.debug("REST request to delete ProfessionalExperience : {}", id);
        professionalExperienceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
