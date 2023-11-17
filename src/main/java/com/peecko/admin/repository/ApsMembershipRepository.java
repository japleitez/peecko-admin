package com.peecko.admin.repository;

import com.peecko.admin.domain.ApsMembership;
import com.peecko.admin.domain.ApsOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApsMembership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApsMembershipRepository extends JpaRepository<ApsMembership, Long> {
    Page<ApsMembership> findByApsOrder(ApsOrder apsOrder, Pageable pageable);
}
