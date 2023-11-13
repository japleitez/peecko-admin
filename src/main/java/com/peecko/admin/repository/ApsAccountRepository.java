package com.peecko.admin.repository;

import com.peecko.admin.domain.ApsAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApsAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApsAccountRepository extends JpaRepository<ApsAccount, Long> {}
