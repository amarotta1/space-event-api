package ar.edu.um.isa.space.web.rest;

import ar.edu.um.isa.space.repository.SpaceShipRepository;
import ar.edu.um.isa.space.service.SpaceShipService;
import ar.edu.um.isa.space.service.dto.SpaceShipDTO;
import ar.edu.um.isa.space.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.edu.um.isa.space.domain.SpaceShip}.
 */
@RestController
@RequestMapping("/api")
public class SpaceShipResource {

    private final Logger log = LoggerFactory.getLogger(SpaceShipResource.class);

    private static final String ENTITY_NAME = "spaceShip";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpaceShipService spaceShipService;

    private final SpaceShipRepository spaceShipRepository;

    public SpaceShipResource(SpaceShipService spaceShipService, SpaceShipRepository spaceShipRepository) {
        this.spaceShipService = spaceShipService;
        this.spaceShipRepository = spaceShipRepository;
    }

    /**
     * {@code POST  /space-ships} : Create a new spaceShip.
     *
     * @param spaceShipDTO the spaceShipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spaceShipDTO, or with status {@code 400 (Bad Request)} if the spaceShip has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/space-ships")
    public ResponseEntity<SpaceShipDTO> createSpaceShip(@Valid @RequestBody SpaceShipDTO spaceShipDTO) throws URISyntaxException {
        log.debug("REST request to save SpaceShip : {}", spaceShipDTO);
        if (spaceShipDTO.getId() != null) {
            throw new BadRequestAlertException("A new spaceShip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpaceShipDTO result = spaceShipService.save(spaceShipDTO);
        return ResponseEntity
            .created(new URI("/api/space-ships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /space-ships/:id} : Updates an existing spaceShip.
     *
     * @param id the id of the spaceShipDTO to save.
     * @param spaceShipDTO the spaceShipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spaceShipDTO,
     * or with status {@code 400 (Bad Request)} if the spaceShipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spaceShipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/space-ships/{id}")
    public ResponseEntity<SpaceShipDTO> updateSpaceShip(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SpaceShipDTO spaceShipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SpaceShip : {}, {}", id, spaceShipDTO);
        if (spaceShipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spaceShipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spaceShipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpaceShipDTO result = spaceShipService.save(spaceShipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spaceShipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /space-ships/:id} : Partial updates given fields of an existing spaceShip, field will ignore if it is null
     *
     * @param id the id of the spaceShipDTO to save.
     * @param spaceShipDTO the spaceShipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spaceShipDTO,
     * or with status {@code 400 (Bad Request)} if the spaceShipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the spaceShipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the spaceShipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/space-ships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpaceShipDTO> partialUpdateSpaceShip(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SpaceShipDTO spaceShipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpaceShip partially : {}, {}", id, spaceShipDTO);
        if (spaceShipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spaceShipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spaceShipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpaceShipDTO> result = spaceShipService.partialUpdate(spaceShipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spaceShipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /space-ships} : get all the spaceShips.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spaceShips in body.
     */
    @GetMapping("/space-ships")
    public List<SpaceShipDTO> getAllSpaceShips(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all SpaceShips");
        return spaceShipService.findAll();
    }

    /**
     * {@code GET  /space-ships/:id} : get the "id" spaceShip.
     *
     * @param id the id of the spaceShipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spaceShipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/space-ships/{id}")
    public ResponseEntity<SpaceShipDTO> getSpaceShip(@PathVariable Long id) {
        log.debug("REST request to get SpaceShip : {}", id);
        Optional<SpaceShipDTO> spaceShipDTO = spaceShipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spaceShipDTO);
    }

    /**
     * {@code DELETE  /space-ships/:id} : delete the "id" spaceShip.
     *
     * @param id the id of the spaceShipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/space-ships/{id}")
    public ResponseEntity<Void> deleteSpaceShip(@PathVariable Long id) {
        log.debug("REST request to delete SpaceShip : {}", id);
        spaceShipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    //------------------------------------------
    @GetMapping("/space-ships/mission/{id}")
    public List<SpaceShipDTO> getAllSpaceShipsByMission(@PathVariable Long id) {
        log.debug("REST request to get all SpaceShips by mission");
        return spaceShipService.findAllByMission(id);
    }
}
