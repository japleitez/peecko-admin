package com.peecko.admin.repository;

import com.peecko.admin.domain.Contact;
import com.peecko.admin.domain.enumeration.ContactType;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query(value = "SELECT c FROM Contact c WHERE c.company.id = :companyId AND c.type = :type")
    Optional<Contact> findByCompanyAndType(@Param("companyId") Long companyId, @Param("type") ContactType type);
}
