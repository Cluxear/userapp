package com.tw.userapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.userapp.web.rest.TestUtil;

public class CertificationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificationDTO.class);
        CertificationDTO certificationDTO1 = new CertificationDTO();
        certificationDTO1.setId(1L);
        CertificationDTO certificationDTO2 = new CertificationDTO();
        assertThat(certificationDTO1).isNotEqualTo(certificationDTO2);
        certificationDTO2.setId(certificationDTO1.getId());
        assertThat(certificationDTO1).isEqualTo(certificationDTO2);
        certificationDTO2.setId(2L);
        assertThat(certificationDTO1).isNotEqualTo(certificationDTO2);
        certificationDTO1.setId(null);
        assertThat(certificationDTO1).isNotEqualTo(certificationDTO2);
    }
}
