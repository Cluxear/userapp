package com.tw.userapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.userapp.web.rest.TestUtil;

public class SeniorityLevelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeniorityLevelDTO.class);
        SeniorityLevelDTO seniorityLevelDTO1 = new SeniorityLevelDTO();
        seniorityLevelDTO1.setId(1L);
        SeniorityLevelDTO seniorityLevelDTO2 = new SeniorityLevelDTO();
        assertThat(seniorityLevelDTO1).isNotEqualTo(seniorityLevelDTO2);
        seniorityLevelDTO2.setId(seniorityLevelDTO1.getId());
        assertThat(seniorityLevelDTO1).isEqualTo(seniorityLevelDTO2);
        seniorityLevelDTO2.setId(2L);
        assertThat(seniorityLevelDTO1).isNotEqualTo(seniorityLevelDTO2);
        seniorityLevelDTO1.setId(null);
        assertThat(seniorityLevelDTO1).isNotEqualTo(seniorityLevelDTO2);
    }
}
