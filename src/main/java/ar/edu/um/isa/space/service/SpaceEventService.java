package ar.edu.um.isa.space.service;

import ar.edu.um.isa.space.domain.SpaceEvent;
import ar.edu.um.isa.space.repository.SpaceEventRepository;
import ar.edu.um.isa.space.service.dto.SpaceEventDTO;
import ar.edu.um.isa.space.service.mapper.SpaceEventMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SpaceEvent}.
 */
@Service
@Transactional
public class SpaceEventService {

    private final Logger log = LoggerFactory.getLogger(SpaceEventService.class);

    private final SpaceEventRepository spaceEventRepository;

    private final SpaceEventMapper spaceEventMapper;

    public SpaceEventService(SpaceEventRepository spaceEventRepository, SpaceEventMapper spaceEventMapper) {
        this.spaceEventRepository = spaceEventRepository;
        this.spaceEventMapper = spaceEventMapper;
    }

    /**
     * Save a spaceEvent.
     *
     * @param spaceEventDTO the entity to save.
     * @return the persisted entity.
     */
    public SpaceEventDTO save(SpaceEventDTO spaceEventDTO) {
        log.debug("Request to save SpaceEvent : {}", spaceEventDTO);
        SpaceEvent spaceEvent = spaceEventMapper.toEntity(spaceEventDTO);
        spaceEvent = spaceEventRepository.save(spaceEvent);
        return spaceEventMapper.toDto(spaceEvent);
    }

    /**
     * Partially update a spaceEvent.
     *
     * @param spaceEventDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SpaceEventDTO> partialUpdate(SpaceEventDTO spaceEventDTO) {
        log.debug("Request to partially update SpaceEvent : {}", spaceEventDTO);

        return spaceEventRepository
            .findById(spaceEventDTO.getId())
            .map(existingSpaceEvent -> {
                spaceEventMapper.partialUpdate(existingSpaceEvent, spaceEventDTO);

                return existingSpaceEvent;
            })
            .map(spaceEventRepository::save)
            .map(spaceEventMapper::toDto);
    }

    /**
     * Get all the spaceEvents.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SpaceEventDTO> findAll() {
        log.debug("Request to get all SpaceEvents");
        return spaceEventRepository.findAll().stream().map(spaceEventMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one spaceEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpaceEventDTO> findOne(Long id) {
        log.debug("Request to get SpaceEvent : {}", id);
        return spaceEventRepository.findById(id).map(spaceEventMapper::toDto);
    }

    /**
     * Delete the spaceEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SpaceEvent : {}", id);
        spaceEventRepository.deleteById(id);
    }

    //-------------------------------------------------------------

    /**
     * get all SpaceEvent of SpaceShip by id
     * @return list of SpaceEvent
     */
    public Optional<SpaceEventDTO> findByMission(Long id) {
        log.debug("Request to get SpaceEvent by Mission : {}", id);
        return spaceEventRepository.findByMission(id).map(spaceEventMapper::toDto);
    }
}
