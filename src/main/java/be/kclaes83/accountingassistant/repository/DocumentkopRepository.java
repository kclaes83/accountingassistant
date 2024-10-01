package be.kclaes83.accountingassistant.repository;

import be.kclaes83.accountingassistant.domain.Documentkop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Documentkop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentkopRepository extends JpaRepository<Documentkop, Long> {}
