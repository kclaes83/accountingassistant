package be.kclaes83.accountingassistant.service;

import be.kclaes83.accountingassistant.domain.Documentkop;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link be.kclaes83.accountingassistant.domain.Documentkop}.
 */
public interface DocumentkopService {
    /**
     * Save a documentkop.
     *
     * @param documentkop the entity to save.
     * @return the persisted entity.
     */
    Documentkop save(Documentkop documentkop);

    /**
     * Updates a documentkop.
     *
     * @param documentkop the entity to update.
     * @return the persisted entity.
     */
    Documentkop update(Documentkop documentkop);

    /**
     * Partially updates a documentkop.
     *
     * @param documentkop the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Documentkop> partialUpdate(Documentkop documentkop);

    /**
     * Get all the documentkops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Documentkop> findAll(Pageable pageable);

    /**
     * Get the "id" documentkop.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Documentkop> findOne(Long id);

    /**
     * Delete the "id" documentkop.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
