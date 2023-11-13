package com.peecko.admin.repository;

import com.peecko.admin.domain.SecuredRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SecuredRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecuredRequestRepository extends JpaRepository<SecuredRequest, Long> {}
