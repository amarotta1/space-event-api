package ar.edu.um.isa.space.service.mapper;

import ar.edu.um.isa.space.domain.SpaceEvent;
import ar.edu.um.isa.space.service.dto.SpaceEventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SpaceEvent} and its DTO {@link SpaceEventDTO}.
 */
@Mapper(componentModel = "spring", uses = { MissionMapper.class })
public interface SpaceEventMapper extends EntityMapper<SpaceEventDTO, SpaceEvent> {
    @Mapping(target = "mission", source = "mission", qualifiedByName = "name")
    SpaceEventDTO toDto(SpaceEvent s);
}
