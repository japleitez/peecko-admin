package com.peecko.admin.repository;

import com.peecko.admin.domain.LikedVideo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LikedVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikedVideoRepository extends JpaRepository<LikedVideo, Long> {}
