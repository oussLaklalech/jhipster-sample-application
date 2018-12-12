package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Kitchen;
import io.github.jhipster.application.repository.KitchenRepository;
import io.github.jhipster.application.service.KitchenService;
import io.github.jhipster.application.service.dto.KitchenDTO;
import io.github.jhipster.application.service.mapper.KitchenMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KitchenResource REST controller.
 *
 * @see KitchenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class KitchenResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private KitchenMapper kitchenMapper;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKitchenMockMvc;

    private Kitchen kitchen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KitchenResource kitchenResource = new KitchenResource(kitchenService);
        this.restKitchenMockMvc = MockMvcBuilders.standaloneSetup(kitchenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kitchen createEntity(EntityManager em) {
        Kitchen kitchen = new Kitchen()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return kitchen;
    }

    @Before
    public void initTest() {
        kitchen = createEntity(em);
    }

    @Test
    @Transactional
    public void createKitchen() throws Exception {
        int databaseSizeBeforeCreate = kitchenRepository.findAll().size();

        // Create the Kitchen
        KitchenDTO kitchenDTO = kitchenMapper.toDto(kitchen);
        restKitchenMockMvc.perform(post("/api/kitchens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kitchenDTO)))
            .andExpect(status().isCreated());

        // Validate the Kitchen in the database
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeCreate + 1);
        Kitchen testKitchen = kitchenList.get(kitchenList.size() - 1);
        assertThat(testKitchen.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testKitchen.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKitchen.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createKitchenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kitchenRepository.findAll().size();

        // Create the Kitchen with an existing ID
        kitchen.setId(1L);
        KitchenDTO kitchenDTO = kitchenMapper.toDto(kitchen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKitchenMockMvc.perform(post("/api/kitchens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kitchenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kitchen in the database
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKitchens() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get all the kitchenList
        restKitchenMockMvc.perform(get("/api/kitchens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kitchen.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getKitchen() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get the kitchen
        restKitchenMockMvc.perform(get("/api/kitchens/{id}", kitchen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kitchen.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKitchen() throws Exception {
        // Get the kitchen
        restKitchenMockMvc.perform(get("/api/kitchens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKitchen() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        int databaseSizeBeforeUpdate = kitchenRepository.findAll().size();

        // Update the kitchen
        Kitchen updatedKitchen = kitchenRepository.findById(kitchen.getId()).get();
        // Disconnect from session so that the updates on updatedKitchen are not directly saved in db
        em.detach(updatedKitchen);
        updatedKitchen
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        KitchenDTO kitchenDTO = kitchenMapper.toDto(updatedKitchen);

        restKitchenMockMvc.perform(put("/api/kitchens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kitchenDTO)))
            .andExpect(status().isOk());

        // Validate the Kitchen in the database
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeUpdate);
        Kitchen testKitchen = kitchenList.get(kitchenList.size() - 1);
        assertThat(testKitchen.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testKitchen.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKitchen.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingKitchen() throws Exception {
        int databaseSizeBeforeUpdate = kitchenRepository.findAll().size();

        // Create the Kitchen
        KitchenDTO kitchenDTO = kitchenMapper.toDto(kitchen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKitchenMockMvc.perform(put("/api/kitchens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kitchenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kitchen in the database
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKitchen() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        int databaseSizeBeforeDelete = kitchenRepository.findAll().size();

        // Get the kitchen
        restKitchenMockMvc.perform(delete("/api/kitchens/{id}", kitchen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kitchen.class);
        Kitchen kitchen1 = new Kitchen();
        kitchen1.setId(1L);
        Kitchen kitchen2 = new Kitchen();
        kitchen2.setId(kitchen1.getId());
        assertThat(kitchen1).isEqualTo(kitchen2);
        kitchen2.setId(2L);
        assertThat(kitchen1).isNotEqualTo(kitchen2);
        kitchen1.setId(null);
        assertThat(kitchen1).isNotEqualTo(kitchen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KitchenDTO.class);
        KitchenDTO kitchenDTO1 = new KitchenDTO();
        kitchenDTO1.setId(1L);
        KitchenDTO kitchenDTO2 = new KitchenDTO();
        assertThat(kitchenDTO1).isNotEqualTo(kitchenDTO2);
        kitchenDTO2.setId(kitchenDTO1.getId());
        assertThat(kitchenDTO1).isEqualTo(kitchenDTO2);
        kitchenDTO2.setId(2L);
        assertThat(kitchenDTO1).isNotEqualTo(kitchenDTO2);
        kitchenDTO1.setId(null);
        assertThat(kitchenDTO1).isNotEqualTo(kitchenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(kitchenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(kitchenMapper.fromId(null)).isNull();
    }
}
