package ar.edu.um.isa.space.domain;

import ar.edu.um.isa.space.domain.enumeration.SpaceShipThrusters;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SpaceShip.
 */
@Entity
@Table(name = "space_ship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SpaceShip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SpaceShipThrusters type;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToMany
    @JoinTable(
        name = "rel_space_ship__mission",
        joinColumns = @JoinColumn(name = "space_ship_id"),
        inverseJoinColumns = @JoinColumn(name = "mission_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "spaceShips" }, allowSetters = true)
    private Set<Mission> missions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SpaceShip id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SpaceShip name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpaceShipThrusters getType() {
        return this.type;
    }

    public SpaceShip type(SpaceShipThrusters type) {
        this.setType(type);
        return this;
    }

    public void setType(SpaceShipThrusters type) {
        this.type = type;
    }

    public Manufacturer getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public SpaceShip manufacturer(Manufacturer manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public Set<Mission> getMissions() {
        return this.missions;
    }

    public void setMissions(Set<Mission> missions) {
        this.missions = missions;
    }

    public SpaceShip missions(Set<Mission> missions) {
        this.setMissions(missions);
        return this;
    }

    public SpaceShip addMission(Mission mission) {
        this.missions.add(mission);
        mission.getSpaceShips().add(this);
        return this;
    }

    public SpaceShip removeMission(Mission mission) {
        this.missions.remove(mission);
        mission.getSpaceShips().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpaceShip)) {
            return false;
        }
        return id != null && id.equals(((SpaceShip) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpaceShip{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
