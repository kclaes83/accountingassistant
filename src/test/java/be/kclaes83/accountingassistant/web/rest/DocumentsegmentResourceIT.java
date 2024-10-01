package be.kclaes83.accountingassistant.web.rest;

import static be.kclaes83.accountingassistant.domain.DocumentsegmentAsserts.*;
import static be.kclaes83.accountingassistant.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import be.kclaes83.accountingassistant.IntegrationTest;
import be.kclaes83.accountingassistant.domain.Documentsegment;
import be.kclaes83.accountingassistant.repository.DocumentsegmentRepository;
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
 * Integration tests for the {@link DocumentsegmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentsegmentResourceIT {

    private static final String DEFAULT_BEDRIJFSNUMMER = "AAAA";
    private static final String UPDATED_BEDRIJFSNUMMER = "BBBB";

    private static final String DEFAULT_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_BOEKJAAR = "AAAA";
    private static final String UPDATED_BOEKJAAR = "BBBB";

    private static final String DEFAULT_BOEKINGSREGEL_NR_BOEKHOUDINGSDOCUMENT = "AAA";
    private static final String UPDATED_BOEKINGSREGEL_NR_BOEKHOUDINGSDOCUMENT = "BBB";

    private static final String DEFAULT_BOEKINGSREGEL_IDENTIFICATIE = "A";
    private static final String UPDATED_BOEKINGSREGEL_IDENTIFICATIE = "B";

    private static final Instant DEFAULT_VEREFFENINGSDATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VEREFFENINGSDATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INVOERDATUM_VEREFFENING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOERDATUM_VEREFFENING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VEREFFENINGSDOCUMENT_NR = "AAAAAAAAAA";
    private static final String UPDATED_VEREFFENINGSDOCUMENT_NR = "BBBBBBBBBB";

    private static final String DEFAULT_BOEKINGSSLEUTEL = "AA";
    private static final String UPDATED_BOEKINGSSLEUTEL = "BB";

    private static final String ENTITY_API_URL = "/api/documentsegments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentsegmentRepository documentsegmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentsegmentMockMvc;

    private Documentsegment documentsegment;

    private Documentsegment insertedDocumentsegment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documentsegment createEntity() {
        return new Documentsegment()
            .bedrijfsnummer(DEFAULT_BEDRIJFSNUMMER)
            .documentNrBoekhoudingsdocument(DEFAULT_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(DEFAULT_BOEKJAAR)
            .boekingsregelNrBoekhoudingsdocument(DEFAULT_BOEKINGSREGEL_NR_BOEKHOUDINGSDOCUMENT)
            .boekingsregelIdentificatie(DEFAULT_BOEKINGSREGEL_IDENTIFICATIE)
            .vereffeningsdatum(DEFAULT_VEREFFENINGSDATUM)
            .invoerdatumVereffening(DEFAULT_INVOERDATUM_VEREFFENING)
            .vereffeningsdocumentNr(DEFAULT_VEREFFENINGSDOCUMENT_NR)
            .boekingssleutel(DEFAULT_BOEKINGSSLEUTEL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documentsegment createUpdatedEntity() {
        return new Documentsegment()
            .bedrijfsnummer(UPDATED_BEDRIJFSNUMMER)
            .documentNrBoekhoudingsdocument(UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(UPDATED_BOEKJAAR)
            .boekingsregelNrBoekhoudingsdocument(UPDATED_BOEKINGSREGEL_NR_BOEKHOUDINGSDOCUMENT)
            .boekingsregelIdentificatie(UPDATED_BOEKINGSREGEL_IDENTIFICATIE)
            .vereffeningsdatum(UPDATED_VEREFFENINGSDATUM)
            .invoerdatumVereffening(UPDATED_INVOERDATUM_VEREFFENING)
            .vereffeningsdocumentNr(UPDATED_VEREFFENINGSDOCUMENT_NR)
            .boekingssleutel(UPDATED_BOEKINGSSLEUTEL);
    }

    @BeforeEach
    public void initTest() {
        documentsegment = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDocumentsegment != null) {
            documentsegmentRepository.delete(insertedDocumentsegment);
            insertedDocumentsegment = null;
        }
    }

    @Test
    @Transactional
    void createDocumentsegment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Documentsegment
        var returnedDocumentsegment = om.readValue(
            restDocumentsegmentMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentsegment))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Documentsegment.class
        );

        // Validate the Documentsegment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDocumentsegmentUpdatableFieldsEquals(returnedDocumentsegment, getPersistedDocumentsegment(returnedDocumentsegment));

        insertedDocumentsegment = returnedDocumentsegment;
    }

    @Test
    @Transactional
    void createDocumentsegmentWithExistingId() throws Exception {
        // Create the Documentsegment with an existing ID
        documentsegment.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentsegmentMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentsegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentsegment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBoekingssleutelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        documentsegment.setBoekingssleutel(null);

        // Create the Documentsegment, which fails.

        restDocumentsegmentMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentsegment))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocumentsegments() throws Exception {
        // Initialize the database
        insertedDocumentsegment = documentsegmentRepository.saveAndFlush(documentsegment);

        // Get all the documentsegmentList
        restDocumentsegmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentsegment.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrijfsnummer").value(hasItem(DEFAULT_BEDRIJFSNUMMER)))
            .andExpect(jsonPath("$.[*].documentNrBoekhoudingsdocument").value(hasItem(DEFAULT_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)))
            .andExpect(jsonPath("$.[*].boekjaar").value(hasItem(DEFAULT_BOEKJAAR)))
            .andExpect(jsonPath("$.[*].boekingsregelNrBoekhoudingsdocument").value(hasItem(DEFAULT_BOEKINGSREGEL_NR_BOEKHOUDINGSDOCUMENT)))
            .andExpect(jsonPath("$.[*].boekingsregelIdentificatie").value(hasItem(DEFAULT_BOEKINGSREGEL_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].vereffeningsdatum").value(hasItem(DEFAULT_VEREFFENINGSDATUM.toString())))
            .andExpect(jsonPath("$.[*].invoerdatumVereffening").value(hasItem(DEFAULT_INVOERDATUM_VEREFFENING.toString())))
            .andExpect(jsonPath("$.[*].vereffeningsdocumentNr").value(hasItem(DEFAULT_VEREFFENINGSDOCUMENT_NR)))
            .andExpect(jsonPath("$.[*].boekingssleutel").value(hasItem(DEFAULT_BOEKINGSSLEUTEL)));
    }

    @Test
    @Transactional
    void getDocumentsegment() throws Exception {
        // Initialize the database
        insertedDocumentsegment = documentsegmentRepository.saveAndFlush(documentsegment);

        // Get the documentsegment
        restDocumentsegmentMockMvc
            .perform(get(ENTITY_API_URL_ID, documentsegment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentsegment.getId().intValue()))
            .andExpect(jsonPath("$.bedrijfsnummer").value(DEFAULT_BEDRIJFSNUMMER))
            .andExpect(jsonPath("$.documentNrBoekhoudingsdocument").value(DEFAULT_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT))
            .andExpect(jsonPath("$.boekjaar").value(DEFAULT_BOEKJAAR))
            .andExpect(jsonPath("$.boekingsregelNrBoekhoudingsdocument").value(DEFAULT_BOEKINGSREGEL_NR_BOEKHOUDINGSDOCUMENT))
            .andExpect(jsonPath("$.boekingsregelIdentificatie").value(DEFAULT_BOEKINGSREGEL_IDENTIFICATIE))
            .andExpect(jsonPath("$.vereffeningsdatum").value(DEFAULT_VEREFFENINGSDATUM.toString()))
            .andExpect(jsonPath("$.invoerdatumVereffening").value(DEFAULT_INVOERDATUM_VEREFFENING.toString()))
            .andExpect(jsonPath("$.vereffeningsdocumentNr").value(DEFAULT_VEREFFENINGSDOCUMENT_NR))
            .andExpect(jsonPath("$.boekingssleutel").value(DEFAULT_BOEKINGSSLEUTEL));
    }

    @Test
    @Transactional
    void getNonExistingDocumentsegment() throws Exception {
        // Get the documentsegment
        restDocumentsegmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentsegment() throws Exception {
        // Initialize the database
        insertedDocumentsegment = documentsegmentRepository.saveAndFlush(documentsegment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentsegment
        Documentsegment updatedDocumentsegment = documentsegmentRepository.findById(documentsegment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentsegment are not directly saved in db
        em.detach(updatedDocumentsegment);
        updatedDocumentsegment
            .bedrijfsnummer(UPDATED_BEDRIJFSNUMMER)
            .documentNrBoekhoudingsdocument(UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(UPDATED_BOEKJAAR)
            .boekingsregelNrBoekhoudingsdocument(UPDATED_BOEKINGSREGEL_NR_BOEKHOUDINGSDOCUMENT)
            .boekingsregelIdentificatie(UPDATED_BOEKINGSREGEL_IDENTIFICATIE)
            .vereffeningsdatum(UPDATED_VEREFFENINGSDATUM)
            .invoerdatumVereffening(UPDATED_INVOERDATUM_VEREFFENING)
            .vereffeningsdocumentNr(UPDATED_VEREFFENINGSDOCUMENT_NR)
            .boekingssleutel(UPDATED_BOEKINGSSLEUTEL);

        restDocumentsegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentsegment.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDocumentsegment))
            )
            .andExpect(status().isOk());

        // Validate the Documentsegment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentsegmentToMatchAllProperties(updatedDocumentsegment);
    }

    @Test
    @Transactional
    void putNonExistingDocumentsegment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentsegment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentsegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentsegment.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentsegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentsegment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentsegment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentsegment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentsegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentsegment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentsegment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentsegment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsegmentMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentsegment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documentsegment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentsegmentWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentsegment = documentsegmentRepository.saveAndFlush(documentsegment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentsegment using partial update
        Documentsegment partialUpdatedDocumentsegment = new Documentsegment();
        partialUpdatedDocumentsegment.setId(documentsegment.getId());

        partialUpdatedDocumentsegment
            .bedrijfsnummer(UPDATED_BEDRIJFSNUMMER)
            .vereffeningsdocumentNr(UPDATED_VEREFFENINGSDOCUMENT_NR)
            .boekingssleutel(UPDATED_BOEKINGSSLEUTEL);

        restDocumentsegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentsegment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentsegment))
            )
            .andExpect(status().isOk());

        // Validate the Documentsegment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentsegmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentsegment, documentsegment),
            getPersistedDocumentsegment(documentsegment)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentsegmentWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentsegment = documentsegmentRepository.saveAndFlush(documentsegment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentsegment using partial update
        Documentsegment partialUpdatedDocumentsegment = new Documentsegment();
        partialUpdatedDocumentsegment.setId(documentsegment.getId());

        partialUpdatedDocumentsegment
            .bedrijfsnummer(UPDATED_BEDRIJFSNUMMER)
            .documentNrBoekhoudingsdocument(UPDATED_DOCUMENT_NR_BOEKHOUDINGSDOCUMENT)
            .boekjaar(UPDATED_BOEKJAAR)
            .boekingsregelNrBoekhoudingsdocument(UPDATED_BOEKINGSREGEL_NR_BOEKHOUDINGSDOCUMENT)
            .boekingsregelIdentificatie(UPDATED_BOEKINGSREGEL_IDENTIFICATIE)
            .vereffeningsdatum(UPDATED_VEREFFENINGSDATUM)
            .invoerdatumVereffening(UPDATED_INVOERDATUM_VEREFFENING)
            .vereffeningsdocumentNr(UPDATED_VEREFFENINGSDOCUMENT_NR)
            .boekingssleutel(UPDATED_BOEKINGSSLEUTEL);

        restDocumentsegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentsegment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentsegment))
            )
            .andExpect(status().isOk());

        // Validate the Documentsegment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentsegmentUpdatableFieldsEquals(
            partialUpdatedDocumentsegment,
            getPersistedDocumentsegment(partialUpdatedDocumentsegment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDocumentsegment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentsegment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentsegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentsegment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentsegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentsegment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentsegment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentsegment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentsegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documentsegment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentsegment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentsegment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsegmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentsegment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documentsegment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentsegment() throws Exception {
        // Initialize the database
        insertedDocumentsegment = documentsegmentRepository.saveAndFlush(documentsegment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentsegment
        restDocumentsegmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentsegment.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentsegmentRepository.count();
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

    protected Documentsegment getPersistedDocumentsegment(Documentsegment documentsegment) {
        return documentsegmentRepository.findById(documentsegment.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentsegmentToMatchAllProperties(Documentsegment expectedDocumentsegment) {
        assertDocumentsegmentAllPropertiesEquals(expectedDocumentsegment, getPersistedDocumentsegment(expectedDocumentsegment));
    }

    protected void assertPersistedDocumentsegmentToMatchUpdatableProperties(Documentsegment expectedDocumentsegment) {
        assertDocumentsegmentAllUpdatablePropertiesEquals(expectedDocumentsegment, getPersistedDocumentsegment(expectedDocumentsegment));
    }
}
