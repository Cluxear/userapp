package com.tw.userapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProfessionalExperienceMapperTest {

    private ProfessionalExperienceMapper professionalExperienceMapper;

    @BeforeEach
    public void setUp() {
        professionalExperienceMapper = new ProfessionalExperienceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(professionalExperienceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(professionalExperienceMapper.fromId(null)).isNull();
    }
}
