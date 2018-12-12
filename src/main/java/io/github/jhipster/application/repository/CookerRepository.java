package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Cooker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cooker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CookerRepository extends JpaRepository<Cooker, Long> {

}
