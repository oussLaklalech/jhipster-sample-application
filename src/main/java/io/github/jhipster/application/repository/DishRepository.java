package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Dish;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dish entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

}
