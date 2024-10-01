package be.kclaes83.accountingassistant.domain;

import be.kclaes83.accountingassistant.domain.enumeration.DocumentSoort;
import be.kclaes83.accountingassistant.domain.enumeration.Maand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Documentkop.
 */
@Entity
@Table(name = "documentkop")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Documentkop implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "documentsoort")
    private DocumentSoort documentsoort;

    @Column(name = "documentdatum")
    private Instant documentdatum;

    @Column(name = "boekingsdatum")
    private Instant boekingsdatum;

    @Enumerated(EnumType.STRING)
    @Column(name = "boekmaand")
    private Maand boekmaand;

    @Column(name = "invoerdag")
    private Instant invoerdag;

    @Column(name = "invoertijd")
    private Instant invoertijd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentkop")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documentkop" }, allowSetters = true)
    private Set<Documentsegment> documentsegments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Documentkop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBedrijfsnummer() {
        return this.bedrijfsnummer;
    }

    public Documentkop bedrijfsnummer(String bedrijfsnummer) {
        this.setBedrijfsnummer(bedrijfsnummer);
        return this;
    }

    public void setBedrijfsnummer(String bedrijfsnummer) {
        this.bedrijfsnummer = bedrijfsnummer;
    }

    public String getDocumentNrBoekhoudingsdocument() {
        return this.documentNrBoekhoudingsdocument;
    }

    public Documentkop documentNrBoekhoudingsdocument(String documentNrBoekhoudingsdocument) {
        this.setDocumentNrBoekhoudingsdocument(documentNrBoekhoudingsdocument);
        return this;
    }

    public void setDocumentNrBoekhoudingsdocument(String documentNrBoekhoudingsdocument) {
        this.documentNrBoekhoudingsdocument = documentNrBoekhoudingsdocument;
    }

    public String getBoekjaar() {
        return this.boekjaar;
    }

    public Documentkop boekjaar(String boekjaar) {
        this.setBoekjaar(boekjaar);
        return this;
    }

    public void setBoekjaar(String boekjaar) {
        this.boekjaar = boekjaar;
    }

    public DocumentSoort getDocumentsoort() {
        return this.documentsoort;
    }

    public Documentkop documentsoort(DocumentSoort documentsoort) {
        this.setDocumentsoort(documentsoort);
        return this;
    }

    public void setDocumentsoort(DocumentSoort documentsoort) {
        this.documentsoort = documentsoort;
    }

    public Instant getDocumentdatum() {
        return this.documentdatum;
    }

    public Documentkop documentdatum(Instant documentdatum) {
        this.setDocumentdatum(documentdatum);
        return this;
    }

    public void setDocumentdatum(Instant documentdatum) {
        this.documentdatum = documentdatum;
    }

    public Instant getBoekingsdatum() {
        return this.boekingsdatum;
    }

    public Documentkop boekingsdatum(Instant boekingsdatum) {
        this.setBoekingsdatum(boekingsdatum);
        return this;
    }

    public void setBoekingsdatum(Instant boekingsdatum) {
        this.boekingsdatum = boekingsdatum;
    }

    public Maand getBoekmaand() {
        return this.boekmaand;
    }

    public Documentkop boekmaand(Maand boekmaand) {
        this.setBoekmaand(boekmaand);
        return this;
    }

    public void setBoekmaand(Maand boekmaand) {
        this.boekmaand = boekmaand;
    }

    public Instant getInvoerdag() {
        return this.invoerdag;
    }

    public Documentkop invoerdag(Instant invoerdag) {
        this.setInvoerdag(invoerdag);
        return this;
    }

    public void setInvoerdag(Instant invoerdag) {
        this.invoerdag = invoerdag;
    }

    public Instant getInvoertijd() {
        return this.invoertijd;
    }

    public Documentkop invoertijd(Instant invoertijd) {
        this.setInvoertijd(invoertijd);
        return this;
    }

    public void setInvoertijd(Instant invoertijd) {
        this.invoertijd = invoertijd;
    }

    public Set<Documentsegment> getDocumentsegments() {
        return this.documentsegments;
    }

    public void setDocumentsegments(Set<Documentsegment> documentsegments) {
        if (this.documentsegments != null) {
            this.documentsegments.forEach(i -> i.setDocumentkop(null));
        }
        if (documentsegments != null) {
            documentsegments.forEach(i -> i.setDocumentkop(this));
        }
        this.documentsegments = documentsegments;
    }

    public Documentkop documentsegments(Set<Documentsegment> documentsegments) {
        this.setDocumentsegments(documentsegments);
        return this;
    }

    public Documentkop addDocumentsegment(Documentsegment documentsegment) {
        this.documentsegments.add(documentsegment);
        documentsegment.setDocumentkop(this);
        return this;
    }

    public Documentkop removeDocumentsegment(Documentsegment documentsegment) {
        this.documentsegments.remove(documentsegment);
        documentsegment.setDocumentkop(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documentkop)) {
            return false;
        }
        return getId() != null && getId().equals(((Documentkop) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documentkop{" +
            "id=" + getId() +
            ", bedrijfsnummer='" + getBedrijfsnummer() + "'" +
            ", documentNrBoekhoudingsdocument='" + getDocumentNrBoekhoudingsdocument() + "'" +
            ", boekjaar='" + getBoekjaar() + "'" +
            ", documentsoort='" + getDocumentsoort() + "'" +
            ", documentdatum='" + getDocumentdatum() + "'" +
            ", boekingsdatum='" + getBoekingsdatum() + "'" +
            ", boekmaand='" + getBoekmaand() + "'" +
            ", invoerdag='" + getInvoerdag() + "'" +
            ", invoertijd='" + getInvoertijd() + "'" +
            "}";
    }
}
