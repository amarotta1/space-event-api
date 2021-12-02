package ar.edu.um.isa.space.repository;

import ar.edu.um.isa.space.domain.SpaceEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SpaceEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpaceEventRepository extends JpaRepository<SpaceEvent, Long> {}
