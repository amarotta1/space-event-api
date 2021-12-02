package ar.edu.um.isa.space.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.isa.space.IntegrationTest;
import ar.edu.um.isa.space.domain.SpaceShip;
import ar.edu.um.isa.space.domain.enumeration.SpaceShipThrusters;
import ar.edu.um.isa.space.repository.SpaceShipRepository;
import ar.edu.um.isa.space.service.SpaceShipService;
import ar.edu.um.isa.space.service.dto.SpaceShipDTO;
import ar.edu.um.isa.space.service.mapper.SpaceShipMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SpaceShipResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SpaceShipResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final SpaceShipThrusters DEFAULT_TYPE = SpaceShipThrusters.ION;
    private static final SpaceShipThrusters UPDATED_TYPE = SpaceShipThrusters.CHEMICAL;

    private static final String ENTITY_API_URL = "/api/space-ships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpaceShipRepository spaceShipRepository;

    @Mock
    private SpaceShipRepository spaceShipRepositoryMock;

    @Autowired
    private SpaceShipMapper spaceShipMapper;

    @Mock
    private SpaceShipService spaceShipServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpaceShipMockMvc;

    private SpaceShip spaceShip;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpaceShip createEntity(EntityManager em) {
        SpaceShip spaceShip = new SpaceShip().name(DEFAULT_NAME).type(DEFAULT_TYPE);
        return spaceShip;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpaceShip createUpdatedEntity(EntityManager em) {
        SpaceShip spaceShip = new SpaceShip().name(UPDATED_NAME).type(UPDATED_TYPE);
        return spaceShip;
    }

    @BeforeEach
    public void initTest() {
        spaceShip = createEntity(em);
    }

    @Test
    @Transactional
    void createSpaceShip() throws Exception {
        int databaseSizeBeforeCreate = spaceShipRepository.findAll().size();
        // Create the SpaceShip
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);
        restSpaceShipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spaceShipDTO)))
            .andExpect(status().isCreated());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeCreate + 1);
        SpaceShip testSpaceShip = spaceShipList.get(spaceShipList.size() - 1);
        assertThat(testSpaceShip.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpaceShip.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createSpaceShipWithExistingId() throws Exception {
        // Create the SpaceShip with an existing ID
        spaceShip.setId(1L);
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        int databaseSizeBeforeCreate = spaceShipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpaceShipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spaceShipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = spaceShipRepository.findAll().size();
        // set the field null
        spaceShip.setName(null);

        // Create the SpaceShip, which fails.
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        restSpaceShipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spaceShipDTO)))
            .andExpect(status().isBadRequest());

        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = spaceShipRepository.findAll().size();
        // set the field null
        spaceShip.setType(null);

        // Create the SpaceShip, which fails.
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        restSpaceShipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spaceShipDTO)))
            .andExpect(status().isBadRequest());

        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSpaceShips() throws Exception {
        // Initialize the database
        spaceShipRepository.saveAndFlush(spaceShip);

        // Get all the spaceShipList
        restSpaceShipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spaceShip.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSpaceShipsWithEagerRelationshipsIsEnabled() throws Exception {
        when(spaceShipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSpaceShipMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(spaceShipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSpaceShipsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(spaceShipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSpaceShipMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(spaceShipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSpaceShip() throws Exception {
        // Initialize the database
        spaceShipRepository.saveAndFlush(spaceShip);

        // Get the spaceShip
        restSpaceShipMockMvc
            .perform(get(ENTITY_API_URL_ID, spaceShip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(spaceShip.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSpaceShip() throws Exception {
        // Get the spaceShip
        restSpaceShipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSpaceShip() throws Exception {
        // Initialize the database
        spaceShipRepository.saveAndFlush(spaceShip);

        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();

        // Update the spaceShip
        SpaceShip updatedSpaceShip = spaceShipRepository.findById(spaceShip.getId()).get();
        // Disconnect from session so that the updates on updatedSpaceShip are not directly saved in db
        em.detach(updatedSpaceShip);
        updatedSpaceShip.name(UPDATED_NAME).type(UPDATED_TYPE);
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(updatedSpaceShip);

        restSpaceShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, spaceShipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(spaceShipDTO))
            )
            .andExpect(status().isOk());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
        SpaceShip testSpaceShip = spaceShipList.get(spaceShipList.size() - 1);
        assertThat(testSpaceShip.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpaceShip.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSpaceShip() throws Exception {
        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();
        spaceShip.setId(count.incrementAndGet());

        // Create the SpaceShip
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpaceShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, spaceShipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(spaceShipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpaceShip() throws Exception {
        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();
        spaceShip.setId(count.incrementAndGet());

        // Create the SpaceShip
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpaceShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(spaceShipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpaceShip() throws Exception {
        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();
        spaceShip.setId(count.incrementAndGet());

        // Create the SpaceShip
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpaceShipMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spaceShipDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpaceShipWithPatch() throws Exception {
        // Initialize the database
        spaceShipRepository.saveAndFlush(spaceShip);

        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();

        // Update the spaceShip using partial update
        SpaceShip partialUpdatedSpaceShip = new SpaceShip();
        partialUpdatedSpaceShip.setId(spaceShip.getId());

        restSpaceShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpaceShip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpaceShip))
            )
            .andExpect(status().isOk());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
        SpaceShip testSpaceShip = spaceShipList.get(spaceShipList.size() - 1);
        assertThat(testSpaceShip.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpaceShip.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSpaceShipWithPatch() throws Exception {
        // Initialize the database
        spaceShipRepository.saveAndFlush(spaceShip);

        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();

        // Update the spaceShip using partial update
        SpaceShip partialUpdatedSpaceShip = new SpaceShip();
        partialUpdatedSpaceShip.setId(spaceShip.getId());

        partialUpdatedSpaceShip.name(UPDATED_NAME).type(UPDATED_TYPE);

        restSpaceShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpaceShip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpaceShip))
            )
            .andExpect(status().isOk());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
        SpaceShip testSpaceShip = spaceShipList.get(spaceShipList.size() - 1);
        assertThat(testSpaceShip.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpaceShip.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSpaceShip() throws Exception {
        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();
        spaceShip.setId(count.incrementAndGet());

        // Create the SpaceShip
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpaceShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, spaceShipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(spaceShipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpaceShip() throws Exception {
        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();
        spaceShip.setId(count.incrementAndGet());

        // Create the SpaceShip
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpaceShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(spaceShipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpaceShip() throws Exception {
        int databaseSizeBeforeUpdate = spaceShipRepository.findAll().size();
        spaceShip.setId(count.incrementAndGet());

        // Create the SpaceShip
        SpaceShipDTO spaceShipDTO = spaceShipMapper.toDto(spaceShip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpaceShipMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(spaceShipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpaceShip in the database
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpaceShip() throws Exception {
        // Initialize the database
        spaceShipRepository.saveAndFlush(spaceShip);

        int databaseSizeBeforeDelete = spaceShipRepository.findAll().size();

        // Delete the spaceShip
        restSpaceShipMockMvc
            .perform(delete(ENTITY_API_URL_ID, spaceShip.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpaceShip> spaceShipList = spaceShipRepository.findAll();
        assertThat(spaceShipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
