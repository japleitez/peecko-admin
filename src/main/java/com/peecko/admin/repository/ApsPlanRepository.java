package com.peecko.admin.repository;

import com.peecko.admin.domain.ApsPlan;
import com.peecko.admin.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApsPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApsPlanRepository extends JpaRepository<ApsPlan, Long> {
    Page<ApsPlan> findByCustomer(Customer customer, Pageable pageable);
}
