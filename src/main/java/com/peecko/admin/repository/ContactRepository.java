package com.peecko.admin.repository;

import com.peecko.admin.domain.Contact;
import com.peecko.admin.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findByCustomer(Customer customer, Pageable pageable);
}
