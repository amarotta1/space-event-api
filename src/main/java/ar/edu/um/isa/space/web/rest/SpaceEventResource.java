package ar.edu.um.isa.space.web.rest;

import ar.edu.um.isa.space.repository.SpaceEventRepository;
import ar.edu.um.isa.space.service.SpaceEventService;
import ar.edu.um.isa.space.service.dto.SpaceEventDTO;
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
 * REST controller for managing {@link ar.edu.um.isa.space.domain.SpaceEvent}.
 */
@RestController
@RequestMapping("/api")
public class SpaceEventResource {

    private final Logger log = LoggerFactory.getLogger(SpaceEventResource.class);

    private static final String ENTITY_NAME = "spaceEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpaceEventService spaceEventService;

    private final SpaceEventRepository spaceEventRepository;

    public SpaceEventResource(SpaceEventService spaceEventService, SpaceEventRepository spaceEventRepository) {
        this.spaceEventService = spaceEventService;
        this.spaceEventRepository = spaceEventRepository;
    }

    /**
     * {@code POST  /space-events} : Create a new spaceEvent.
     *
     * @param spaceEventDTO the spaceEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spaceEventDTO, or with status {@code 400 (Bad Request)} if the spaceEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/space-events")
    public ResponseEntity<SpaceEventDTO> createSpaceEvent(@Valid @RequestBody SpaceEventDTO spaceEventDTO) throws URISyntaxException {
        log.debug("REST request to save SpaceEvent : {}", spaceEventDTO);
        if (spaceEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new spaceEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpaceEventDTO result = spaceEventService.save(spaceEventDTO);
        return ResponseEntity
            .created(new URI("/api/space-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /space-events/:id} : Updates an existing spaceEvent.
     *
     * @param id the id of the spaceEventDTO to save.
     * @param spaceEventDTO the spaceEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spaceEventDTO,
     * or with status {@code 400 (Bad Request)} if the spaceEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spaceEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/space-events/{id}")
    public ResponseEntity<SpaceEventDTO> updateSpaceEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SpaceEventDTO spaceEventDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SpaceEvent : {}, {}", id, spaceEventDTO);
        if (spaceEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spaceEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spaceEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpaceEventDTO result = spaceEventService.save(spaceEventDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spaceEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /space-events/:id} : Partial updates given fields of an existing spaceEvent, field will ignore if it is null
     *
     * @param id the id of the spaceEventDTO to save.
     * @param spaceEventDTO the spaceEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spaceEventDTO,
     * or with status {@code 400 (Bad Request)} if the spaceEventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the spaceEventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the spaceEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/space-events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpaceEventDTO> partialUpdateSpaceEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SpaceEventDTO spaceEventDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpaceEvent partially : {}, {}", id, spaceEventDTO);
        if (spaceEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spaceEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spaceEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpaceEventDTO> result = spaceEventService.partialUpdate(spaceEventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spaceEventDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /space-events} : get all the spaceEvents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spaceEvents in body.
     */
    @GetMapping("/space-events")
    public List<SpaceEventDTO> getAllSpaceEvents() {
        log.debug("REST request to get all SpaceEvents");
        return spaceEventService.findAll();
    }

    /**
     * {@code GET  /space-events/:id} : get the "id" spaceEvent.
     *
     * @param id the id of the spaceEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spaceEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/space-events/{id}")
    public ResponseEntity<SpaceEventDTO> getSpaceEvent(@PathVariable Long id) {
        log.debug("REST request to get SpaceEvent : {}", id);
        Optional<SpaceEventDTO> spaceEventDTO = spaceEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spaceEventDTO);
    }

    /**
     * {@code DELETE  /space-events/:id} : delete the "id" spaceEvent.
     *
     * @param id the id of the spaceEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/space-events/{id}")
    public ResponseEntity<Void> deleteSpaceEvent(@PathVariable Long id) {
        log.debug("REST request to delete SpaceEvent : {}", id);
        spaceEventService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
