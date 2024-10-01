package be.kclaes83.accountingassistant.web.rest;

import be.kclaes83.accountingassistant.domain.Documentkop;
import be.kclaes83.accountingassistant.repository.DocumentkopRepository;
import be.kclaes83.accountingassistant.service.DocumentkopService;
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
 * REST controller for managing {@link be.kclaes83.accountingassistant.domain.Documentkop}.
 */
@RestController
@RequestMapping("/api/documentkops")
public class DocumentkopResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentkopResource.class);

    private static final String ENTITY_NAME = "documentkop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentkopService documentkopService;

    private final DocumentkopRepository documentkopRepository;

    public DocumentkopResource(DocumentkopService documentkopService, DocumentkopRepository documentkopRepository) {
        this.documentkopService = documentkopService;
        this.documentkopRepository = documentkopRepository;
    }

    /**
     * {@code POST  /documentkops} : Create a new documentkop.
     *
     * @param documentkop the documentkop to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentkop, or with status {@code 400 (Bad Request)} if the documentkop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Documentkop> createDocumentkop(@Valid @RequestBody Documentkop documentkop) throws URISyntaxException {
        LOG.debug("REST request to save Documentkop : {}", documentkop);
        if (documentkop.getId() != null) {
            throw new BadRequestAlertException("A new documentkop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        documentkop = documentkopService.save(documentkop);
        return ResponseEntity.created(new URI("/api/documentkops/" + documentkop.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, documentkop.getId().toString()))
            .body(documentkop);
    }

    /**
     * {@code PUT  /documentkops/:id} : Updates an existing documentkop.
     *
     * @param id the id of the documentkop to save.
     * @param documentkop the documentkop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentkop,
     * or with status {@code 400 (Bad Request)} if the documentkop is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentkop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Documentkop> updateDocumentkop(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Documentkop documentkop
    ) throws URISyntaxException {
        LOG.debug("REST request to update Documentkop : {}, {}", id, documentkop);
        if (documentkop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentkop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentkopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentkop = documentkopService.update(documentkop);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentkop.getId().toString()))
            .body(documentkop);
    }

    /**
     * {@code PATCH  /documentkops/:id} : Partial updates given fields of an existing documentkop, field will ignore if it is null
     *
     * @param id the id of the documentkop to save.
     * @param documentkop the documentkop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentkop,
     * or with status {@code 400 (Bad Request)} if the documentkop is not valid,
     * or with status {@code 404 (Not Found)} if the documentkop is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentkop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Documentkop> partialUpdateDocumentkop(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Documentkop documentkop
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Documentkop partially : {}, {}", id, documentkop);
        if (documentkop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentkop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentkopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Documentkop> result = documentkopService.partialUpdate(documentkop);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentkop.getId().toString())
        );
    }

    /**
     * {@code GET  /documentkops} : get all the documentkops.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentkops in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Documentkop>> getAllDocumentkops(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Documentkops");
        Page<Documentkop> page = documentkopService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documentkops/:id} : get the "id" documentkop.
     *
     * @param id the id of the documentkop to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentkop, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Documentkop> getDocumentkop(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Documentkop : {}", id);
        Optional<Documentkop> documentkop = documentkopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentkop);
    }

    /**
     * {@code DELETE  /documentkops/:id} : delete the "id" documentkop.
     *
     * @param id the id of the documentkop to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentkop(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Documentkop : {}", id);
        documentkopService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
