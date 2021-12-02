package ar.edu.um.isa.space.service.mapper;

import ar.edu.um.isa.space.domain.SpaceShip;
import ar.edu.um.isa.space.service.dto.SpaceShipDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SpaceShip} and its DTO {@link SpaceShipDTO}.
 */
@Mapper(componentModel = "spring", uses = { ManufacturerMapper.class, MissionMapper.class })
public interface SpaceShipMapper extends EntityMapper<SpaceShipDTO, SpaceShip> {
    @Mapping(target = "manufacturer", source = "manufacturer", qualifiedByName = "id")
    @Mapping(target = "missions", source = "missions", qualifiedByName = "idSet")
    SpaceShipDTO toDto(SpaceShip s);

    @Mapping(target = "removeMission", ignore = true)
    SpaceShip toEntity(SpaceShipDTO spaceShipDTO);
}
