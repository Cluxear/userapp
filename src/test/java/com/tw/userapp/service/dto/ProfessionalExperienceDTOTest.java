package com.tw.userapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.userapp.web.rest.TestUtil;

public class ProfessionalExperienceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalExperienceDTO.class);
        ProfessionalExperienceDTO professionalExperienceDTO1 = new ProfessionalExperienceDTO();
        professionalExperienceDTO1.setId(1L);
        ProfessionalExperienceDTO professionalExperienceDTO2 = new ProfessionalExperienceDTO();
        assertThat(professionalExperienceDTO1).isNotEqualTo(professionalExperienceDTO2);
        professionalExperienceDTO2.setId(professionalExperienceDTO1.getId());
        assertThat(professionalExperienceDTO1).isEqualTo(professionalExperienceDTO2);
        professionalExperienceDTO2.setId(2L);
        assertThat(professionalExperienceDTO1).isNotEqualTo(professionalExperienceDTO2);
        professionalExperienceDTO1.setId(null);
        assertThat(professionalExperienceDTO1).isNotEqualTo(professionalExperienceDTO2);
    }
}
