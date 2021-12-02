package ar.edu.um.isa.space.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpaceShipMapperTest {

    private SpaceShipMapper spaceShipMapper;

    @BeforeEach
    public void setUp() {
        spaceShipMapper = new SpaceShipMapperImpl();
    }
}
