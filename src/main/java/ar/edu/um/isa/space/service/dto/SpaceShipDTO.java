package ar.edu.um.isa.space.service.dto;

import ar.edu.um.isa.space.domain.enumeration.SpaceShipThrusters;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ar.edu.um.isa.space.domain.SpaceShip} entity.
 */
public class SpaceShipDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private SpaceShipThrusters type;

    private ManufacturerDTO manufacturer;

    private Set<MissionDTO> missions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpaceShipThrusters getType() {
        return type;
    }

    public void setType(SpaceShipThrusters type) {
        this.type = type;
    }

    public ManufacturerDTO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDTO manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Set<MissionDTO> getMissions() {
        return missions;
    }

    public void setMissions(Set<MissionDTO> missions) {
        this.missions = missions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpaceShipDTO)) {
            return false;
        }

        SpaceShipDTO spaceShipDTO = (SpaceShipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, spaceShipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpaceShipDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", manufacturer=" + getManufacturer() +
            ", missions=" + getMissions() +
            "}";
    }
}
