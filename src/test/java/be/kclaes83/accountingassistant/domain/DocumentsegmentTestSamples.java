package be.kclaes83.accountingassistant.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentsegmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Documentsegment getDocumentsegmentSample1() {
        return new Documentsegment()
            .id(1L)
            .bedrijfsnummer("bedrijfsnummer1")
            .documentNrBoekhoudingsdocument("documentNrBoekhoudingsdocument1")
            .boekjaar("boekjaar1")
            .boekingsregelNrBoekhoudingsdocument("boekingsregelNrBoekhoudingsdocument1")
            .boekingsregelIdentificatie("boekingsregelIdentificatie1")
            .vereffeningsdocumentNr("vereffeningsdocumentNr1")
            .boekingssleutel("boekingssleutel1");
    }

    public static Documentsegment getDocumentsegmentSample2() {
        return new Documentsegment()
            .id(2L)
            .bedrijfsnummer("bedrijfsnummer2")
            .documentNrBoekhoudingsdocument("documentNrBoekhoudingsdocument2")
            .boekjaar("boekjaar2")
            .boekingsregelNrBoekhoudingsdocument("boekingsregelNrBoekhoudingsdocument2")
            .boekingsregelIdentificatie("boekingsregelIdentificatie2")
            .vereffeningsdocumentNr("vereffeningsdocumentNr2")
            .boekingssleutel("boekingssleutel2");
    }

    public static Documentsegment getDocumentsegmentRandomSampleGenerator() {
        return new Documentsegment()
            .id(longCount.incrementAndGet())
            .bedrijfsnummer(UUID.randomUUID().toString())
            .documentNrBoekhoudingsdocument(UUID.randomUUID().toString())
            .boekjaar(UUID.randomUUID().toString())
            .boekingsregelNrBoekhoudingsdocument(UUID.randomUUID().toString())
            .boekingsregelIdentificatie(UUID.randomUUID().toString())
            .vereffeningsdocumentNr(UUID.randomUUID().toString())
            .boekingssleutel(UUID.randomUUID().toString());
    }
}
