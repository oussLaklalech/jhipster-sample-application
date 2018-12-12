package io.github.jhipster.application.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OrderPreparation entity.
 */
public class OrderPreparationDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Instant dateOrder;

    private Instant dateDelivery;

    private String specialInstruction;

    private Long consumerId;

    private Long dishId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Instant dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Instant getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Instant dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderPreparationDTO orderPreparationDTO = (OrderPreparationDTO) o;
        if (orderPreparationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderPreparationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderPreparationDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", dateOrder='" + getDateOrder() + "'" +
            ", dateDelivery='" + getDateDelivery() + "'" +
            ", specialInstruction='" + getSpecialInstruction() + "'" +
            ", consumer=" + getConsumerId() +
            ", dish=" + getDishId() +
            "}";
    }
}
