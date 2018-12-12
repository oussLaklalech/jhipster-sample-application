package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.CookerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cooker and its DTO CookerDTO.
 */
@Mapper(componentModel = "spring", uses = {KitchenMapper.class})
public interface CookerMapper extends EntityMapper<CookerDTO, Cooker> {

    @Mapping(source = "cooker.id", target = "cookerId")
    CookerDTO toDto(Cooker cooker);

    @Mapping(source = "cookerId", target = "cooker")
    Cooker toEntity(CookerDTO cookerDTO);

    default Cooker fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cooker cooker = new Cooker();
        cooker.setId(id);
        return cooker;
    }
}
