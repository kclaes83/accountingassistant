package be.kclaes83.accountingassistant.service.impl;

import be.kclaes83.accountingassistant.domain.Documentsegment;
import be.kclaes83.accountingassistant.repository.DocumentsegmentRepository;
import be.kclaes83.accountingassistant.service.DocumentsegmentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link be.kclaes83.accountingassistant.domain.Documentsegment}.
 */
@Service
@Transactional
public class DocumentsegmentServiceImpl implements DocumentsegmentService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentsegmentServiceImpl.class);

    private final DocumentsegmentRepository documentsegmentRepository;

    public DocumentsegmentServiceImpl(DocumentsegmentRepository documentsegmentRepository) {
        this.documentsegmentRepository = documentsegmentRepository;
    }

    @Override
    public Documentsegment save(Documentsegment documentsegment) {
        LOG.debug("Request to save Documentsegment : {}", documentsegment);
        return documentsegmentRepository.save(documentsegment);
    }

    @Override
    public Documentsegment update(Documentsegment documentsegment) {
        LOG.debug("Request to update Documentsegment : {}", documentsegment);
        return documentsegmentRepository.save(documentsegment);
    }

    @Override
    public Optional<Documentsegment> partialUpdate(Documentsegment documentsegment) {
        LOG.debug("Request to partially update Documentsegment : {}", documentsegment);

        return documentsegmentRepository
            .findById(documentsegment.getId())
            .map(existingDocumentsegment -> {
                if (documentsegment.getBedrijfsnummer() != null) {
                    existingDocumentsegment.setBedrijfsnummer(documentsegment.getBedrijfsnummer());
                }
                if (documentsegment.getDocumentNrBoekhoudingsdocument() != null) {
                    existingDocumentsegment.setDocumentNrBoekhoudingsdocument(documentsegment.getDocumentNrBoekhoudingsdocument());
                }
                if (documentsegment.getBoekjaar() != null) {
                    existingDocumentsegment.setBoekjaar(documentsegment.getBoekjaar());
                }
                if (documentsegment.getBoekingsregelNrBoekhoudingsdocument() != null) {
                    existingDocumentsegment.setBoekingsregelNrBoekhoudingsdocument(
                        documentsegment.getBoekingsregelNrBoekhoudingsdocument()
                    );
                }
                if (documentsegment.getBoekingsregelIdentificatie() != null) {
                    existingDocumentsegment.setBoekingsregelIdentificatie(documentsegment.getBoekingsregelIdentificatie());
                }
                if (documentsegment.getVereffeningsdatum() != null) {
                    existingDocumentsegment.setVereffeningsdatum(documentsegment.getVereffeningsdatum());
                }
                if (documentsegment.getInvoerdatumVereffening() != null) {
                    existingDocumentsegment.setInvoerdatumVereffening(documentsegment.getInvoerdatumVereffening());
                }
                if (documentsegment.getVereffeningsdocumentNr() != null) {
                    existingDocumentsegment.setVereffeningsdocumentNr(documentsegment.getVereffeningsdocumentNr());
                }
                if (documentsegment.getBoekingssleutel() != null) {
                    existingDocumentsegment.setBoekingssleutel(documentsegment.getBoekingssleutel());
                }

                return existingDocumentsegment;
            })
            .map(documentsegmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Documentsegment> findAll(Pageable pageable) {
        LOG.debug("Request to get all Documentsegments");
        return documentsegmentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Documentsegment> findOne(Long id) {
        LOG.debug("Request to get Documentsegment : {}", id);
        return documentsegmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Documentsegment : {}", id);
        documentsegmentRepository.deleteById(id);
    }
}
