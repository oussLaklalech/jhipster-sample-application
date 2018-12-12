package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.OrderPreparationService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.OrderPreparationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderPreparation.
 */
@RestController
@RequestMapping("/api")
public class OrderPreparationResource {

    private final Logger log = LoggerFactory.getLogger(OrderPreparationResource.class);

    private static final String ENTITY_NAME = "orderPreparation";

    private final OrderPreparationService orderPreparationService;

    public OrderPreparationResource(OrderPreparationService orderPreparationService) {
        this.orderPreparationService = orderPreparationService;
    }

    /**
     * POST  /order-preparations : Create a new orderPreparation.
     *
     * @param orderPreparationDTO the orderPreparationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderPreparationDTO, or with status 400 (Bad Request) if the orderPreparation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-preparations")
    @Timed
    public ResponseEntity<OrderPreparationDTO> createOrderPreparation(@RequestBody OrderPreparationDTO orderPreparationDTO) throws URISyntaxException {
        log.debug("REST request to save OrderPreparation : {}", orderPreparationDTO);
        if (orderPreparationDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderPreparation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderPreparationDTO result = orderPreparationService.save(orderPreparationDTO);
        return ResponseEntity.created(new URI("/api/order-preparations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-preparations : Updates an existing orderPreparation.
     *
     * @param orderPreparationDTO the orderPreparationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderPreparationDTO,
     * or with status 400 (Bad Request) if the orderPreparationDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderPreparationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-preparations")
    @Timed
    public ResponseEntity<OrderPreparationDTO> updateOrderPreparation(@RequestBody OrderPreparationDTO orderPreparationDTO) throws URISyntaxException {
        log.debug("REST request to update OrderPreparation : {}", orderPreparationDTO);
        if (orderPreparationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderPreparationDTO result = orderPreparationService.save(orderPreparationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderPreparationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-preparations : get all the orderPreparations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderPreparations in body
     */
    @GetMapping("/order-preparations")
    @Timed
    public List<OrderPreparationDTO> getAllOrderPreparations() {
        log.debug("REST request to get all OrderPreparations");
        return orderPreparationService.findAll();
    }

    /**
     * GET  /order-preparations/:id : get the "id" orderPreparation.
     *
     * @param id the id of the orderPreparationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderPreparationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-preparations/{id}")
    @Timed
    public ResponseEntity<OrderPreparationDTO> getOrderPreparation(@PathVariable Long id) {
        log.debug("REST request to get OrderPreparation : {}", id);
        Optional<OrderPreparationDTO> orderPreparationDTO = orderPreparationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderPreparationDTO);
    }

    /**
     * DELETE  /order-preparations/:id : delete the "id" orderPreparation.
     *
     * @param id the id of the orderPreparationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-preparations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderPreparation(@PathVariable Long id) {
        log.debug("REST request to delete OrderPreparation : {}", id);
        orderPreparationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
