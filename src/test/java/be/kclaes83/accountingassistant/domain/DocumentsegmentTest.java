package be.kclaes83.accountingassistant.domain;

import static be.kclaes83.accountingassistant.domain.DocumentkopTestSamples.*;
import static be.kclaes83.accountingassistant.domain.DocumentsegmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import be.kclaes83.accountingassistant.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentsegmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documentsegment.class);
        Documentsegment documentsegment1 = getDocumentsegmentSample1();
        Documentsegment documentsegment2 = new Documentsegment();
        assertThat(documentsegment1).isNotEqualTo(documentsegment2);

        documentsegment2.setId(documentsegment1.getId());
        assertThat(documentsegment1).isEqualTo(documentsegment2);

        documentsegment2 = getDocumentsegmentSample2();
        assertThat(documentsegment1).isNotEqualTo(documentsegment2);
    }

    @Test
    void documentkopTest() {
        Documentsegment documentsegment = getDocumentsegmentRandomSampleGenerator();
        Documentkop documentkopBack = getDocumentkopRandomSampleGenerator();

        documentsegment.setDocumentkop(documentkopBack);
        assertThat(documentsegment.getDocumentkop()).isEqualTo(documentkopBack);

        documentsegment.documentkop(null);
        assertThat(documentsegment.getDocumentkop()).isNull();
    }
}
