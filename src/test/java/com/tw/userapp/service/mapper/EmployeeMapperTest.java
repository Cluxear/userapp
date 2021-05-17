package com.tw.userapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;

public class EmployeeMapperTest {

    private EmployeeMapper employeeMapper;

    @BeforeEach
    public void setUp() {
        employeeMapper = new EmployeeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = UUID.randomUUID().toString();
        assertThat(employeeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeMapper.fromId(null)).isNull();
    }
}
