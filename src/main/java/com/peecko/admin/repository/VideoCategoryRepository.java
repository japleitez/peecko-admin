package com.peecko.admin.repository;

import com.peecko.admin.domain.VideoCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VideoCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Long> {}
