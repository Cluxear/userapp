package com.tw.userapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;

public class CandidateMapperTest {

    private CandidateMapper candidateMapper;

    @BeforeEach
    public void setUp() {
        candidateMapper = new CandidateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = UUID.randomUUID().toString();
        assertThat(candidateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(candidateMapper.fromId(null)).isNull();
    }
}
