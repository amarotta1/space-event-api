package ar.edu.um.isa.space.repository;

import ar.edu.um.isa.space.domain.SpaceEvent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SpaceEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpaceEventRepository extends JpaRepository<SpaceEvent, Long> {
    @Query("select spaceEvent from SpaceEvent spaceEvent where spaceEvent.mission.id = :id")
    Optional<SpaceEvent> findByMission(@Param("id") Long id);
}
