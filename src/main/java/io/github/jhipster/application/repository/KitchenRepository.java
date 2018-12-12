package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Kitchen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Kitchen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

}
