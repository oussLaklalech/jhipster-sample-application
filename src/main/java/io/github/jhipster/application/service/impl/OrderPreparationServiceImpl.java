package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.OrderPreparationService;
import io.github.jhipster.application.domain.OrderPreparation;
import io.github.jhipster.application.repository.OrderPreparationRepository;
import io.github.jhipster.application.service.dto.OrderPreparationDTO;
import io.github.jhipster.application.service.mapper.OrderPreparationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OrderPreparation.
 */
@Service
@Transactional
public class OrderPreparationServiceImpl implements OrderPreparationService {

    private final Logger log = LoggerFactory.getLogger(OrderPreparationServiceImpl.class);

    private final OrderPreparationRepository orderPreparationRepository;

    private final OrderPreparationMapper orderPreparationMapper;

    public OrderPreparationServiceImpl(OrderPreparationRepository orderPreparationRepository, OrderPreparationMapper orderPreparationMapper) {
        this.orderPreparationRepository = orderPreparationRepository;
        this.orderPreparationMapper = orderPreparationMapper;
    }

    /**
     * Save a orderPreparation.
     *
     * @param orderPreparationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderPreparationDTO save(OrderPreparationDTO orderPreparationDTO) {
        log.debug("Request to save OrderPreparation : {}", orderPreparationDTO);

        OrderPreparation orderPreparation = orderPreparationMapper.toEntity(orderPreparationDTO);
        orderPreparation = orderPreparationRepository.save(orderPreparation);
        return orderPreparationMapper.toDto(orderPreparation);
    }

    /**
     * Get all the orderPreparations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderPreparationDTO> findAll() {
        log.debug("Request to get all OrderPreparations");
        return orderPreparationRepository.findAll().stream()
            .map(orderPreparationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderPreparation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderPreparationDTO> findOne(Long id) {
        log.debug("Request to get OrderPreparation : {}", id);
        return orderPreparationRepository.findById(id)
            .map(orderPreparationMapper::toDto);
    }

    /**
     * Delete the orderPreparation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderPreparation : {}", id);
        orderPreparationRepository.deleteById(id);
    }
}
