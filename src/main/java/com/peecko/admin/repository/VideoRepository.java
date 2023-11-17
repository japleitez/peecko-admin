package com.peecko.admin.repository;

import com.peecko.admin.domain.Coach;
import com.peecko.admin.domain.Video;
import com.peecko.admin.domain.VideoCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findByVideoCategory(VideoCategory videoCategory, Pageable pageable);
    Page<Video> findByCoach(Coach coach, Pageable pageable);
    Page<Video> findByCoachAndVideoCategory(Coach coach, VideoCategory videoCategory, Pageable pageable);
}
