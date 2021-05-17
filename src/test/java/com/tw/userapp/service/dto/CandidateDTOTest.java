package com.tw.userapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.userapp.web.rest.TestUtil;
import java.util.UUID;

public class CandidateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateDTO.class);
        CandidateDTO candidateDTO1 = new CandidateDTO();
        candidateDTO1.setId(UUID.randomUUID().toString());
        CandidateDTO candidateDTO2 = new CandidateDTO();
        assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
        candidateDTO2.setId(candidateDTO1.getId());
        assertThat(candidateDTO1).isEqualTo(candidateDTO2);
        candidateDTO2.setId(UUID.randomUUID().toString());
        assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
        candidateDTO1.setId(null);
        assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
    }
}
