package com.tw.userapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CertificationMapperTest {

    private CertificationMapper certificationMapper;

    @BeforeEach
    public void setUp() {
        certificationMapper = new CertificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(certificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(certificationMapper.fromId(null)).isNull();
    }
}
