package com.tw.userapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SeniorityLevelMapperTest {

    private SeniorityLevelMapper seniorityLevelMapper;

    @BeforeEach
    public void setUp() {
        seniorityLevelMapper = new SeniorityLevelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(seniorityLevelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(seniorityLevelMapper.fromId(null)).isNull();
    }
}
