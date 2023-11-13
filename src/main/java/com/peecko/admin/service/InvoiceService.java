package com.peecko.admin.service;

import com.peecko.admin.domain.Invoice;
import com.peecko.admin.repository.InvoiceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.Invoice}.
 */
@Service
@Transactional
public class InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save.
     * @return the persisted entity.
     */
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    /**
     * Update a invoice.
     *
     * @param invoice the entity to save.
     * @return the persisted entity.
     */
    public Invoice update(Invoice invoice) {
        log.debug("Request to update Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    /**
     * Partially update a invoice.
     *
     * @param invoice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Invoice> partialUpdate(Invoice invoice) {
        log.debug("Request to partially update Invoice : {}", invoice);

        return invoiceRepository
            .findById(invoice.getId())
            .map(existingInvoice -> {
                if (invoice.getNumber() != null) {
                    existingInvoice.setNumber(invoice.getNumber());
                }
                if (invoice.getIssued() != null) {
                    existingInvoice.setIssued(invoice.getIssued());
                }
                if (invoice.getDueDate() != null) {
                    existingInvoice.setDueDate(invoice.getDueDate());
                }
                if (invoice.getSaleDate() != null) {
                    existingInvoice.setSaleDate(invoice.getSaleDate());
                }
                if (invoice.getSubtotal() != null) {
                    existingInvoice.setSubtotal(invoice.getSubtotal());
                }
                if (invoice.getVat() != null) {
                    existingInvoice.setVat(invoice.getVat());
                }
                if (invoice.getTotal() != null) {
                    existingInvoice.setTotal(invoice.getTotal());
                }
                if (invoice.getPaid() != null) {
                    existingInvoice.setPaid(invoice.getPaid());
                }
                if (invoice.getPaidDate() != null) {
                    existingInvoice.setPaidDate(invoice.getPaidDate());
                }
                if (invoice.getDiff() != null) {
                    existingInvoice.setDiff(invoice.getDiff());
                }
                if (invoice.getNotes() != null) {
                    existingInvoice.setNotes(invoice.getNotes());
                }

                return existingInvoice;
            })
            .map(invoiceRepository::save);
    }

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Invoice> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll(pageable);
    }

    /**
     * Get one invoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Invoice> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id);
    }

    /**
     * Delete the invoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
