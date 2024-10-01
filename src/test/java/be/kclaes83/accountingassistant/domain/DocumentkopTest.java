package be.kclaes83.accountingassistant.domain;

import static be.kclaes83.accountingassistant.domain.DocumentkopTestSamples.*;
import static be.kclaes83.accountingassistant.domain.DocumentsegmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import be.kclaes83.accountingassistant.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DocumentkopTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documentkop.class);
        Documentkop documentkop1 = getDocumentkopSample1();
        Documentkop documentkop2 = new Documentkop();
        assertThat(documentkop1).isNotEqualTo(documentkop2);

        documentkop2.setId(documentkop1.getId());
        assertThat(documentkop1).isEqualTo(documentkop2);

        documentkop2 = getDocumentkopSample2();
        assertThat(documentkop1).isNotEqualTo(documentkop2);
    }

    @Test
    void documentsegmentTest() {
        Documentkop documentkop = getDocumentkopRandomSampleGenerator();
        Documentsegment documentsegmentBack = getDocumentsegmentRandomSampleGenerator();

        documentkop.addDocumentsegment(documentsegmentBack);
        assertThat(documentkop.getDocumentsegments()).containsOnly(documentsegmentBack);
        assertThat(documentsegmentBack.getDocumentkop()).isEqualTo(documentkop);

        documentkop.removeDocumentsegment(documentsegmentBack);
        assertThat(documentkop.getDocumentsegments()).doesNotContain(documentsegmentBack);
        assertThat(documentsegmentBack.getDocumentkop()).isNull();

        documentkop.documentsegments(new HashSet<>(Set.of(documentsegmentBack)));
        assertThat(documentkop.getDocumentsegments()).containsOnly(documentsegmentBack);
        assertThat(documentsegmentBack.getDocumentkop()).isEqualTo(documentkop);

        documentkop.setDocumentsegments(new HashSet<>());
        assertThat(documentkop.getDocumentsegments()).doesNotContain(documentsegmentBack);
        assertThat(documentsegmentBack.getDocumentkop()).isNull();
    }
}
