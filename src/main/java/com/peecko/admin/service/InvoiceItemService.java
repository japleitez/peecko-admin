package com.peecko.admin.service;

import com.peecko.admin.domain.InvoiceItem;
import com.peecko.admin.repository.InvoiceItemRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.InvoiceItem}.
 */
@Service
@Transactional
public class InvoiceItemService {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemService.class);

    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    /**
     * Save a invoiceItem.
     *
     * @param invoiceItem the entity to save.
     * @return the persisted entity.
     */
    public InvoiceItem save(InvoiceItem invoiceItem) {
        log.debug("Request to save InvoiceItem : {}", invoiceItem);
        return invoiceItemRepository.save(invoiceItem);
    }

    /**
     * Update a invoiceItem.
     *
     * @param invoiceItem the entity to save.
     * @return the persisted entity.
     */
    public InvoiceItem update(InvoiceItem invoiceItem) {
        log.debug("Request to update InvoiceItem : {}", invoiceItem);
        return invoiceItemRepository.save(invoiceItem);
    }

    /**
     * Partially update a invoiceItem.
     *
     * @param invoiceItem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InvoiceItem> partialUpdate(InvoiceItem invoiceItem) {
        log.debug("Request to partially update InvoiceItem : {}", invoiceItem);

        return invoiceItemRepository
            .findById(invoiceItem.getId())
            .map(existingInvoiceItem -> {
                if (invoiceItem.getType() != null) {
                    existingInvoiceItem.setType(invoiceItem.getType());
                }
                if (invoiceItem.getDescription() != null) {
                    existingInvoiceItem.setDescription(invoiceItem.getDescription());
                }
                if (invoiceItem.getQuatity() != null) {
                    existingInvoiceItem.setQuatity(invoiceItem.getQuatity());
                }
                if (invoiceItem.getPriceUnit() != null) {
                    existingInvoiceItem.setPriceUnit(invoiceItem.getPriceUnit());
                }
                if (invoiceItem.getPriceExtended() != null) {
                    existingInvoiceItem.setPriceExtended(invoiceItem.getPriceExtended());
                }
                if (invoiceItem.getDisRate() != null) {
                    existingInvoiceItem.setDisRate(invoiceItem.getDisRate());
                }
                if (invoiceItem.getDisAmount() != null) {
                    existingInvoiceItem.setDisAmount(invoiceItem.getDisAmount());
                }
                if (invoiceItem.getFinalPrice() != null) {
                    existingInvoiceItem.setFinalPrice(invoiceItem.getFinalPrice());
                }
                if (invoiceItem.getVatRate() != null) {
                    existingInvoiceItem.setVatRate(invoiceItem.getVatRate());
                }
                if (invoiceItem.getVatAmount() != null) {
                    existingInvoiceItem.setVatAmount(invoiceItem.getVatAmount());
                }
                if (invoiceItem.getTotal() != null) {
                    existingInvoiceItem.setTotal(invoiceItem.getTotal());
                }

                return existingInvoiceItem;
            })
            .map(invoiceItemRepository::save);
    }

    /**
     * Get all the invoiceItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceItem> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceItems");
        return invoiceItemRepository.findAll(pageable);
    }

    /**
     * Get one invoiceItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceItem> findOne(Long id) {
        log.debug("Request to get InvoiceItem : {}", id);
        return invoiceItemRepository.findById(id);
    }

    /**
     * Delete the invoiceItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InvoiceItem : {}", id);
        invoiceItemRepository.deleteById(id);
    }
}
