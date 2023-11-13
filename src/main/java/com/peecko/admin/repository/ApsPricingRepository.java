package com.peecko.admin.repository;

import com.peecko.admin.domain.ApsPricing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApsPricing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApsPricingRepository extends JpaRepository<ApsPricing, Long> {}
