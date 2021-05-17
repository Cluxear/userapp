package com.tw.userapp.web.rest;

import com.tw.userapp.UserappApp;
import com.tw.userapp.config.TestSecurityConfiguration;
import com.tw.userapp.domain.Certification;
import com.tw.userapp.repository.CertificationRepository;
import com.tw.userapp.service.CertificationService;
import com.tw.userapp.service.dto.CertificationDTO;
import com.tw.userapp.service.mapper.CertificationMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CertificationResource} REST controller.
 */
@SpringBootTest(classes = { UserappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CertificationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CertificationMapper certificationMapper;

    @Autowired
    private CertificationService certificationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertificationMockMvc;

    private Certification certification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certification createEntity(EntityManager em) {
        Certification certification = new Certification()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return certification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certification createUpdatedEntity(EntityManager em) {
        Certification certification = new Certification()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        return certification;
    }

    @BeforeEach
    public void initTest() {
        certification = createEntity(em);
    }

    @Test
    @Transactional
    public void createCertification() throws Exception {
        int databaseSizeBeforeCreate = certificationRepository.findAll().size();
        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);
        restCertificationMockMvc.perform(post("/api/certifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeCreate + 1);
        Certification testCertification = certificationList.get(certificationList.size() - 1);
        assertThat(testCertification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCertification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCertificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = certificationRepository.findAll().size();

        // Create the Certification with an existing ID
        certification.setId(1L);
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificationMockMvc.perform(post("/api/certifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificationRepository.findAll().size();
        // set the field null
        certification.setTitle(null);

        // Create the Certification, which fails.
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);


        restCertificationMockMvc.perform(post("/api/certifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isBadRequest());

        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCertifications() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList
        restCertificationMockMvc.perform(get("/api/certifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certification.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCertification() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get the certification
        restCertificationMockMvc.perform(get("/api/certifications/{id}", certification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certification.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingCertification() throws Exception {
        // Get the certification
        restCertificationMockMvc.perform(get("/api/certifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCertification() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();

        // Update the certification
        Certification updatedCertification = certificationRepository.findById(certification.getId()).get();
        // Disconnect from session so that the updates on updatedCertification are not directly saved in db
        em.detach(updatedCertification);
        updatedCertification
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        CertificationDTO certificationDTO = certificationMapper.toDto(updatedCertification);

        restCertificationMockMvc.perform(put("/api/certifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isOk());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
        Certification testCertification = certificationList.get(certificationList.size() - 1);
        assertThat(testCertification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCertification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCertification() throws Exception {
        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificationMockMvc.perform(put("/api/certifications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCertification() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        int databaseSizeBeforeDelete = certificationRepository.findAll().size();

        // Delete the certification
        restCertificationMockMvc.perform(delete("/api/certifications/{id}", certification.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
