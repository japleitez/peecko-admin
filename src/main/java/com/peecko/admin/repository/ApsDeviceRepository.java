package com.peecko.admin.repository;

import com.peecko.admin.domain.ApsDevice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApsDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApsDeviceRepository extends JpaRepository<ApsDevice, Long> {}
