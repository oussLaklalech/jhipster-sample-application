package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.OrderPreparation;
import io.github.jhipster.application.repository.OrderPreparationRepository;
import io.github.jhipster.application.service.OrderPreparationService;
import io.github.jhipster.application.service.dto.OrderPreparationDTO;
import io.github.jhipster.application.service.mapper.OrderPreparationMapper;
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
 * Test class for the OrderPreparationResource REST controller.
 *
 * @see OrderPreparationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class OrderPreparationResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Instant DEFAULT_DATE_ORDER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ORDER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_DELIVERY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DELIVERY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SPECIAL_INSTRUCTION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_INSTRUCTION = "BBBBBBBBBB";

    @Autowired
    private OrderPreparationRepository orderPreparationRepository;

    @Autowired
    private OrderPreparationMapper orderPreparationMapper;

    @Autowired
    private OrderPreparationService orderPreparationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderPreparationMockMvc;

    private OrderPreparation orderPreparation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderPreparationResource orderPreparationResource = new OrderPreparationResource(orderPreparationService);
        this.restOrderPreparationMockMvc = MockMvcBuilders.standaloneSetup(orderPreparationResource)
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
    public static OrderPreparation createEntity(EntityManager em) {
        OrderPreparation orderPreparation = new OrderPreparation()
            .quantity(DEFAULT_QUANTITY)
            .dateOrder(DEFAULT_DATE_ORDER)
            .dateDelivery(DEFAULT_DATE_DELIVERY)
            .specialInstruction(DEFAULT_SPECIAL_INSTRUCTION);
        return orderPreparation;
    }

    @Before
    public void initTest() {
        orderPreparation = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderPreparation() throws Exception {
        int databaseSizeBeforeCreate = orderPreparationRepository.findAll().size();

        // Create the OrderPreparation
        OrderPreparationDTO orderPreparationDTO = orderPreparationMapper.toDto(orderPreparation);
        restOrderPreparationMockMvc.perform(post("/api/order-preparations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPreparationDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderPreparation in the database
        List<OrderPreparation> orderPreparationList = orderPreparationRepository.findAll();
        assertThat(orderPreparationList).hasSize(databaseSizeBeforeCreate + 1);
        OrderPreparation testOrderPreparation = orderPreparationList.get(orderPreparationList.size() - 1);
        assertThat(testOrderPreparation.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderPreparation.getDateOrder()).isEqualTo(DEFAULT_DATE_ORDER);
        assertThat(testOrderPreparation.getDateDelivery()).isEqualTo(DEFAULT_DATE_DELIVERY);
        assertThat(testOrderPreparation.getSpecialInstruction()).isEqualTo(DEFAULT_SPECIAL_INSTRUCTION);
    }

    @Test
    @Transactional
    public void createOrderPreparationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderPreparationRepository.findAll().size();

        // Create the OrderPreparation with an existing ID
        orderPreparation.setId(1L);
        OrderPreparationDTO orderPreparationDTO = orderPreparationMapper.toDto(orderPreparation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderPreparationMockMvc.perform(post("/api/order-preparations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPreparationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPreparation in the database
        List<OrderPreparation> orderPreparationList = orderPreparationRepository.findAll();
        assertThat(orderPreparationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderPreparations() throws Exception {
        // Initialize the database
        orderPreparationRepository.saveAndFlush(orderPreparation);

        // Get all the orderPreparationList
        restOrderPreparationMockMvc.perform(get("/api/order-preparations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderPreparation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].dateOrder").value(hasItem(DEFAULT_DATE_ORDER.toString())))
            .andExpect(jsonPath("$.[*].dateDelivery").value(hasItem(DEFAULT_DATE_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].specialInstruction").value(hasItem(DEFAULT_SPECIAL_INSTRUCTION.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderPreparation() throws Exception {
        // Initialize the database
        orderPreparationRepository.saveAndFlush(orderPreparation);

        // Get the orderPreparation
        restOrderPreparationMockMvc.perform(get("/api/order-preparations/{id}", orderPreparation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderPreparation.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.dateOrder").value(DEFAULT_DATE_ORDER.toString()))
            .andExpect(jsonPath("$.dateDelivery").value(DEFAULT_DATE_DELIVERY.toString()))
            .andExpect(jsonPath("$.specialInstruction").value(DEFAULT_SPECIAL_INSTRUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderPreparation() throws Exception {
        // Get the orderPreparation
        restOrderPreparationMockMvc.perform(get("/api/order-preparations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderPreparation() throws Exception {
        // Initialize the database
        orderPreparationRepository.saveAndFlush(orderPreparation);

        int databaseSizeBeforeUpdate = orderPreparationRepository.findAll().size();

        // Update the orderPreparation
        OrderPreparation updatedOrderPreparation = orderPreparationRepository.findById(orderPreparation.getId()).get();
        // Disconnect from session so that the updates on updatedOrderPreparation are not directly saved in db
        em.detach(updatedOrderPreparation);
        updatedOrderPreparation
            .quantity(UPDATED_QUANTITY)
            .dateOrder(UPDATED_DATE_ORDER)
            .dateDelivery(UPDATED_DATE_DELIVERY)
            .specialInstruction(UPDATED_SPECIAL_INSTRUCTION);
        OrderPreparationDTO orderPreparationDTO = orderPreparationMapper.toDto(updatedOrderPreparation);

        restOrderPreparationMockMvc.perform(put("/api/order-preparations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPreparationDTO)))
            .andExpect(status().isOk());

        // Validate the OrderPreparation in the database
        List<OrderPreparation> orderPreparationList = orderPreparationRepository.findAll();
        assertThat(orderPreparationList).hasSize(databaseSizeBeforeUpdate);
        OrderPreparation testOrderPreparation = orderPreparationList.get(orderPreparationList.size() - 1);
        assertThat(testOrderPreparation.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderPreparation.getDateOrder()).isEqualTo(UPDATED_DATE_ORDER);
        assertThat(testOrderPreparation.getDateDelivery()).isEqualTo(UPDATED_DATE_DELIVERY);
        assertThat(testOrderPreparation.getSpecialInstruction()).isEqualTo(UPDATED_SPECIAL_INSTRUCTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderPreparation() throws Exception {
        int databaseSizeBeforeUpdate = orderPreparationRepository.findAll().size();

        // Create the OrderPreparation
        OrderPreparationDTO orderPreparationDTO = orderPreparationMapper.toDto(orderPreparation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderPreparationMockMvc.perform(put("/api/order-preparations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPreparationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPreparation in the database
        List<OrderPreparation> orderPreparationList = orderPreparationRepository.findAll();
        assertThat(orderPreparationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderPreparation() throws Exception {
        // Initialize the database
        orderPreparationRepository.saveAndFlush(orderPreparation);

        int databaseSizeBeforeDelete = orderPreparationRepository.findAll().size();

        // Get the orderPreparation
        restOrderPreparationMockMvc.perform(delete("/api/order-preparations/{id}", orderPreparation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderPreparation> orderPreparationList = orderPreparationRepository.findAll();
        assertThat(orderPreparationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPreparation.class);
        OrderPreparation orderPreparation1 = new OrderPreparation();
        orderPreparation1.setId(1L);
        OrderPreparation orderPreparation2 = new OrderPreparation();
        orderPreparation2.setId(orderPreparation1.getId());
        assertThat(orderPreparation1).isEqualTo(orderPreparation2);
        orderPreparation2.setId(2L);
        assertThat(orderPreparation1).isNotEqualTo(orderPreparation2);
        orderPreparation1.setId(null);
        assertThat(orderPreparation1).isNotEqualTo(orderPreparation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPreparationDTO.class);
        OrderPreparationDTO orderPreparationDTO1 = new OrderPreparationDTO();
        orderPreparationDTO1.setId(1L);
        OrderPreparationDTO orderPreparationDTO2 = new OrderPreparationDTO();
        assertThat(orderPreparationDTO1).isNotEqualTo(orderPreparationDTO2);
        orderPreparationDTO2.setId(orderPreparationDTO1.getId());
        assertThat(orderPreparationDTO1).isEqualTo(orderPreparationDTO2);
        orderPreparationDTO2.setId(2L);
        assertThat(orderPreparationDTO1).isNotEqualTo(orderPreparationDTO2);
        orderPreparationDTO1.setId(null);
        assertThat(orderPreparationDTO1).isNotEqualTo(orderPreparationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderPreparationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderPreparationMapper.fromId(null)).isNull();
    }
}
