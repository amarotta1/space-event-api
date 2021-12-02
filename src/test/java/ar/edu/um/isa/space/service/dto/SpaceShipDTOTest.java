package ar.edu.um.isa.space.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.isa.space.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpaceShipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpaceShipDTO.class);
        SpaceShipDTO spaceShipDTO1 = new SpaceShipDTO();
        spaceShipDTO1.setId(1L);
        SpaceShipDTO spaceShipDTO2 = new SpaceShipDTO();
        assertThat(spaceShipDTO1).isNotEqualTo(spaceShipDTO2);
        spaceShipDTO2.setId(spaceShipDTO1.getId());
        assertThat(spaceShipDTO1).isEqualTo(spaceShipDTO2);
        spaceShipDTO2.setId(2L);
        assertThat(spaceShipDTO1).isNotEqualTo(spaceShipDTO2);
        spaceShipDTO1.setId(null);
        assertThat(spaceShipDTO1).isNotEqualTo(spaceShipDTO2);
    }
}
