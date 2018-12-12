package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.KitchenService;
import io.github.jhipster.application.domain.Kitchen;
import io.github.jhipster.application.repository.KitchenRepository;
import io.github.jhipster.application.service.dto.KitchenDTO;
import io.github.jhipster.application.service.mapper.KitchenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Kitchen.
 */
@Service
@Transactional
public class KitchenServiceImpl implements KitchenService {

    private final Logger log = LoggerFactory.getLogger(KitchenServiceImpl.class);

    private final KitchenRepository kitchenRepository;

    private final KitchenMapper kitchenMapper;

    public KitchenServiceImpl(KitchenRepository kitchenRepository, KitchenMapper kitchenMapper) {
        this.kitchenRepository = kitchenRepository;
        this.kitchenMapper = kitchenMapper;
    }

    /**
     * Save a kitchen.
     *
     * @param kitchenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KitchenDTO save(KitchenDTO kitchenDTO) {
        log.debug("Request to save Kitchen : {}", kitchenDTO);

        Kitchen kitchen = kitchenMapper.toEntity(kitchenDTO);
        kitchen = kitchenRepository.save(kitchen);
        return kitchenMapper.toDto(kitchen);
    }

    /**
     * Get all the kitchens.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KitchenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Kitchens");
        return kitchenRepository.findAll(pageable)
            .map(kitchenMapper::toDto);
    }


    /**
     * Get one kitchen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KitchenDTO> findOne(Long id) {
        log.debug("Request to get Kitchen : {}", id);
        return kitchenRepository.findById(id)
            .map(kitchenMapper::toDto);
    }

    /**
     * Delete the kitchen by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kitchen : {}", id);
        kitchenRepository.deleteById(id);
    }
}
