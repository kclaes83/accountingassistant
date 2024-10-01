package be.kclaes83.accountingassistant.web.rest;

import static be.kclaes83.accountingassistant.domain.DocumentkopAsserts.*;
import static be.kclaes83.accountingassistant.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import be.kclaes83.accountingassistant.IntegrationTest;
import be.kclaes83.accountingassistant.domain.Documentkop;
import be.kclaes83.accountingassistant.domain.enumeration.DocumentSoort;
import be.kclaes83.accountingassistant.domain.enumeration.Maand;
import be.kclaes83.accountingassistant.repository.DocumentkopRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DocumentkopResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentkopResourceIT {

    private static final String DEFAULT_BEDRIJFSNUMMER = "AAAA";
    private static final String UPDATED_BEDRIJFSNUMMER = "BBBB";

    private static final String DEFAULT_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_BOEKJAAR = "AAAA";
    private static final String UPDATED_BOEKJAAR = "BBBB";

    private static final DocumentSoort DEFAULT_DOCUMENTSOORT = DocumentSoort.INKOMENDE_FACTUUR;
    private static final DocumentSoort UPDATED_DOCUMENTSOORT = DocumentSoort.UITGAANDE_FACTUUR;

    private static final Instant DEFAULT_DOCUMENTDATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOCUMENTDATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BOEKINGSDATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOEKINGSDATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Maand DEFAULT_BOEKMAAND = Maand.JA;
    private static final Maand UPDATED_BOEKMAAND = Maand.FE;

    private static final Instant DEFAULT_INVOERDAG = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOERDAG = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INVOERTIJD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOERTIJD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/documentkops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentkopRepository documentkopRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentkopMockMvc;

    private Documentkop documentkop;

    private Documentkop insertedDocumentkop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documentkop createEntity() {
        return new Documentkop()
            .bedrijfsnummer(DEFAULT_BEDRIJFSNUMMER)
            .documentNrBoekhoudingsdocument(DEFAULT_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(DEFAULT_BOEKJAAR)
            .documentsoort(DEFAULT_DOCUMENTSOORT)
            .documentdatum(DEFAULT_DOCUMENTDATUM)
            .boekingsdatum(DEFAULT_BOEKINGSDATUM)
            .boekmaand(DEFAULT_BOEKMAAND)
            .invoerdag(DEFAULT_INVOERDAG)
            .invoertijd(DEFAULT_INVOERTIJD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documentkop createUpdatedEntity() {
        return new Documentkop()
            .bedrijfsnummer(UPDATED_BEDRIJFSNUMMER)
            .documentNrBoekhoudingsdocument(UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(UPDATED_BOEKJAAR)
            .documentsoort(UPDATED_DOCUMENTSOORT)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .boekingsdatum(UPDATED_BOEKINGSDATUM)
            .boekmaand(UPDATED_BOEKMAAND)
            .invoerdag(UPDATED_INVOERDAG)
            .invoertijd(UPDATED_INVOERTIJD);
    }

    @BeforeEach
    public void initTest() {
        documentkop = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDocumentkop != null) {
            documentkopRepository.delete(insertedDocumentkop);
            insertedDocumentkop = null;
        }
    }

    @Test
    @Transactional
    void createDocumentkop() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Documentkop
        var returnedDocumentkop = om.readValue(
            restDocumentkopMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentkop))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Documentkop.class
        );

        // Validate the Documentkop in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDocumentkopUpdatableFieldsEquals(returnedDocumentkop, getPersistedDocumentkop(returnedDocumentkop));

        insertedDocumentkop = returnedDocumentkop;
    }

    @Test
    @Transactional
    void createDocumentkopWithExistingId() throws Exception {
        // Create the Documentkop with an existing ID
        documentkop.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentkopMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentkop)))
            .andExpect(status().isBadRequest());

        // Validate the Documentkop in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentkops() throws Exception {
        // Initialize the database
        insertedDocumentkop = documentkopRepository.saveAndFlush(documentkop);

        // Get all the documentkopList
        restDocumentkopMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentkop.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrijfsnummer").value(hasItem(DEFAULT_BEDRIJFSNUMMER)))
            .andExpect(jsonPath("$.[*].documentNrBoekhoudingsdocument").value(hasItem(DEFAULT_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)))
            .andExpect(jsonPath("$.[*].boekjaar").value(hasItem(DEFAULT_BOEKJAAR)))
            .andExpect(jsonPath("$.[*].documentsoort").value(hasItem(DEFAULT_DOCUMENTSOORT.toString())))
            .andExpect(jsonPath("$.[*].documentdatum").value(hasItem(DEFAULT_DOCUMENTDATUM.toString())))
            .andExpect(jsonPath("$.[*].boekingsdatum").value(hasItem(DEFAULT_BOEKINGSDATUM.toString())))
            .andExpect(jsonPath("$.[*].boekmaand").value(hasItem(DEFAULT_BOEKMAAND.toString())))
            .andExpect(jsonPath("$.[*].invoerdag").value(hasItem(DEFAULT_INVOERDAG.toString())))
            .andExpect(jsonPath("$.[*].invoertijd").value(hasItem(DEFAULT_INVOERTIJD.toString())));
    }

    @Test
    @Transactional
    void getDocumentkop() throws Exception {
        // Initialize the database
        insertedDocumentkop = documentkopRepository.saveAndFlush(documentkop);

        // Get the documentkop
        restDocumentkopMockMvc
            .perform(get(ENTITY_API_URL_ID, documentkop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentkop.getId().intValue()))
            .andExpect(jsonPath("$.bedrijfsnummer").value(DEFAULT_BEDRIJFSNUMMER))
            .andExpect(jsonPath("$.documentNrBoekhoudingsdocument").value(DEFAULT_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT))
            .andExpect(jsonPath("$.boekjaar").value(DEFAULT_BOEKJAAR))
            .andExpect(jsonPath("$.documentsoort").value(DEFAULT_DOCUMENTSOORT.toString()))
            .andExpect(jsonPath("$.documentdatum").value(DEFAULT_DOCUMENTDATUM.toString()))
            .andExpect(jsonPath("$.boekingsdatum").value(DEFAULT_BOEKINGSDATUM.toString()))
            .andExpect(jsonPath("$.boekmaand").value(DEFAULT_BOEKMAAND.toString()))
            .andExpect(jsonPath("$.invoerdag").value(DEFAULT_INVOERDAG.toString()))
            .andExpect(jsonPath("$.invoertijd").value(DEFAULT_INVOERTIJD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDocumentkop() throws Exception {
        // Get the documentkop
        restDocumentkopMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentkop() throws Exception {
        // Initialize the database
        insertedDocumentkop = documentkopRepository.saveAndFlush(documentkop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentkop
        Documentkop updatedDocumentkop = documentkopRepository.findById(documentkop.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentkop are not directly saved in db
        em.detach(updatedDocumentkop);
        updatedDocumentkop
            .bedrijfsnummer(UPDATED_BEDRIJFSNUMMER)
            .documentNrBoekhoudingsdocument(UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(UPDATED_BOEKJAAR)
            .documentsoort(UPDATED_DOCUMENTSOORT)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .boekingsdatum(UPDATED_BOEKINGSDATUM)
            .boekmaand(UPDATED_BOEKMAAND)
            .invoerdag(UPDATED_INVOERDAG)
            .invoertijd(UPDATED_INVOERTIJD);

        restDocumentkopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentkop.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDocumentkop))
            )
            .andExpect(status().isOk());

        // Validate the Documentkop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentkopToMatchAllProperties(updatedDocumentkop);
    }

    @Test
    @Transactional
    void putNonExistingDocumentkop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentkop.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentkopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentkop.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentkop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentkop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentkop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentkop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentkopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentkop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentkop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentkop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentkop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentkopMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentkop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documentkop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentkopWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentkop = documentkopRepository.saveAndFlush(documentkop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentkop using partial update
        Documentkop partialUpdatedDocumentkop = new Documentkop();
        partialUpdatedDocumentkop.setId(documentkop.getId());

        partialUpdatedDocumentkop
            .documentNrBoekhoudingsdocument(UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(UPDATED_BOEKJAAR)
            .documentsoort(UPDATED_DOCUMENTSOORT)
            .boekingsdatum(UPDATED_BOEKINGSDATUM)
            .boekmaand(UPDATED_BOEKMAAND)
            .invoertijd(UPDATED_INVOERTIJD);

        restDocumentkopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentkop.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentkop))
            )
            .andExpect(status().isOk());

        // Validate the Documentkop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentkopUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentkop, documentkop),
            getPersistedDocumentkop(documentkop)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentkopWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentkop = documentkopRepository.saveAndFlush(documentkop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentkop using partial update
        Documentkop partialUpdatedDocumentkop = new Documentkop();
        partialUpdatedDocumentkop.setId(documentkop.getId());

        partialUpdatedDocumentkop
            .bedrijfsnummer(UPDATED_BEDRIJFSNUMMER)
            .documentNrBoekhoudingsdocument(UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(UPDATED_BOEKJAAR)
            .documentsoort(UPDATED_DOCUMENTSOORT)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .boekingsdatum(UPDATED_BOEKINGSDATUM)
            .boekmaand(UPDATED_BOEKMAAND)
            .invoerdag(UPDATED_INVOERDAG)
            .invoertijd(UPDATED_INVOERTIJD);

        restDocumentkopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentkop.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentkop))
            )
            .andExpect(status().isOk());

        // Validate the Documentkop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentkopUpdatableFieldsEquals(partialUpdatedDocumentkop, getPersistedDocumentkop(partialUpdatedDocumentkop));
    }

    @Test
    @Transactional
    void patchNonExistingDocumentkop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentkop.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentkopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentkop.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentkop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentkop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentkop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentkop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentkopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentkop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentkop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentkop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentkop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentkopMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentkop))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documentkop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentkop() throws Exception {
        // Initialize the database
        insertedDocumentkop = documentkopRepository.saveAndFlush(documentkop);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentkop
        restDocumentkopMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentkop.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentkopRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Documentkop getPersistedDocumentkop(Documentkop documentkop) {
        return documentkopRepository.findById(documentkop.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentkopToMatchAllProperties(Documentkop expectedDocumentkop) {
        assertDocumentkopAllPropertiesEquals(expectedDocumentkop, getPersistedDocumentkop(expectedDocumentkop));
    }

    protected void assertPersistedDocumentkopToMatchUpdatableProperties(Documentkop expectedDocumentkop) {
        assertDocumentkopAllUpdatablePropertiesEquals(expectedDocumentkop, getPersistedDocumentkop(expectedDocumentkop));
    }
}
