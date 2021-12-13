package ar.edu.um.isa.space.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.isa.space.domain.Mission;
import ar.edu.um.isa.space.service.dto.MissionDTO;
import ar.edu.um.isa.space.web.rest.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MissionMapperTest {

    private MissionMapper missionMapper;

    @BeforeEach
    public void setUp() {
        missionMapper = new MissionMapperImpl();
    }

    @Test
    public void testMissionMapperCanConvertBackAndForth() throws Exception {
        TestUtil.equalsVerifier(Mission.class);
        Mission mission = new Mission().name("Mision espacial").description("Es una muy importante mision");
        MissionDTO missionDTO = missionMapper.toDto(mission);
        assertThat(missionMapper.toEntity(missionDTO)).usingRecursiveComparison().isEqualTo(mission);
    }

    @Test
    public void testMissionMapperNotEqual() throws Exception {
        TestUtil.equalsVerifier(Mission.class);
        Mission mission1 = new Mission().id(1L).name("Mision espacial").description("Es una muy importante mision");
        Mission mission2 = new Mission().id(2L).name("Mision espacial").description("Es una muy importante mision");
        MissionDTO missionDTO1 = missionMapper.toDto(mission1);
        assertThat(missionMapper.toEntity(missionDTO1)).usingRecursiveComparison().isNotEqualTo(mission2);
    }
}
