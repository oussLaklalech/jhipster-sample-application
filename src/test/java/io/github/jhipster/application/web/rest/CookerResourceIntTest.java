package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Cooker;
import io.github.jhipster.application.repository.CookerRepository;
import io.github.jhipster.application.service.CookerService;
import io.github.jhipster.application.service.dto.CookerDTO;
import io.github.jhipster.application.service.mapper.CookerMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CookerResource REST controller.
 *
 * @see CookerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CookerResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private CookerRepository cookerRepository;

    @Autowired
    private CookerMapper cookerMapper;

    @Autowired
    private CookerService cookerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCookerMockMvc;

    private Cooker cooker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CookerResource cookerResource = new CookerResource(cookerService);
        this.restCookerMockMvc = MockMvcBuilders.standaloneSetup(cookerResource)
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
    public static Cooker createEntity(EntityManager em) {
        Cooker cooker = new Cooker()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .birthday(DEFAULT_BIRTHDAY)
            .address(DEFAULT_ADDRESS);
        return cooker;
    }

    @Before
    public void initTest() {
        cooker = createEntity(em);
    }

    @Test
    @Transactional
    public void createCooker() throws Exception {
        int databaseSizeBeforeCreate = cookerRepository.findAll().size();

        // Create the Cooker
        CookerDTO cookerDTO = cookerMapper.toDto(cooker);
        restCookerMockMvc.perform(post("/api/cookers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cookerDTO)))
            .andExpect(status().isCreated());

        // Validate the Cooker in the database
        List<Cooker> cookerList = cookerRepository.findAll();
        assertThat(cookerList).hasSize(databaseSizeBeforeCreate + 1);
        Cooker testCooker = cookerList.get(cookerList.size() - 1);
        assertThat(testCooker.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCooker.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCooker.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCooker.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCooker.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testCooker.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createCookerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cookerRepository.findAll().size();

        // Create the Cooker with an existing ID
        cooker.setId(1L);
        CookerDTO cookerDTO = cookerMapper.toDto(cooker);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCookerMockMvc.perform(post("/api/cookers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cookerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cooker in the database
        List<Cooker> cookerList = cookerRepository.findAll();
        assertThat(cookerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCookers() throws Exception {
        // Initialize the database
        cookerRepository.saveAndFlush(cooker);

        // Get all the cookerList
        restCookerMockMvc.perform(get("/api/cookers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cooker.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }
    
    @Test
    @Transactional
    public void getCooker() throws Exception {
        // Initialize the database
        cookerRepository.saveAndFlush(cooker);

        // Get the cooker
        restCookerMockMvc.perform(get("/api/cookers/{id}", cooker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cooker.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCooker() throws Exception {
        // Get the cooker
        restCookerMockMvc.perform(get("/api/cookers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCooker() throws Exception {
        // Initialize the database
        cookerRepository.saveAndFlush(cooker);

        int databaseSizeBeforeUpdate = cookerRepository.findAll().size();

        // Update the cooker
        Cooker updatedCooker = cookerRepository.findById(cooker.getId()).get();
        // Disconnect from session so that the updates on updatedCooker are not directly saved in db
        em.detach(updatedCooker);
        updatedCooker
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS);
        CookerDTO cookerDTO = cookerMapper.toDto(updatedCooker);

        restCookerMockMvc.perform(put("/api/cookers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cookerDTO)))
            .andExpect(status().isOk());

        // Validate the Cooker in the database
        List<Cooker> cookerList = cookerRepository.findAll();
        assertThat(cookerList).hasSize(databaseSizeBeforeUpdate);
        Cooker testCooker = cookerList.get(cookerList.size() - 1);
        assertThat(testCooker.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCooker.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCooker.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCooker.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCooker.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCooker.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingCooker() throws Exception {
        int databaseSizeBeforeUpdate = cookerRepository.findAll().size();

        // Create the Cooker
        CookerDTO cookerDTO = cookerMapper.toDto(cooker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCookerMockMvc.perform(put("/api/cookers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cookerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cooker in the database
        List<Cooker> cookerList = cookerRepository.findAll();
        assertThat(cookerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCooker() throws Exception {
        // Initialize the database
        cookerRepository.saveAndFlush(cooker);

        int databaseSizeBeforeDelete = cookerRepository.findAll().size();

        // Get the cooker
        restCookerMockMvc.perform(delete("/api/cookers/{id}", cooker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cooker> cookerList = cookerRepository.findAll();
        assertThat(cookerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cooker.class);
        Cooker cooker1 = new Cooker();
        cooker1.setId(1L);
        Cooker cooker2 = new Cooker();
        cooker2.setId(cooker1.getId());
        assertThat(cooker1).isEqualTo(cooker2);
        cooker2.setId(2L);
        assertThat(cooker1).isNotEqualTo(cooker2);
        cooker1.setId(null);
        assertThat(cooker1).isNotEqualTo(cooker2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CookerDTO.class);
        CookerDTO cookerDTO1 = new CookerDTO();
        cookerDTO1.setId(1L);
        CookerDTO cookerDTO2 = new CookerDTO();
        assertThat(cookerDTO1).isNotEqualTo(cookerDTO2);
        cookerDTO2.setId(cookerDTO1.getId());
        assertThat(cookerDTO1).isEqualTo(cookerDTO2);
        cookerDTO2.setId(2L);
        assertThat(cookerDTO1).isNotEqualTo(cookerDTO2);
        cookerDTO1.setId(null);
        assertThat(cookerDTO1).isNotEqualTo(cookerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cookerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cookerMapper.fromId(null)).isNull();
    }
}
