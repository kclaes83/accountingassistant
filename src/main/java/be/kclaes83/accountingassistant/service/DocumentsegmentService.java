package be.kclaes83.accountingassistant.service;

import be.kclaes83.accountingassistant.domain.Documentsegment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link be.kclaes83.accountingassistant.domain.Documentsegment}.
 */
public interface DocumentsegmentService {
    /**
     * Save a documentsegment.
     *
     * @param documentsegment the entity to save.
     * @return the persisted entity.
     */
    Documentsegment save(Documentsegment documentsegment);

    /**
     * Updates a documentsegment.
     *
     * @param documentsegment the entity to update.
     * @return the persisted entity.
     */
    Documentsegment update(Documentsegment documentsegment);

    /**
     * Partially updates a documentsegment.
     *
     * @param documentsegment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Documentsegment> partialUpdate(Documentsegment documentsegment);

    /**
     * Get all the documentsegments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Documentsegment> findAll(Pageable pageable);

    /**
     * Get the "id" documentsegment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Documentsegment> findOne(Long id);

    /**
     * Delete the "id" documentsegment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
