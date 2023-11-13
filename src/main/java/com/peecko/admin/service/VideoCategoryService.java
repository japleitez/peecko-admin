package com.peecko.admin.service;

import com.peecko.admin.domain.VideoCategory;
import com.peecko.admin.repository.VideoCategoryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.VideoCategory}.
 */
@Service
@Transactional
public class VideoCategoryService {

    private final Logger log = LoggerFactory.getLogger(VideoCategoryService.class);

    private final VideoCategoryRepository videoCategoryRepository;

    public VideoCategoryService(VideoCategoryRepository videoCategoryRepository) {
        this.videoCategoryRepository = videoCategoryRepository;
    }

    /**
     * Save a videoCategory.
     *
     * @param videoCategory the entity to save.
     * @return the persisted entity.
     */
    public VideoCategory save(VideoCategory videoCategory) {
        log.debug("Request to save VideoCategory : {}", videoCategory);
        return videoCategoryRepository.save(videoCategory);
    }

    /**
     * Update a videoCategory.
     *
     * @param videoCategory the entity to save.
     * @return the persisted entity.
     */
    public VideoCategory update(VideoCategory videoCategory) {
        log.debug("Request to update VideoCategory : {}", videoCategory);
        return videoCategoryRepository.save(videoCategory);
    }

    /**
     * Partially update a videoCategory.
     *
     * @param videoCategory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VideoCategory> partialUpdate(VideoCategory videoCategory) {
        log.debug("Request to partially update VideoCategory : {}", videoCategory);

        return videoCategoryRepository
            .findById(videoCategory.getId())
            .map(existingVideoCategory -> {
                if (videoCategory.getCode() != null) {
                    existingVideoCategory.setCode(videoCategory.getCode());
                }
                if (videoCategory.getTitle() != null) {
                    existingVideoCategory.setTitle(videoCategory.getTitle());
                }
                if (videoCategory.getCreated() != null) {
                    existingVideoCategory.setCreated(videoCategory.getCreated());
                }
                if (videoCategory.getReleased() != null) {
                    existingVideoCategory.setReleased(videoCategory.getReleased());
                }
                if (videoCategory.getArchived() != null) {
                    existingVideoCategory.setArchived(videoCategory.getArchived());
                }

                return existingVideoCategory;
            })
            .map(videoCategoryRepository::save);
    }

    /**
     * Get all the videoCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VideoCategory> findAll(Pageable pageable) {
        log.debug("Request to get all VideoCategories");
        return videoCategoryRepository.findAll(pageable);
    }

    /**
     * Get one videoCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VideoCategory> findOne(Long id) {
        log.debug("Request to get VideoCategory : {}", id);
        return videoCategoryRepository.findById(id);
    }

    /**
     * Delete the videoCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VideoCategory : {}", id);
        videoCategoryRepository.deleteById(id);
    }
}
