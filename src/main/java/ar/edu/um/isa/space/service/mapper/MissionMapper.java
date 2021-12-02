package ar.edu.um.isa.space.service.mapper;

import ar.edu.um.isa.space.domain.Mission;
import ar.edu.um.isa.space.service.dto.MissionDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mission} and its DTO {@link MissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MissionMapper extends EntityMapper<MissionDTO, Mission> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<MissionDTO> toDtoIdSet(Set<Mission> mission);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    MissionDTO toDtoName(Mission mission);
}
