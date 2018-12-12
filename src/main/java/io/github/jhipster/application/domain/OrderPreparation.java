package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Order entity.
 */
@ApiModel(description = "Order entity.")
@Entity
@Table(name = "order_preparation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderPreparation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "date_order")
    private Instant dateOrder;

    @Column(name = "date_delivery")
    private Instant dateDelivery;

    @Column(name = "special_instruction")
    private String specialInstruction;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Consumer consumer;

    @OneToMany(mappedBy = "orderPreparation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<State> states = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Dish dish;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderPreparation quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getDateOrder() {
        return dateOrder;
    }

    public OrderPreparation dateOrder(Instant dateOrder) {
        this.dateOrder = dateOrder;
        return this;
    }

    public void setDateOrder(Instant dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Instant getDateDelivery() {
        return dateDelivery;
    }

    public OrderPreparation dateDelivery(Instant dateDelivery) {
        this.dateDelivery = dateDelivery;
        return this;
    }

    public void setDateDelivery(Instant dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public OrderPreparation specialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
        return this;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public OrderPreparation consumer(Consumer consumer) {
        this.consumer = consumer;
        return this;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Set<State> getStates() {
        return states;
    }

    public OrderPreparation states(Set<State> states) {
        this.states = states;
        return this;
    }

    public OrderPreparation addState(State state) {
        this.states.add(state);
        state.setOrderPreparation(this);
        return this;
    }

    public OrderPreparation removeState(State state) {
        this.states.remove(state);
        state.setOrderPreparation(null);
        return this;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Dish getDish() {
        return dish;
    }

    public OrderPreparation dish(Dish dish) {
        this.dish = dish;
        return this;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderPreparation orderPreparation = (OrderPreparation) o;
        if (orderPreparation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderPreparation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderPreparation{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", dateOrder='" + getDateOrder() + "'" +
            ", dateDelivery='" + getDateDelivery() + "'" +
            ", specialInstruction='" + getSpecialInstruction() + "'" +
            "}";
    }
}
