package be.kclaes83.accountingassistant.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentkopTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Documentkop getDocumentkopSample1() {
        return new Documentkop()
            .id(1L)
            .bedrijfsnummer("bedrijfsnummer1")
            .documentNrBoekhoudingsdocument("documentNrBoekhoudingsdocument1")
            .boekjaar("boekjaar1");
    }

    public static Documentkop getDocumentkopSample2() {
        return new Documentkop()
            .id(2L)
            .bedrijfsnummer("bedrijfsnummer2")
            .documentNrBoekhoudingsdocument("documentNrBoekhoudingsdocument2")
            .boekjaar("boekjaar2");
    }

    public static Documentkop getDocumentkopRandomSampleGenerator() {
        return new Documentkop()
            .id(longCount.incrementAndGet())
            .bedrijfsnummer(UUID.randomUUID().toString())
            .documentNrBoekhoudingsdocument(UUID.randomUUID().toString())
            .boekjaar(UUID.randomUUID().toString());
    }
}
