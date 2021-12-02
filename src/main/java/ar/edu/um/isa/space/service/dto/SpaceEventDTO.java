package ar.edu.um.isa.space.service.dto;

import ar.edu.um.isa.space.domain.enumeration.SpaceEventType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ar.edu.um.isa.space.domain.SpaceEvent} entity.
 */
public class SpaceEventDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate date;

    @NotNull
    private String description;

    @Lob
    private byte[] photo;

    private String photoContentType;

    @NotNull
    private SpaceEventType type;

    private MissionDTO mission;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public SpaceEventType getType() {
        return type;
    }

    public void setType(SpaceEventType type) {
        this.type = type;
    }

    public MissionDTO getMission() {
        return mission;
    }

    public void setMission(MissionDTO mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpaceEventDTO)) {
            return false;
        }

        SpaceEventDTO spaceEventDTO = (SpaceEventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, spaceEventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpaceEventDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", type='" + getType() + "'" +
            ", mission=" + getMission() +
            "}";
    }
}
