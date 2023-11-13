package com.peecko.admin.service;

import com.peecko.admin.domain.LikedVideo;
import com.peecko.admin.repository.LikedVideoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.LikedVideo}.
 */
@Service
@Transactional
public class LikedVideoService {

    private final Logger log = LoggerFactory.getLogger(LikedVideoService.class);

    private final LikedVideoRepository likedVideoRepository;

    public LikedVideoService(LikedVideoRepository likedVideoRepository) {
        this.likedVideoRepository = likedVideoRepository;
    }

    /**
     * Save a likedVideo.
     *
     * @param likedVideo the entity to save.
     * @return the persisted entity.
     */
    public LikedVideo save(LikedVideo likedVideo) {
        log.debug("Request to save LikedVideo : {}", likedVideo);
        return likedVideoRepository.save(likedVideo);
    }

    /**
     * Update a likedVideo.
     *
     * @param likedVideo the entity to save.
     * @return the persisted entity.
     */
    public LikedVideo update(LikedVideo likedVideo) {
        log.debug("Request to update LikedVideo : {}", likedVideo);
        return likedVideoRepository.save(likedVideo);
    }

    /**
     * Partially update a likedVideo.
     *
     * @param likedVideo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LikedVideo> partialUpdate(LikedVideo likedVideo) {
        log.debug("Request to partially update LikedVideo : {}", likedVideo);

        return likedVideoRepository
            .findById(likedVideo.getId())
            .map(existingLikedVideo -> {
                if (likedVideo.getVideoId() != null) {
                    existingLikedVideo.setVideoId(likedVideo.getVideoId());
                }
                if (likedVideo.getPersonId() != null) {
                    existingLikedVideo.setPersonId(likedVideo.getPersonId());
                }
                if (likedVideo.getCoachId() != null) {
                    existingLikedVideo.setCoachId(likedVideo.getCoachId());
                }
                if (likedVideo.getCreated() != null) {
                    existingLikedVideo.setCreated(likedVideo.getCreated());
                }

                return existingLikedVideo;
            })
            .map(likedVideoRepository::save);
    }

    /**
     * Get all the likedVideos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LikedVideo> findAll(Pageable pageable) {
        log.debug("Request to get all LikedVideos");
        return likedVideoRepository.findAll(pageable);
    }

    /**
     * Get one likedVideo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LikedVideo> findOne(Long id) {
        log.debug("Request to get LikedVideo : {}", id);
        return likedVideoRepository.findById(id);
    }

    /**
     * Delete the likedVideo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LikedVideo : {}", id);
        likedVideoRepository.deleteById(id);
    }
}
