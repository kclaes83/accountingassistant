package be.kclaes83.accountingassistant.service.impl;

import be.kclaes83.accountingassistant.domain.Documentkop;
import be.kclaes83.accountingassistant.repository.DocumentkopRepository;
import be.kclaes83.accountingassistant.service.DocumentkopService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link be.kclaes83.accountingassistant.domain.Documentkop}.
 */
@Service
@Transactional
public class DocumentkopServiceImpl implements DocumentkopService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentkopServiceImpl.class);

    private final DocumentkopRepository documentkopRepository;

    public DocumentkopServiceImpl(DocumentkopRepository documentkopRepository) {
        this.documentkopRepository = documentkopRepository;
    }

    @Override
    public Documentkop save(Documentkop documentkop) {
        LOG.debug("Request to save Documentkop : {}", documentkop);
        return documentkopRepository.save(documentkop);
    }

    @Override
    public Documentkop update(Documentkop documentkop) {
        LOG.debug("Request to update Documentkop : {}", documentkop);
        return documentkopRepository.save(documentkop);
    }

    @Override
    public Optional<Documentkop> partialUpdate(Documentkop documentkop) {
        LOG.debug("Request to partially update Documentkop : {}", documentkop);

        return documentkopRepository
            .findById(documentkop.getId())
            .map(existingDocumentkop -> {
                if (documentkop.getBedrijfsnummer() != null) {
                    existingDocumentkop.setBedrijfsnummer(documentkop.getBedrijfsnummer());
                }
                if (documentkop.getDocumentNrBoekhoudingsdocument() != null) {
                    existingDocumentkop.setDocumentNrBoekhoudingsdocument(documentkop.getDocumentNrBoekhoudingsdocument());
                }
                if (documentkop.getBoekjaar() != null) {
                    existingDocumentkop.setBoekjaar(documentkop.getBoekjaar());
                }
                if (documentkop.getDocumentsoort() != null) {
                    existingDocumentkop.setDocumentsoort(documentkop.getDocumentsoort());
                }
                if (documentkop.getDocumentdatum() != null) {
                    existingDocumentkop.setDocumentdatum(documentkop.getDocumentdatum());
                }
                if (documentkop.getBoekingsdatum() != null) {
                    existingDocumentkop.setBoekingsdatum(documentkop.getBoekingsdatum());
                }
                if (documentkop.getBoekmaand() != null) {
                    existingDocumentkop.setBoekmaand(documentkop.getBoekmaand());
                }
                if (documentkop.getInvoerdag() != null) {
                    existingDocumentkop.setInvoerdag(documentkop.getInvoerdag());
                }
                if (documentkop.getInvoertijd() != null) {
                    existingDocumentkop.setInvoertijd(documentkop.getInvoertijd());
                }

                return existingDocumentkop;
            })
            .map(documentkopRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Documentkop> findAll(Pageable pageable) {
        LOG.debug("Request to get all Documentkops");
        return documentkopRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Documentkop> findOne(Long id) {
        LOG.debug("Request to get Documentkop : {}", id);
        return documentkopRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Documentkop : {}", id);
        documentkopRepository.deleteById(id);
    }
}
