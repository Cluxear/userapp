package com.tw.userapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AcademicExperienceMapperTest {

    private AcademicExperienceMapper academicExperienceMapper;

    @BeforeEach
    public void setUp() {
        academicExperienceMapper = new AcademicExperienceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(academicExperienceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(academicExperienceMapper.fromId(null)).isNull();
    }
}
