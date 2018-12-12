package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Dish;
import io.github.jhipster.application.repository.DishRepository;
import io.github.jhipster.application.service.DishService;
import io.github.jhipster.application.service.dto.DishDTO;
import io.github.jhipster.application.service.mapper.DishMapper;
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
 * Test class for the DishResource REST controller.
 *
 * @see DishResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DishResourceIntTest {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTE = 1;
    private static final Integer UPDATED_NOTE = 2;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDishMockMvc;

    private Dish dish;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DishResource dishResource = new DishResource(dishService);
        this.restDishMockMvc = MockMvcBuilders.standaloneSetup(dishResource)
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
    public static Dish createEntity(EntityManager em) {
        Dish dish = new Dish()
            .price(DEFAULT_PRICE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .note(DEFAULT_NOTE);
        return dish;
    }

    @Before
    public void initTest() {
        dish = createEntity(em);
    }

    @Test
    @Transactional
    public void createDish() throws Exception {
        int databaseSizeBeforeCreate = dishRepository.findAll().size();

        // Create the Dish
        DishDTO dishDTO = dishMapper.toDto(dish);
        restDishMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishDTO)))
            .andExpect(status().isCreated());

        // Validate the Dish in the database
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeCreate + 1);
        Dish testDish = dishList.get(dishList.size() - 1);
        assertThat(testDish.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDish.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDish.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDish.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createDishWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dishRepository.findAll().size();

        // Create the Dish with an existing ID
        dish.setId(1L);
        DishDTO dishDTO = dishMapper.toDto(dish);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDishMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDishes() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        // Get all the dishList
        restDishMockMvc.perform(get("/api/dishes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dish.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        // Get the dish
        restDishMockMvc.perform(get("/api/dishes/{id}", dish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dish.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    public void getNonExistingDish() throws Exception {
        // Get the dish
        restDishMockMvc.perform(get("/api/dishes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        int databaseSizeBeforeUpdate = dishRepository.findAll().size();

        // Update the dish
        Dish updatedDish = dishRepository.findById(dish.getId()).get();
        // Disconnect from session so that the updates on updatedDish are not directly saved in db
        em.detach(updatedDish);
        updatedDish
            .price(UPDATED_PRICE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .note(UPDATED_NOTE);
        DishDTO dishDTO = dishMapper.toDto(updatedDish);

        restDishMockMvc.perform(put("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishDTO)))
            .andExpect(status().isOk());

        // Validate the Dish in the database
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeUpdate);
        Dish testDish = dishList.get(dishList.size() - 1);
        assertThat(testDish.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDish.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDish.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDish.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingDish() throws Exception {
        int databaseSizeBeforeUpdate = dishRepository.findAll().size();

        // Create the Dish
        DishDTO dishDTO = dishMapper.toDto(dish);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDishMockMvc.perform(put("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        int databaseSizeBeforeDelete = dishRepository.findAll().size();

        // Get the dish
        restDishMockMvc.perform(delete("/api/dishes/{id}", dish.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dish.class);
        Dish dish1 = new Dish();
        dish1.setId(1L);
        Dish dish2 = new Dish();
        dish2.setId(dish1.getId());
        assertThat(dish1).isEqualTo(dish2);
        dish2.setId(2L);
        assertThat(dish1).isNotEqualTo(dish2);
        dish1.setId(null);
        assertThat(dish1).isNotEqualTo(dish2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DishDTO.class);
        DishDTO dishDTO1 = new DishDTO();
        dishDTO1.setId(1L);
        DishDTO dishDTO2 = new DishDTO();
        assertThat(dishDTO1).isNotEqualTo(dishDTO2);
        dishDTO2.setId(dishDTO1.getId());
        assertThat(dishDTO1).isEqualTo(dishDTO2);
        dishDTO2.setId(2L);
        assertThat(dishDTO1).isNotEqualTo(dishDTO2);
        dishDTO1.setId(null);
        assertThat(dishDTO1).isNotEqualTo(dishDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dishMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dishMapper.fromId(null)).isNull();
    }
}
