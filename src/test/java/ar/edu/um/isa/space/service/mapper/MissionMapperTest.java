package ar.edu.um.isa.space.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MissionMapperTest {

    private MissionMapper missionMapper;

    @BeforeEach
    public void setUp() {
        missionMapper = new MissionMapperImpl();
    }
}
