package be.kclaes83.accountingassistant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Documentsegment.
 */
@Entity
@Table(name = "documentsegment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Documentsegment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 4)
    @Column(name = "bedrijfsnummer", length = 4)
    private String bedrijfsnummer;

    @Size(max = 10)
    @Column(name = "document_nr_boekhoudingsdocument", length = 10)
    private String documentNrBoekhoudingsdocument;

    @Size(max = 4)
    @Column(name = "boekjaar", length = 4)
    private String boekjaar;

    @Size(max = 3)
    @Column(name = "boekingsregel_nr_boekhoudingsdocument", length = 3)
    private String boekingsregelNrBoekhoudingsdocument;

    @Size(max = 1)
    @Column(name = "boekingsregel_identificatie", length = 1)
    private String boekingsregelIdentificatie;

    @Column(name = "vereffeningsdatum")
    private Instant vereffeningsdatum;

    @Column(name = "invoerdatum_vereffening")
    private Instant invoerdatumVereffening;

    @Size(max = 10)
    @Column(name = "vereffeningsdocument_nr", length = 10)
    private String vereffeningsdocumentNr;

    @NotNull
    @Size(max = 2)
    @Column(name = "boekingssleutel", length = 2, nullable = false)
    private String boekingssleutel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "documentsegments" }, allowSetters = true)
    private Documentkop documentkop;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Documentsegment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBedrijfsnummer() {
        return this.bedrijfsnummer;
    }

    public Documentsegment bedrijfsnummer(String bedrijfsnummer) {
        this.setBedrijfsnummer(bedrijfsnummer);
        return this;
    }

    public void setBedrijfsnummer(String bedrijfsnummer) {
        this.bedrijfsnummer = bedrijfsnummer;
    }

    public String getDocumentNrBoekhoudingsdocument() {
        return this.documentNrBoekhoudingsdocument;
    }

    public Documentsegment documentNrBoekhoudingsdocument(String documentNrBoekhoudingsdocument) {
        this.setDocumentNrBoekhoudingsdocument(documentNrBoekhoudingsdocument);
        return this;
    }

    public void setDocumentNrBoekhoudingsdocument(String documentNrBoekhoudingsdocument) {
        this.documentNrBoekhoudingsdocument = documentNrBoekhoudingsdocument;
    }

    public String getBoekjaar() {
        return this.boekjaar;
    }

    public Documentsegment boekjaar(String boekjaar) {
        this.setBoekjaar(boekjaar);
        return this;
    }

    public void setBoekjaar(String boekjaar) {
        this.boekjaar = boekjaar;
    }

    public String getBoekingsregelNrBoekhoudingsdocument() {
        return this.boekingsregelNrBoekhoudingsdocument;
    }

    public Documentsegment boekingsregelNrBoekhoudingsdocument(String boekingsregelNrBoekhoudingsdocument) {
        this.setBoekingsregelNrBoekhoudingsdocument(boekingsregelNrBoekhoudingsdocument);
        return this;
    }

    public void setBoekingsregelNrBoekhoudingsdocument(String boekingsregelNrBoekhoudingsdocument) {
        this.boekingsregelNrBoekhoudingsdocument = boekingsregelNrBoekhoudingsdocument;
    }

    public String getBoekingsregelIdentificatie() {
        return this.boekingsregelIdentificatie;
    }

    public Documentsegment boekingsregelIdentificatie(String boekingsregelIdentificatie) {
        this.setBoekingsregelIdentificatie(boekingsregelIdentificatie);
        return this;
    }

    public void setBoekingsregelIdentificatie(String boekingsregelIdentificatie) {
        this.boekingsregelIdentificatie = boekingsregelIdentificatie;
    }

    public Instant getVereffeningsdatum() {
        return this.vereffeningsdatum;
    }

    public Documentsegment vereffeningsdatum(Instant vereffeningsdatum) {
        this.setVereffeningsdatum(vereffeningsdatum);
        return this;
    }

    public void setVereffeningsdatum(Instant vereffeningsdatum) {
        this.vereffeningsdatum = vereffeningsdatum;
    }

    public Instant getInvoerdatumVereffening() {
        return this.invoerdatumVereffening;
    }

    public Documentsegment invoerdatumVereffening(Instant invoerdatumVereffening) {
        this.setInvoerdatumVereffening(invoerdatumVereffening);
        return this;
    }

    public void setInvoerdatumVereffening(Instant invoerdatumVereffening) {
        this.invoerdatumVereffening = invoerdatumVereffening;
    }

    public String getVereffeningsdocumentNr() {
        return this.vereffeningsdocumentNr;
    }

    public Documentsegment vereffeningsdocumentNr(String vereffeningsdocumentNr) {
        this.setVereffeningsdocumentNr(vereffeningsdocumentNr);
        return this;
    }

    public void setVereffeningsdocumentNr(String vereffeningsdocumentNr) {
        this.vereffeningsdocumentNr = vereffeningsdocumentNr;
    }

    public String getBoekingssleutel() {
        return this.boekingssleutel;
    }

    public Documentsegment boekingssleutel(String boekingssleutel) {
        this.setBoekingssleutel(boekingssleutel);
        return this;
    }

    public void setBoekingssleutel(String boekingssleutel) {
        this.boekingssleutel = boekingssleutel;
    }

    public Documentkop getDocumentkop() {
        return this.documentkop;
    }

    public void setDocumentkop(Documentkop documentkop) {
        this.documentkop = documentkop;
    }

    public Documentsegment documentkop(Documentkop documentkop) {
        this.setDocumentkop(documentkop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documentsegment)) {
            return false;
        }
        return getId() != null && getId().equals(((Documentsegment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documentsegment{" +
            "id=" + getId() +
            ", bedrijfsnummer='" + getBedrijfsnummer() + "'" +
            ", documentNrBoekhoudingsdocument='" + getDocumentNrBoekhoudingsdocument() + "'" +
            ", boekjaar='" + getBoekjaar() + "'" +
            ", boekingsregelNrBoekhoudingsdocument='" + getBoekingsregelNrBoekhoudingsdocument() + "'" +
            ", boekingsregelIdentificatie='" + getBoekingsregelIdentificatie() + "'" +
            ", vereffeningsdatum='" + getVereffeningsdatum() + "'" +
            ", invoerdatumVereffening='" + getInvoerdatumVereffening() + "'" +
            ", vereffeningsdocumentNr='" + getVereffeningsdocumentNr() + "'" +
            ", boekingssleutel='" + getBoekingssleutel() + "'" +
            "}";
    }
}
