package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.CookerService;
import io.github.jhipster.application.domain.Cooker;
import io.github.jhipster.application.repository.CookerRepository;
import io.github.jhipster.application.service.dto.CookerDTO;
import io.github.jhipster.application.service.mapper.CookerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Cooker.
 */
@Service
@Transactional
public class CookerServiceImpl implements CookerService {

    private final Logger log = LoggerFactory.getLogger(CookerServiceImpl.class);

    private final CookerRepository cookerRepository;

    private final CookerMapper cookerMapper;

    public CookerServiceImpl(CookerRepository cookerRepository, CookerMapper cookerMapper) {
        this.cookerRepository = cookerRepository;
        this.cookerMapper = cookerMapper;
    }

    /**
     * Save a cooker.
     *
     * @param cookerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CookerDTO save(CookerDTO cookerDTO) {
        log.debug("Request to save Cooker : {}", cookerDTO);

        Cooker cooker = cookerMapper.toEntity(cookerDTO);
        cooker = cookerRepository.save(cooker);
        return cookerMapper.toDto(cooker);
    }

    /**
     * Get all the cookers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CookerDTO> findAll() {
        log.debug("Request to get all Cookers");
        return cookerRepository.findAll().stream()
            .map(cookerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cooker by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CookerDTO> findOne(Long id) {
        log.debug("Request to get Cooker : {}", id);
        return cookerRepository.findById(id)
            .map(cookerMapper::toDto);
    }

    /**
     * Delete the cooker by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cooker : {}", id);
        cookerRepository.deleteById(id);
    }
}
