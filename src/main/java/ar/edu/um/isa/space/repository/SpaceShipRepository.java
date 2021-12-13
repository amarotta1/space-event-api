package ar.edu.um.isa.space.repository;

import ar.edu.um.isa.space.domain.SpaceShip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SpaceShip entity.
 */
@Repository
public interface SpaceShipRepository extends JpaRepository<SpaceShip, Long> {
    @Query(
        value = "select distinct spaceShip from SpaceShip spaceShip left join fetch spaceShip.missions",
        countQuery = "select count(distinct spaceShip) from SpaceShip spaceShip"
    )
    Page<SpaceShip> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct spaceShip from SpaceShip spaceShip left join fetch spaceShip.missions")
    List<SpaceShip> findAllWithEagerRelationships();

    @Query("select spaceShip from SpaceShip spaceShip left join fetch spaceShip.missions where spaceShip.id =:id")
    Optional<SpaceShip> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select spaceShip from SpaceShip spaceShip left join spaceShip.missions mission where mission.id =:id")
    List<SpaceShip> findAllByMission(@Param("id") Long id);
}
