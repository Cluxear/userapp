package com.tw.userapp.web.rest;

import com.tw.userapp.UserappApp;
import com.tw.userapp.config.TestSecurityConfiguration;
import com.tw.userapp.domain.ProfessionalExperience;
import com.tw.userapp.repository.ProfessionalExperienceRepository;
import com.tw.userapp.service.ProfessionalExperienceService;
import com.tw.userapp.service.dto.ProfessionalExperienceDTO;
import com.tw.userapp.service.mapper.ProfessionalExperienceMapper;

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
 * Integration tests for the {@link ProfessionalExperienceResource} REST controller.
 */
@SpringBootTest(classes = { UserappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProfessionalExperienceResourceIT {

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_POST = "AAAAAAAAAA";
    private static final String UPDATED_POST = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProfessionalExperienceRepository professionalExperienceRepository;

    @Autowired
    private ProfessionalExperienceMapper professionalExperienceMapper;

    @Autowired
    private ProfessionalExperienceService professionalExperienceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfessionalExperienceMockMvc;

    private ProfessionalExperience professionalExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfessionalExperience createEntity(EntityManager em) {
        ProfessionalExperience professionalExperience = new ProfessionalExperience()
            .place(DEFAULT_PLACE)
            .post(DEFAULT_POST)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return professionalExperience;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfessionalExperience createUpdatedEntity(EntityManager em) {
        ProfessionalExperience professionalExperience = new ProfessionalExperience()
            .place(UPDATED_PLACE)
            .post(UPDATED_POST)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return professionalExperience;
    }

    @BeforeEach
    public void initTest() {
        professionalExperience = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessionalExperience() throws Exception {
        int databaseSizeBeforeCreate = professionalExperienceRepository.findAll().size();
        // Create the ProfessionalExperience
        ProfessionalExperienceDTO professionalExperienceDTO = professionalExperienceMapper.toDto(professionalExperience);
        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperienceDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfessionalExperience in the database
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        ProfessionalExperience testProfessionalExperience = professionalExperienceList.get(professionalExperienceList.size() - 1);
        assertThat(testProfessionalExperience.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testProfessionalExperience.getPosition()).isEqualTo(DEFAULT_POST);
        assertThat(testProfessionalExperience.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProfessionalExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProfessionalExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createProfessionalExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionalExperienceRepository.findAll().size();

        // Create the ProfessionalExperience with an existing ID
        professionalExperience.setId(1L);
        ProfessionalExperienceDTO professionalExperienceDTO = professionalExperienceMapper.toDto(professionalExperience);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalExperience in the database
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionalExperienceRepository.findAll().size();
        // set the field null
        professionalExperience.setPlace(null);

        // Create the ProfessionalExperience, which fails.
        ProfessionalExperienceDTO professionalExperienceDTO = professionalExperienceMapper.toDto(professionalExperience);


        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperienceDTO)))
            .andExpect(status().isBadRequest());

        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionalExperienceRepository.findAll().size();
        // set the field null
        professionalExperience.setPosition(null);

        // Create the ProfessionalExperience, which fails.
        ProfessionalExperienceDTO professionalExperienceDTO = professionalExperienceMapper.toDto(professionalExperience);


        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperienceDTO)))
            .andExpect(status().isBadRequest());

        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfessionalExperiences() throws Exception {
        // Initialize the database
        professionalExperienceRepository.saveAndFlush(professionalExperience);

        // Get all the professionalExperienceList
        restProfessionalExperienceMockMvc.perform(get("/api/professional-experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionalExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getProfessionalExperience() throws Exception {
        // Initialize the database
        professionalExperienceRepository.saveAndFlush(professionalExperience);

        // Get the professionalExperience
        restProfessionalExperienceMockMvc.perform(get("/api/professional-experiences/{id}", professionalExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(professionalExperience.getId().intValue()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProfessionalExperience() throws Exception {
        // Get the professionalExperience
        restProfessionalExperienceMockMvc.perform(get("/api/professional-experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessionalExperience() throws Exception {
        // Initialize the database
        professionalExperienceRepository.saveAndFlush(professionalExperience);

        int databaseSizeBeforeUpdate = professionalExperienceRepository.findAll().size();

        // Update the professionalExperience
        ProfessionalExperience updatedProfessionalExperience = professionalExperienceRepository.findById(professionalExperience.getId()).get();
        // Disconnect from session so that the updates on updatedProfessionalExperience are not directly saved in db
        em.detach(updatedProfessionalExperience);
        updatedProfessionalExperience
            .place(UPDATED_PLACE)
            .post(UPDATED_POST)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        ProfessionalExperienceDTO professionalExperienceDTO = professionalExperienceMapper.toDto(updatedProfessionalExperience);

        restProfessionalExperienceMockMvc.perform(put("/api/professional-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperienceDTO)))
            .andExpect(status().isOk());

        // Validate the ProfessionalExperience in the database
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeUpdate);
        ProfessionalExperience testProfessionalExperience = professionalExperienceList.get(professionalExperienceList.size() - 1);
        assertThat(testProfessionalExperience.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testProfessionalExperience.getPosition()).isEqualTo(UPDATED_POST);
        assertThat(testProfessionalExperience.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProfessionalExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProfessionalExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProfessionalExperience() throws Exception {
        int databaseSizeBeforeUpdate = professionalExperienceRepository.findAll().size();

        // Create the ProfessionalExperience
        ProfessionalExperienceDTO professionalExperienceDTO = professionalExperienceMapper.toDto(professionalExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionalExperienceMockMvc.perform(put("/api/professional-experiences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalExperience in the database
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfessionalExperience() throws Exception {
        // Initialize the database
        professionalExperienceRepository.saveAndFlush(professionalExperience);

        int databaseSizeBeforeDelete = professionalExperienceRepository.findAll().size();

        // Delete the professionalExperience
        restProfessionalExperienceMockMvc.perform(delete("/api/professional-experiences/{id}", professionalExperience.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
