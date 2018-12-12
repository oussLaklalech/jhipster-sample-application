package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ConsumerService;
import io.github.jhipster.application.domain.Consumer;
import io.github.jhipster.application.repository.ConsumerRepository;
import io.github.jhipster.application.service.dto.ConsumerDTO;
import io.github.jhipster.application.service.mapper.ConsumerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Consumer.
 */
@Service
@Transactional
public class ConsumerServiceImpl implements ConsumerService {

    private final Logger log = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    private final ConsumerRepository consumerRepository;

    private final ConsumerMapper consumerMapper;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository, ConsumerMapper consumerMapper) {
        this.consumerRepository = consumerRepository;
        this.consumerMapper = consumerMapper;
    }

    /**
     * Save a consumer.
     *
     * @param consumerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsumerDTO save(ConsumerDTO consumerDTO) {
        log.debug("Request to save Consumer : {}", consumerDTO);

        Consumer consumer = consumerMapper.toEntity(consumerDTO);
        consumer = consumerRepository.save(consumer);
        return consumerMapper.toDto(consumer);
    }

    /**
     * Get all the consumers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsumerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consumers");
        return consumerRepository.findAll(pageable)
            .map(consumerMapper::toDto);
    }


    /**
     * Get one consumer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsumerDTO> findOne(Long id) {
        log.debug("Request to get Consumer : {}", id);
        return consumerRepository.findById(id)
            .map(consumerMapper::toDto);
    }

    /**
     * Delete the consumer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consumer : {}", id);
        consumerRepository.deleteById(id);
    }
}
