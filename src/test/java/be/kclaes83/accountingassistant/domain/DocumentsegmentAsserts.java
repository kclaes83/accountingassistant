package be.kclaes83.accountingassistant.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentsegmentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentsegmentAllPropertiesEquals(Documentsegment expected, Documentsegment actual) {
        assertDocumentsegmentAutoGeneratedPropertiesEquals(expected, actual);
        assertDocumentsegmentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentsegmentAllUpdatablePropertiesEquals(Documentsegment expected, Documentsegment actual) {
        assertDocumentsegmentUpdatableFieldsEquals(expected, actual);
        assertDocumentsegmentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentsegmentAutoGeneratedPropertiesEquals(Documentsegment expected, Documentsegment actual) {
        assertThat(expected)
            .as("Verify Documentsegment auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentsegmentUpdatableFieldsEquals(Documentsegment expected, Documentsegment actual) {
        assertThat(expected)
            .as("Verify Documentsegment relevant properties")
            .satisfies(e -> assertThat(e.getBedrijfsnummer()).as("check bedrijfsnummer").isEqualTo(actual.getBedrijfsnummer()))
            .satisfies(e ->
                assertThat(e.getDocumentNrBoekhoudingsdocument())
                    .as("check documentNrBoekhoudingsdocument")
                    .isEqualTo(actual.getDocumentNrBoekhoudingsdocument())
            )
            .satisfies(e -> assertThat(e.getBoekjaar()).as("check boekjaar").isEqualTo(actual.getBoekjaar()))
            .satisfies(e ->
                assertThat(e.getBoekingsregelNrBoekhoudingsdocument())
                    .as("check boekingsregelNrBoekhoudingsdocument")
                    .isEqualTo(actual.getBoekingsregelNrBoekhoudingsdocument())
            )
            .satisfies(e ->
                assertThat(e.getBoekingsregelIdentificatie())
                    .as("check boekingsregelIdentificatie")
                    .isEqualTo(actual.getBoekingsregelIdentificatie())
            )
            .satisfies(e -> assertThat(e.getVereffeningsdatum()).as("check vereffeningsdatum").isEqualTo(actual.getVereffeningsdatum()))
            .satisfies(e ->
                assertThat(e.getInvoerdatumVereffening()).as("check invoerdatumVereffening").isEqualTo(actual.getInvoerdatumVereffening())
            )
            .satisfies(e ->
                assertThat(e.getVereffeningsdocumentNr()).as("check vereffeningsdocumentNr").isEqualTo(actual.getVereffeningsdocumentNr())
            )
            .satisfies(e -> assertThat(e.getBoekingssleutel()).as("check boekingssleutel").isEqualTo(actual.getBoekingssleutel()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentsegmentUpdatableRelationshipsEquals(Documentsegment expected, Documentsegment actual) {
        assertThat(expected)
            .as("Verify Documentsegment relationships")
            .satisfies(e -> assertThat(e.getDocumentkop()).as("check documentkop").isEqualTo(actual.getDocumentkop()));
    }
}
