package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.OrderPreparationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing OrderPreparation.
 */
public interface OrderPreparationService {

    /**
     * Save a orderPreparation.
     *
     * @param orderPreparationDTO the entity to save
     * @return the persisted entity
     */
    OrderPreparationDTO save(OrderPreparationDTO orderPreparationDTO);

    /**
     * Get all the orderPreparations.
     *
     * @return the list of entities
     */
    List<OrderPreparationDTO> findAll();


    /**
     * Get the "id" orderPreparation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrderPreparationDTO> findOne(Long id);

    /**
     * Delete the "id" orderPreparation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
