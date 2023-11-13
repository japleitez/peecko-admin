package com.peecko.admin.repository;

import com.peecko.admin.domain.VideoStat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VideoStat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoStatRepository extends JpaRepository<VideoStat, Long> {}
