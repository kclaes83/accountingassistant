package be.kclaes83.accountingassistant.web.rest;

import be.kclaes83.accountingassistant.domain.Documentsegment;
import be.kclaes83.accountingassistant.repository.DocumentsegmentRepository;
import be.kclaes83.accountingassistant.service.DocumentsegmentService;
import be.kclaes83.accountingassistant.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link be.kclaes83.accountingassistant.domain.Documentsegment}.
 */
@RestController
@RequestMapping("/api/documentsegments")
public class DocumentsegmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentsegmentResource.class);

    private static final String ENTITY_NAME = "documentsegment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentsegmentService documentsegmentService;

    private final DocumentsegmentRepository documentsegmentRepository;

    public DocumentsegmentResource(DocumentsegmentService documentsegmentService, DocumentsegmentRepository documentsegmentRepository) {
        this.documentsegmentService = documentsegmentService;
        this.documentsegmentRepository = documentsegmentRepository;
    }

    /**
     * {@code POST  /documentsegments} : Create a new documentsegment.
     *
     * @param documentsegment the documentsegment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentsegment, or with status {@code 400 (Bad Request)} if the documentsegment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Documentsegment> createDocumentsegment(@Valid @RequestBody Documentsegment documentsegment)
        throws URISyntaxException {
        LOG.debug("REST request to save Documentsegment : {}", documentsegment);
        if (documentsegment.getId() != null) {
            throw new BadRequestAlertException("A new documentsegment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        documentsegment = documentsegmentService.save(documentsegment);
        return ResponseEntity.created(new URI("/api/documentsegments/" + documentsegment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, documentsegment.getId().toString()))
            .body(documentsegment);
    }

    /**
     * {@code PUT  /documentsegments/:id} : Updates an existing documentsegment.
     *
     * @param id the id of the documentsegment to save.
     * @param documentsegment the documentsegment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentsegment,
     * or with status {@code 400 (Bad Request)} if the documentsegment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentsegment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Documentsegment> updateDocumentsegment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Documentsegment documentsegment
    ) throws URISyntaxException {
        LOG.debug("REST request to update Documentsegment : {}, {}", id, documentsegment);
        if (documentsegment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentsegment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentsegmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentsegment = documentsegmentService.update(documentsegment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentsegment.getId().toString()))
            .body(documentsegment);
    }

    /**
     * {@code PATCH  /documentsegments/:id} : Partial updates given fields of an existing documentsegment, field will ignore if it is null
     *
     * @param id the id of the documentsegment to save.
     * @param documentsegment the documentsegment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentsegment,
     * or with status {@code 400 (Bad Request)} if the documentsegment is not valid,
     * or with status {@code 404 (Not Found)} if the documentsegment is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentsegment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Documentsegment> partialUpdateDocumentsegment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Documentsegment documentsegment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Documentsegment partially : {}, {}", id, documentsegment);
        if (documentsegment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentsegment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentsegmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Documentsegment> result = documentsegmentService.partialUpdate(documentsegment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentsegment.getId().toString())
        );
    }

    /**
     * {@code GET  /documentsegments} : get all the documentsegments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentsegments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Documentsegment>> getAllDocumentsegments(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Documentsegments");
        Page<Documentsegment> page = documentsegmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documentsegments/:id} : get the "id" documentsegment.
     *
     * @param id the id of the documentsegment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentsegment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Documentsegment> getDocumentsegment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Documentsegment : {}", id);
        Optional<Documentsegment> documentsegment = documentsegmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentsegment);
    }

    /**
     * {@code DELETE  /documentsegments/:id} : delete the "id" documentsegment.
     *
     * @param id the id of the documentsegment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentsegment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Documentsegment : {}", id);
        documentsegmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
