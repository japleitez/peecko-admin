package com.peecko.admin.service;

import com.peecko.admin.domain.VideoStat;
import com.peecko.admin.repository.VideoStatRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.VideoStat}.
 */
@Service
@Transactional
public class VideoStatService {

    private final Logger log = LoggerFactory.getLogger(VideoStatService.class);

    private final VideoStatRepository videoStatRepository;

    public VideoStatService(VideoStatRepository videoStatRepository) {
        this.videoStatRepository = videoStatRepository;
    }

    /**
     * Save a videoStat.
     *
     * @param videoStat the entity to save.
     * @return the persisted entity.
     */
    public VideoStat save(VideoStat videoStat) {
        log.debug("Request to save VideoStat : {}", videoStat);
        return videoStatRepository.save(videoStat);
    }

    /**
     * Update a videoStat.
     *
     * @param videoStat the entity to save.
     * @return the persisted entity.
     */
    public VideoStat update(VideoStat videoStat) {
        log.debug("Request to update VideoStat : {}", videoStat);
        return videoStatRepository.save(videoStat);
    }

    /**
     * Partially update a videoStat.
     *
     * @param videoStat the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VideoStat> partialUpdate(VideoStat videoStat) {
        log.debug("Request to partially update VideoStat : {}", videoStat);

        return videoStatRepository
            .findById(videoStat.getId())
            .map(existingVideoStat -> {
                if (videoStat.getVideoId() != null) {
                    existingVideoStat.setVideoId(videoStat.getVideoId());
                }
                if (videoStat.getCategoryId() != null) {
                    existingVideoStat.setCategoryId(videoStat.getCategoryId());
                }
                if (videoStat.getCoachId() != null) {
                    existingVideoStat.setCoachId(videoStat.getCoachId());
                }
                if (videoStat.getLiked() != null) {
                    existingVideoStat.setLiked(videoStat.getLiked());
                }
                if (videoStat.getViewed() != null) {
                    existingVideoStat.setViewed(videoStat.getViewed());
                }

                return existingVideoStat;
            })
            .map(videoStatRepository::save);
    }

    /**
     * Get all the videoStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VideoStat> findAll(Pageable pageable) {
        log.debug("Request to get all VideoStats");
        return videoStatRepository.findAll(pageable);
    }

    /**
     * Get one videoStat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VideoStat> findOne(Long id) {
        log.debug("Request to get VideoStat : {}", id);
        return videoStatRepository.findById(id);
    }

    /**
     * Delete the videoStat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VideoStat : {}", id);
        videoStatRepository.deleteById(id);
    }
}
