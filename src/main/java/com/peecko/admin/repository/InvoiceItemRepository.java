package com.peecko.admin.repository;

import com.peecko.admin.domain.Invoice;
import com.peecko.admin.domain.InvoiceItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InvoiceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    Page<InvoiceItem> findByInvoice(Invoice invoice, Pageable pageable);
}
