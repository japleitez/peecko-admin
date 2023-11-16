package com.peecko.admin.repository;

import com.peecko.admin.domain.ApsOrder;
import com.peecko.admin.domain.ApsPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApsOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApsOrderRepository extends JpaRepository<ApsOrder, Long> {
    Page<ApsOrder> findByApsPlan(ApsPlan apsPlan, Pageable pageable);
}
