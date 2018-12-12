package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.StateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity State and its DTO StateDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderPreparationMapper.class})
public interface StateMapper extends EntityMapper<StateDTO, State> {

    @Mapping(source = "orderPreparation.id", target = "orderPreparationId")
    StateDTO toDto(State state);

    @Mapping(source = "orderPreparationId", target = "orderPreparation")
    State toEntity(StateDTO stateDTO);

    default State fromId(Long id) {
        if (id == null) {
            return null;
        }
        State state = new State();
        state.setId(id);
        return state;
    }
}
