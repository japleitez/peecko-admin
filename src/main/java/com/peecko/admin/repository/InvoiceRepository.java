package com.peecko.admin.repository;

import com.peecko.admin.domain.ApsOrder;
import com.peecko.admin.domain.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findByApsOrder(ApsOrder apsOrder, Pageable pageable);
}
