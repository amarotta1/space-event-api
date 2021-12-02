package ar.edu.um.isa.space.service.mapper;

import ar.edu.um.isa.space.domain.Manufacturer;
import ar.edu.um.isa.space.service.dto.ManufacturerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Manufacturer} and its DTO {@link ManufacturerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ManufacturerMapper extends EntityMapper<ManufacturerDTO, Manufacturer> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManufacturerDTO toDtoId(Manufacturer manufacturer);
}
