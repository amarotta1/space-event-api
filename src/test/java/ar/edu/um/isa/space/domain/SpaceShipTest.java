package ar.edu.um.isa.space.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.isa.space.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpaceShipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpaceShip.class);
        SpaceShip spaceShip1 = new SpaceShip();
        spaceShip1.setId(1L);
        SpaceShip spaceShip2 = new SpaceShip();
        spaceShip2.setId(spaceShip1.getId());
        assertThat(spaceShip1).isEqualTo(spaceShip2);
        spaceShip2.setId(2L);
        assertThat(spaceShip1).isNotEqualTo(spaceShip2);
        spaceShip1.setId(null);
        assertThat(spaceShip1).isNotEqualTo(spaceShip2);
    }
}
