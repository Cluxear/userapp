package com.tw.userapp.web.rest;

import com.tw.userapp.UserappApp;
import com.tw.userapp.config.TestSecurityConfiguration;
import com.tw.userapp.domain.SeniorityLevel;
import com.tw.userapp.repository.SeniorityLevelRepository;
import com.tw.userapp.service.SeniorityLevelService;
import com.tw.userapp.service.dto.SeniorityLevelDTO;
import com.tw.userapp.service.mapper.SeniorityLevelMapper;

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
 * Integration tests for the {@link SeniorityLevelResource} REST controller.
 */
@SpringBootTest(classes = { UserappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SeniorityLevelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SeniorityLevelRepository seniorityLevelRepository;

    @Autowired
    private SeniorityLevelMapper seniorityLevelMapper;

    @Autowired
    private SeniorityLevelService seniorityLevelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeniorityLevelMockMvc;

    private SeniorityLevel seniorityLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeniorityLevel createEntity(EntityManager em) {
        SeniorityLevel seniorityLevel = new SeniorityLevel()
            .name(DEFAULT_NAME);
        return seniorityLevel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeniorityLevel createUpdatedEntity(EntityManager em) {
        SeniorityLevel seniorityLevel = new SeniorityLevel()
            .name(UPDATED_NAME);
        return seniorityLevel;
    }

    @BeforeEach
    public void initTest() {
        seniorityLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeniorityLevel() throws Exception {
        int databaseSizeBeforeCreate = seniorityLevelRepository.findAll().size();
        // Create the SeniorityLevel
        SeniorityLevelDTO seniorityLevelDTO = seniorityLevelMapper.toDto(seniorityLevel);
        restSeniorityLevelMockMvc.perform(post("/api/seniority-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seniorityLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the SeniorityLevel in the database
        List<SeniorityLevel> seniorityLevelList = seniorityLevelRepository.findAll();
        assertThat(seniorityLevelList).hasSize(databaseSizeBeforeCreate + 1);
        SeniorityLevel testSeniorityLevel = seniorityLevelList.get(seniorityLevelList.size() - 1);
        assertThat(testSeniorityLevel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSeniorityLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seniorityLevelRepository.findAll().size();

        // Create the SeniorityLevel with an existing ID
        seniorityLevel.setId(1L);
        SeniorityLevelDTO seniorityLevelDTO = seniorityLevelMapper.toDto(seniorityLevel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeniorityLevelMockMvc.perform(post("/api/seniority-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seniorityLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeniorityLevel in the database
        List<SeniorityLevel> seniorityLevelList = seniorityLevelRepository.findAll();
        assertThat(seniorityLevelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = seniorityLevelRepository.findAll().size();
        // set the field null
        seniorityLevel.setName(null);

        // Create the SeniorityLevel, which fails.
        SeniorityLevelDTO seniorityLevelDTO = seniorityLevelMapper.toDto(seniorityLevel);


        restSeniorityLevelMockMvc.perform(post("/api/seniority-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seniorityLevelDTO)))
            .andExpect(status().isBadRequest());

        List<SeniorityLevel> seniorityLevelList = seniorityLevelRepository.findAll();
        assertThat(seniorityLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeniorityLevels() throws Exception {
        // Initialize the database
        seniorityLevelRepository.saveAndFlush(seniorityLevel);

        // Get all the seniorityLevelList
        restSeniorityLevelMockMvc.perform(get("/api/seniority-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seniorityLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSeniorityLevel() throws Exception {
        // Initialize the database
        seniorityLevelRepository.saveAndFlush(seniorityLevel);

        // Get the seniorityLevel
        restSeniorityLevelMockMvc.perform(get("/api/seniority-levels/{id}", seniorityLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seniorityLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingSeniorityLevel() throws Exception {
        // Get the seniorityLevel
        restSeniorityLevelMockMvc.perform(get("/api/seniority-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeniorityLevel() throws Exception {
        // Initialize the database
        seniorityLevelRepository.saveAndFlush(seniorityLevel);

        int databaseSizeBeforeUpdate = seniorityLevelRepository.findAll().size();

        // Update the seniorityLevel
        SeniorityLevel updatedSeniorityLevel = seniorityLevelRepository.findById(seniorityLevel.getId()).get();
        // Disconnect from session so that the updates on updatedSeniorityLevel are not directly saved in db
        em.detach(updatedSeniorityLevel);
        updatedSeniorityLevel
            .name(UPDATED_NAME);
        SeniorityLevelDTO seniorityLevelDTO = seniorityLevelMapper.toDto(updatedSeniorityLevel);

        restSeniorityLevelMockMvc.perform(put("/api/seniority-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seniorityLevelDTO)))
            .andExpect(status().isOk());

        // Validate the SeniorityLevel in the database
        List<SeniorityLevel> seniorityLevelList = seniorityLevelRepository.findAll();
        assertThat(seniorityLevelList).hasSize(databaseSizeBeforeUpdate);
        SeniorityLevel testSeniorityLevel = seniorityLevelList.get(seniorityLevelList.size() - 1);
        assertThat(testSeniorityLevel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSeniorityLevel() throws Exception {
        int databaseSizeBeforeUpdate = seniorityLevelRepository.findAll().size();

        // Create the SeniorityLevel
        SeniorityLevelDTO seniorityLevelDTO = seniorityLevelMapper.toDto(seniorityLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeniorityLevelMockMvc.perform(put("/api/seniority-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seniorityLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeniorityLevel in the database
        List<SeniorityLevel> seniorityLevelList = seniorityLevelRepository.findAll();
        assertThat(seniorityLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeniorityLevel() throws Exception {
        // Initialize the database
        seniorityLevelRepository.saveAndFlush(seniorityLevel);

        int databaseSizeBeforeDelete = seniorityLevelRepository.findAll().size();

        // Delete the seniorityLevel
        restSeniorityLevelMockMvc.perform(delete("/api/seniority-levels/{id}", seniorityLevel.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeniorityLevel> seniorityLevelList = seniorityLevelRepository.findAll();
        assertThat(seniorityLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
