package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ConsumerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Consumer and its DTO ConsumerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConsumerMapper extends EntityMapper<ConsumerDTO, Consumer> {


    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Consumer toEntity(ConsumerDTO consumerDTO);

    default Consumer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consumer consumer = new Consumer();
        consumer.setId(id);
        return consumer;
    }
}
