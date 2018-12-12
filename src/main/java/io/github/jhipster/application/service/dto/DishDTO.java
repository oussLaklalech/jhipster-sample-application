package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Dish entity.
 */
public class DishDTO implements Serializable {

    private Long id;

    private Double price;

    private String name;

    private String description;

    private Integer note;

    private Long kitchenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Long getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Long kitchenId) {
        this.kitchenId = kitchenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DishDTO dishDTO = (DishDTO) o;
        if (dishDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dishDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DishDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", note=" + getNote() +
            ", kitchen=" + getKitchenId() +
            "}";
    }
}
