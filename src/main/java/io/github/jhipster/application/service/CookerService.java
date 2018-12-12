package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.CookerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Cooker.
 */
public interface CookerService {

    /**
     * Save a cooker.
     *
     * @param cookerDTO the entity to save
     * @return the persisted entity
     */
    CookerDTO save(CookerDTO cookerDTO);

    /**
     * Get all the cookers.
     *
     * @return the list of entities
     */
    List<CookerDTO> findAll();


    /**
     * Get the "id" cooker.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CookerDTO> findOne(Long id);

    /**
     * Delete the "id" cooker.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
