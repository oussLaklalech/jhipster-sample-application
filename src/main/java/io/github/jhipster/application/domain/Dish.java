package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Dish.
 */
@Entity
@Table(name = "dish")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "note")
    private Integer note;

    @ManyToOne
    @JsonIgnoreProperties("dishes")
    private Kitchen kitchen;

    @OneToMany(mappedBy = "dish")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderPreparation> orders = new HashSet<>();
    @OneToMany(mappedBy = "dish")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public Dish price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Dish name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Dish description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNote() {
        return note;
    }

    public Dish note(Integer note) {
        this.note = note;
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public Dish kitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
        return this;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Set<OrderPreparation> getOrders() {
        return orders;
    }

    public Dish orders(Set<OrderPreparation> orderPreparations) {
        this.orders = orderPreparations;
        return this;
    }

    public Dish addOrder(OrderPreparation orderPreparation) {
        this.orders.add(orderPreparation);
        orderPreparation.setDish(this);
        return this;
    }

    public Dish removeOrder(OrderPreparation orderPreparation) {
        this.orders.remove(orderPreparation);
        orderPreparation.setDish(null);
        return this;
    }

    public void setOrders(Set<OrderPreparation> orderPreparations) {
        this.orders = orderPreparations;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Dish comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Dish addComment(Comment comment) {
        this.comments.add(comment);
        comment.setDish(this);
        return this;
    }

    public Dish removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setDish(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
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
        Dish dish = (Dish) o;
        if (dish.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dish.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dish{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", note=" + getNote() +
            "}";
    }
}
