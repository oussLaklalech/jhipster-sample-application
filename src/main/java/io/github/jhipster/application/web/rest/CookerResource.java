package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.CookerService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.CookerDTO;
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
 * REST controller for managing Cooker.
 */
@RestController
@RequestMapping("/api")
public class CookerResource {

    private final Logger log = LoggerFactory.getLogger(CookerResource.class);

    private static final String ENTITY_NAME = "cooker";

    private final CookerService cookerService;

    public CookerResource(CookerService cookerService) {
        this.cookerService = cookerService;
    }

    /**
     * POST  /cookers : Create a new cooker.
     *
     * @param cookerDTO the cookerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cookerDTO, or with status 400 (Bad Request) if the cooker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cookers")
    @Timed
    public ResponseEntity<CookerDTO> createCooker(@RequestBody CookerDTO cookerDTO) throws URISyntaxException {
        log.debug("REST request to save Cooker : {}", cookerDTO);
        if (cookerDTO.getId() != null) {
            throw new BadRequestAlertException("A new cooker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CookerDTO result = cookerService.save(cookerDTO);
        return ResponseEntity.created(new URI("/api/cookers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cookers : Updates an existing cooker.
     *
     * @param cookerDTO the cookerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cookerDTO,
     * or with status 400 (Bad Request) if the cookerDTO is not valid,
     * or with status 500 (Internal Server Error) if the cookerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cookers")
    @Timed
    public ResponseEntity<CookerDTO> updateCooker(@RequestBody CookerDTO cookerDTO) throws URISyntaxException {
        log.debug("REST request to update Cooker : {}", cookerDTO);
        if (cookerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CookerDTO result = cookerService.save(cookerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cookerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cookers : get all the cookers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cookers in body
     */
    @GetMapping("/cookers")
    @Timed
    public List<CookerDTO> getAllCookers() {
        log.debug("REST request to get all Cookers");
        return cookerService.findAll();
    }

    /**
     * GET  /cookers/:id : get the "id" cooker.
     *
     * @param id the id of the cookerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cookerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cookers/{id}")
    @Timed
    public ResponseEntity<CookerDTO> getCooker(@PathVariable Long id) {
        log.debug("REST request to get Cooker : {}", id);
        Optional<CookerDTO> cookerDTO = cookerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cookerDTO);
    }

    /**
     * DELETE  /cookers/:id : delete the "id" cooker.
     *
     * @param id the id of the cookerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cookers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCooker(@PathVariable Long id) {
        log.debug("REST request to delete Cooker : {}", id);
        cookerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
