package com.tw.userapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.userapp.web.rest.TestUtil;

public class AcademicExperienceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicExperienceDTO.class);
        AcademicExperienceDTO academicExperienceDTO1 = new AcademicExperienceDTO();
        academicExperienceDTO1.setId(1L);
        AcademicExperienceDTO academicExperienceDTO2 = new AcademicExperienceDTO();
        assertThat(academicExperienceDTO1).isNotEqualTo(academicExperienceDTO2);
        academicExperienceDTO2.setId(academicExperienceDTO1.getId());
        assertThat(academicExperienceDTO1).isEqualTo(academicExperienceDTO2);
        academicExperienceDTO2.setId(2L);
        assertThat(academicExperienceDTO1).isNotEqualTo(academicExperienceDTO2);
        academicExperienceDTO1.setId(null);
        assertThat(academicExperienceDTO1).isNotEqualTo(academicExperienceDTO2);
    }
}
