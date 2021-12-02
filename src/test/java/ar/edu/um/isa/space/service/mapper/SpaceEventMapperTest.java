package ar.edu.um.isa.space.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpaceEventMapperTest {

    private SpaceEventMapper spaceEventMapper;

    @BeforeEach
    public void setUp() {
        spaceEventMapper = new SpaceEventMapperImpl();
    }
}
