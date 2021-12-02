package ar.edu.um.isa.space.service;

import ar.edu.um.isa.space.domain.Manufacturer;
import ar.edu.um.isa.space.repository.ManufacturerRepository;
import ar.edu.um.isa.space.service.dto.ManufacturerDTO;
import ar.edu.um.isa.space.service.mapper.ManufacturerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Manufacturer}.
 */
@Service
@Transactional
public class ManufacturerService {

    private final Logger log = LoggerFactory.getLogger(ManufacturerService.class);

    private final ManufacturerRepository manufacturerRepository;

    private final ManufacturerMapper manufacturerMapper;

    public ManufacturerService(ManufacturerRepository manufacturerRepository, ManufacturerMapper manufacturerMapper) {
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerMapper = manufacturerMapper;
    }

    /**
     * Save a manufacturer.
     *
     * @param manufacturerDTO the entity to save.
     * @return the persisted entity.
     */
    public ManufacturerDTO save(ManufacturerDTO manufacturerDTO) {
        log.debug("Request to save Manufacturer : {}", manufacturerDTO);
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDTO);
        manufacturer = manufacturerRepository.save(manufacturer);
        return manufacturerMapper.toDto(manufacturer);
    }

    /**
     * Partially update a manufacturer.
     *
     * @param manufacturerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ManufacturerDTO> partialUpdate(ManufacturerDTO manufacturerDTO) {
        log.debug("Request to partially update Manufacturer : {}", manufacturerDTO);

        return manufacturerRepository
            .findById(manufacturerDTO.getId())
            .map(existingManufacturer -> {
                manufacturerMapper.partialUpdate(existingManufacturer, manufacturerDTO);

                return existingManufacturer;
            })
            .map(manufacturerRepository::save)
            .map(manufacturerMapper::toDto);
    }

    /**
     * Get all the manufacturers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ManufacturerDTO> findAll() {
        log.debug("Request to get all Manufacturers");
        return manufacturerRepository.findAll().stream().map(manufacturerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one manufacturer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ManufacturerDTO> findOne(Long id) {
        log.debug("Request to get Manufacturer : {}", id);
        return manufacturerRepository.findById(id).map(manufacturerMapper::toDto);
    }

    /**
     * Delete the manufacturer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Manufacturer : {}", id);
        manufacturerRepository.deleteById(id);
    }
}
