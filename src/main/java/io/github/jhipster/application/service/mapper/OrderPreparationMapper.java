package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.OrderPreparationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderPreparation and its DTO OrderPreparationDTO.
 */
@Mapper(componentModel = "spring", uses = {ConsumerMapper.class, DishMapper.class})
public interface OrderPreparationMapper extends EntityMapper<OrderPreparationDTO, OrderPreparation> {

    @Mapping(source = "consumer.id", target = "consumerId")
    @Mapping(source = "dish.id", target = "dishId")
    OrderPreparationDTO toDto(OrderPreparation orderPreparation);

    @Mapping(source = "consumerId", target = "consumer")
    @Mapping(target = "states", ignore = true)
    @Mapping(source = "dishId", target = "dish")
    OrderPreparation toEntity(OrderPreparationDTO orderPreparationDTO);

    default OrderPreparation fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderPreparation orderPreparation = new OrderPreparation();
        orderPreparation.setId(id);
        return orderPreparation;
    }
}
