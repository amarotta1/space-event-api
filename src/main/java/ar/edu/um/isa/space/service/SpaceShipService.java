package ar.edu.um.isa.space.service;

import ar.edu.um.isa.space.domain.SpaceShip;
import ar.edu.um.isa.space.repository.SpaceShipRepository;
import ar.edu.um.isa.space.service.dto.SpaceShipDTO;
import ar.edu.um.isa.space.service.mapper.SpaceShipMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SpaceShip}.
 */
@Service
@Transactional
public class SpaceShipService {

    private final Logger log = LoggerFactory.getLogger(SpaceShipService.class);

    private final SpaceShipRepository spaceShipRepository;

    private final SpaceShipMapper spaceShipMapper;

    public SpaceShipService(SpaceShipRepository spaceShipRepository, SpaceShipMapper spaceShipMapper) {
        this.spaceShipRepository = spaceShipRepository;
        this.spaceShipMapper = spaceShipMapper;
    }

    /**
     * Save a spaceShip.
     *
     * @param spaceShipDTO the entity to save.
     * @return the persisted entity.
     */
    public SpaceShipDTO save(SpaceShipDTO spaceShipDTO) {
        log.debug("Request to save SpaceShip : {}", spaceShipDTO);
        SpaceShip spaceShip = spaceShipMapper.toEntity(spaceShipDTO);
        spaceShip = spaceShipRepository.save(spaceShip);
        return spaceShipMapper.toDto(spaceShip);
    }

    /**
     * Partially update a spaceShip.
     *
     * @param spaceShipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SpaceShipDTO> partialUpdate(SpaceShipDTO spaceShipDTO) {
        log.debug("Request to partially update SpaceShip : {}", spaceShipDTO);

        return spaceShipRepository
            .findById(spaceShipDTO.getId())
            .map(existingSpaceShip -> {
                spaceShipMapper.partialUpdate(existingSpaceShip, spaceShipDTO);

                return existingSpaceShip;
            })
            .map(spaceShipRepository::save)
            .map(spaceShipMapper::toDto);
    }

    /**
     * Get all the spaceShips.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SpaceShipDTO> findAll() {
        log.debug("Request to get all SpaceShips");
        return spaceShipRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(spaceShipMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the spaceShips with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SpaceShipDTO> findAllWithEagerRelationships(Pageable pageable) {
        return spaceShipRepository.findAllWithEagerRelationships(pageable).map(spaceShipMapper::toDto);
    }

    /**
     * Get one spaceShip by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpaceShipDTO> findOne(Long id) {
        log.debug("Request to get SpaceShip : {}", id);
        return spaceShipRepository.findOneWithEagerRelationships(id).map(spaceShipMapper::toDto);
    }

    /**
     * Delete the spaceShip by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SpaceShip : {}", id);
        spaceShipRepository.deleteById(id);
    }

    //----------------------------------------
    /**
     * Get all the spaceShips by Mission.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SpaceShipDTO> findAllByMission(Long id) {
        log.debug("Request to get all SpaceShips");
        return spaceShipRepository
            .findAllByMission(id)
            .stream()
            .map(spaceShipMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
