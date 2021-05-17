package com.tw.userapp.web.rest;

import com.tw.userapp.UserappApp;
import com.tw.userapp.config.TestSecurityConfiguration;
import com.tw.userapp.domain.AcademicExperience;
import com.tw.userapp.repository.AcademicExperienceRepository;
import com.tw.userapp.service.AcademicExperienceService;
import com.tw.userapp.service.dto.AcademicExperienceDTO;
import com.tw.userapp.service.mapper.AcademicExperienceMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AcademicExperienceResource} REST controller.
 */
@SpringBootTest(classes = { UserappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AcademicExperienceResourceIT {

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_DEGREE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AcademicExperienceRepository academicExperienceRepository;

    @Autowired
    private AcademicExperienceMapper academicExperienceMapper;

    @Autowired
    private AcademicExperienceService academicExperienceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicExperienceMockMvc;

    private AcademicExperience academicExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicExperience createEntity(EntityManager em) {
        AcademicExperience academicExperience = new AcademicExperience()
            .place(DEFAULT_PLACE)
            .degreeName(DEFAULT_DEGREE_NAME)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return academicExperience;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicExperience createUpdatedEntity(EntityManager em) {
        AcademicExperience academicExperience = new AcademicExperience()
            .place(UPDATED_PLACE)
            .degreeName(UPDATED_DEGREE_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return academicExperience;
    }

    @BeforeEach
    public void initTest() {
        academicExperience = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicExperience() throws Exception {
        int databaseSizeBeforeCreate = academicExperienceRepository.findAll().size();
        // Create the AcademicExperience
        AcademicExperienceDTO academicExperienceDTO = academicExperienceMapper.toDto(academicExperience);
        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicExperienceDTO)))
            .andExpect(status().isCreated());

        // Validate the AcademicExperience in the database
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicExperience testAcademicExperience = academicExperienceList.get(academicExperienceList.size() - 1);
        assertThat(testAcademicExperience.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testAcademicExperience.getDegreeName()).isEqualTo(DEFAULT_DEGREE_NAME);
        assertThat(testAcademicExperience.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAcademicExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAcademicExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createAcademicExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicExperienceRepository.findAll().size();

        // Create the AcademicExperience with an existing ID
        academicExperience.setId(1L);
        AcademicExperienceDTO academicExperienceDTO = academicExperienceMapper.toDto(academicExperience);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicExperienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicExperience in the database
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicExperienceRepository.findAll().size();
        // set the field null
        academicExperience.setPlace(null);

        // Create the AcademicExperience, which fails.
        AcademicExperienceDTO academicExperienceDTO = academicExperienceMapper.toDto(academicExperience);


        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicExperienceDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDegreeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicExperienceRepository.findAll().size();
        // set the field null
        academicExperience.setDegreeName(null);

        // Create the AcademicExperience, which fails.
        AcademicExperienceDTO academicExperienceDTO = academicExperienceMapper.toDto(academicExperience);


        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicExperienceDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcademicExperiences() throws Exception {
        // Initialize the database
        academicExperienceRepository.saveAndFlush(academicExperience);

        // Get all the academicExperienceList
        restAcademicExperienceMockMvc.perform(get("/api/academic-experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)))
            .andExpect(jsonPath("$.[*].degreeName").value(hasItem(DEFAULT_DEGREE_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAcademicExperience() throws Exception {
        // Initialize the database
        academicExperienceRepository.saveAndFlush(academicExperience);

        // Get the academicExperience
        restAcademicExperienceMockMvc.perform(get("/api/academic-experiences/{id}", academicExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academicExperience.getId().intValue()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE))
            .andExpect(jsonPath("$.degreeName").value(DEFAULT_DEGREE_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAcademicExperience() throws Exception {
        // Get the academicExperience
        restAcademicExperienceMockMvc.perform(get("/api/academic-experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicExperience() throws Exception {
        // Initialize the database
        academicExperienceRepository.saveAndFlush(academicExperience);

        int databaseSizeBeforeUpdate = academicExperienceRepository.findAll().size();

        // Update the academicExperience
        AcademicExperience updatedAcademicExperience = academicExperienceRepository.findById(academicExperience.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicExperience are not directly saved in db
        em.detach(updatedAcademicExperience);
        updatedAcademicExperience
            .place(UPDATED_PLACE)
            .degreeName(UPDATED_DEGREE_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        AcademicExperienceDTO academicExperienceDTO = academicExperienceMapper.toDto(updatedAcademicExperience);

        restAcademicExperienceMockMvc.perform(put("/api/academic-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicExperienceDTO)))
            .andExpect(status().isOk());

        // Validate the AcademicExperience in the database
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeUpdate);
        AcademicExperience testAcademicExperience = academicExperienceList.get(academicExperienceList.size() - 1);
        assertThat(testAcademicExperience.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testAcademicExperience.getDegreeName()).isEqualTo(UPDATED_DEGREE_NAME);
        assertThat(testAcademicExperience.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAcademicExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAcademicExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicExperience() throws Exception {
        int databaseSizeBeforeUpdate = academicExperienceRepository.findAll().size();

        // Create the AcademicExperience
        AcademicExperienceDTO academicExperienceDTO = academicExperienceMapper.toDto(academicExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicExperienceMockMvc.perform(put("/api/academic-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicExperienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicExperience in the database
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcademicExperience() throws Exception {
        // Initialize the database
        academicExperienceRepository.saveAndFlush(academicExperience);

        int databaseSizeBeforeDelete = academicExperienceRepository.findAll().size();

        // Delete the academicExperience
        restAcademicExperienceMockMvc.perform(delete("/api/academic-experiences/{id}", academicExperience.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
