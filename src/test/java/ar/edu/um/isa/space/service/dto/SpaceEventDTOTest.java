package ar.edu.um.isa.space.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.isa.space.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpaceEventDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpaceEventDTO.class);
        SpaceEventDTO spaceEventDTO1 = new SpaceEventDTO();
        spaceEventDTO1.setId(1L);
        SpaceEventDTO spaceEventDTO2 = new SpaceEventDTO();
        assertThat(spaceEventDTO1).isNotEqualTo(spaceEventDTO2);
        spaceEventDTO2.setId(spaceEventDTO1.getId());
        assertThat(spaceEventDTO1).isEqualTo(spaceEventDTO2);
        spaceEventDTO2.setId(2L);
        assertThat(spaceEventDTO1).isNotEqualTo(spaceEventDTO2);
        spaceEventDTO1.setId(null);
        assertThat(spaceEventDTO1).isNotEqualTo(spaceEventDTO2);
    }
}
