package com.peecko.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoachDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoachDTO.class);
        CoachDTO coachDTO1 = new CoachDTO();
        coachDTO1.setId(1L);
        CoachDTO coachDTO2 = new CoachDTO();
        assertThat(coachDTO1).isNotEqualTo(coachDTO2);
        coachDTO2.setId(coachDTO1.getId());
        assertThat(coachDTO1).isEqualTo(coachDTO2);
        coachDTO2.setId(2L);
        assertThat(coachDTO1).isNotEqualTo(coachDTO2);
        coachDTO1.setId(null);
        assertThat(coachDTO1).isNotEqualTo(coachDTO2);
    }
}
