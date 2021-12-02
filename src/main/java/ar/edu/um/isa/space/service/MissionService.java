package ar.edu.um.isa.space.service;

import ar.edu.um.isa.space.domain.Mission;
import ar.edu.um.isa.space.repository.MissionRepository;
import ar.edu.um.isa.space.service.dto.MissionDTO;
import ar.edu.um.isa.space.service.mapper.MissionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Mission}.
 */
@Service
@Transactional
public class MissionService {

    private final Logger log = LoggerFactory.getLogger(MissionService.class);

    private final MissionRepository missionRepository;

    private final MissionMapper missionMapper;

    public MissionService(MissionRepository missionRepository, MissionMapper missionMapper) {
        this.missionRepository = missionRepository;
        this.missionMapper = missionMapper;
    }

    /**
     * Save a mission.
     *
     * @param missionDTO the entity to save.
     * @return the persisted entity.
     */
    public MissionDTO save(MissionDTO missionDTO) {
        log.debug("Request to save Mission : {}", missionDTO);
        Mission mission = missionMapper.toEntity(missionDTO);
        mission = missionRepository.save(mission);
        return missionMapper.toDto(mission);
    }

    /**
     * Partially update a mission.
     *
     * @param missionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MissionDTO> partialUpdate(MissionDTO missionDTO) {
        log.debug("Request to partially update Mission : {}", missionDTO);

        return missionRepository
            .findById(missionDTO.getId())
            .map(existingMission -> {
                missionMapper.partialUpdate(existingMission, missionDTO);

                return existingMission;
            })
            .map(missionRepository::save)
            .map(missionMapper::toDto);
    }

    /**
     * Get all the missions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MissionDTO> findAll() {
        log.debug("Request to get all Missions");
        return missionRepository.findAll().stream().map(missionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MissionDTO> findOne(Long id) {
        log.debug("Request to get Mission : {}", id);
        return missionRepository.findById(id).map(missionMapper::toDto);
    }

    /**
     * Delete the mission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mission : {}", id);
        missionRepository.deleteById(id);
    }
}
