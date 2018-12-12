package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.KitchenService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.KitchenDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Kitchen.
 */
@RestController
@RequestMapping("/api")
public class KitchenResource {

    private final Logger log = LoggerFactory.getLogger(KitchenResource.class);

    private static final String ENTITY_NAME = "kitchen";

    private final KitchenService kitchenService;

    public KitchenResource(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    /**
     * POST  /kitchens : Create a new kitchen.
     *
     * @param kitchenDTO the kitchenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kitchenDTO, or with status 400 (Bad Request) if the kitchen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kitchens")
    @Timed
    public ResponseEntity<KitchenDTO> createKitchen(@RequestBody KitchenDTO kitchenDTO) throws URISyntaxException {
        log.debug("REST request to save Kitchen : {}", kitchenDTO);
        if (kitchenDTO.getId() != null) {
            throw new BadRequestAlertException("A new kitchen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KitchenDTO result = kitchenService.save(kitchenDTO);
        return ResponseEntity.created(new URI("/api/kitchens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kitchens : Updates an existing kitchen.
     *
     * @param kitchenDTO the kitchenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kitchenDTO,
     * or with status 400 (Bad Request) if the kitchenDTO is not valid,
     * or with status 500 (Internal Server Error) if the kitchenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kitchens")
    @Timed
    public ResponseEntity<KitchenDTO> updateKitchen(@RequestBody KitchenDTO kitchenDTO) throws URISyntaxException {
        log.debug("REST request to update Kitchen : {}", kitchenDTO);
        if (kitchenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KitchenDTO result = kitchenService.save(kitchenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kitchenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kitchens : get all the kitchens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of kitchens in body
     */
    @GetMapping("/kitchens")
    @Timed
    public ResponseEntity<List<KitchenDTO>> getAllKitchens(Pageable pageable) {
        log.debug("REST request to get a page of Kitchens");
        Page<KitchenDTO> page = kitchenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kitchens");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /kitchens/:id : get the "id" kitchen.
     *
     * @param id the id of the kitchenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kitchenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/kitchens/{id}")
    @Timed
    public ResponseEntity<KitchenDTO> getKitchen(@PathVariable Long id) {
        log.debug("REST request to get Kitchen : {}", id);
        Optional<KitchenDTO> kitchenDTO = kitchenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kitchenDTO);
    }

    /**
     * DELETE  /kitchens/:id : delete the "id" kitchen.
     *
     * @param id the id of the kitchenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kitchens/{id}")
    @Timed
    public ResponseEntity<Void> deleteKitchen(@PathVariable Long id) {
        log.debug("REST request to delete Kitchen : {}", id);
        kitchenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
