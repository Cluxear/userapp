package com.tw.userapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.userapp.web.rest.TestUtil;

public class AcademicExperienceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicExperience.class);
        AcademicExperience academicExperience1 = new AcademicExperience();
        academicExperience1.setId(1L);
        AcademicExperience academicExperience2 = new AcademicExperience();
        academicExperience2.setId(academicExperience1.getId());
        assertThat(academicExperience1).isEqualTo(academicExperience2);
        academicExperience2.setId(2L);
        assertThat(academicExperience1).isNotEqualTo(academicExperience2);
        academicExperience1.setId(null);
        assertThat(academicExperience1).isNotEqualTo(academicExperience2);
    }
}
