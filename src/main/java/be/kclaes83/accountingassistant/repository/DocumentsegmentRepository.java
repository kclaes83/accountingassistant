package be.kclaes83.accountingassistant.repository;

import be.kclaes83.accountingassistant.domain.Documentsegment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Documentsegment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentsegmentRepository extends JpaRepository<Documentsegment, Long> {}
