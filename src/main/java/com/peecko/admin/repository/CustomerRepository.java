package com.peecko.admin.repository;

import com.peecko.admin.domain.Agency;
import com.peecko.admin.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByAgency(Agency agency, Pageable pageable);
}
