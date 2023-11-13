package com.peecko.admin.service;

import com.peecko.admin.domain.VideoItem;
import com.peecko.admin.repository.VideoItemRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.VideoItem}.
 */
@Service
@Transactional
public class VideoItemService {

    private final Logger log = LoggerFactory.getLogger(VideoItemService.class);

    private final VideoItemRepository videoItemRepository;

    public VideoItemService(VideoItemRepository videoItemRepository) {
        this.videoItemRepository = videoItemRepository;
    }

    /**
     * Save a videoItem.
     *
     * @param videoItem the entity to save.
     * @return the persisted entity.
     */
    public VideoItem save(VideoItem videoItem) {
        log.debug("Request to save VideoItem : {}", videoItem);
        return videoItemRepository.save(videoItem);
    }

    /**
     * Update a videoItem.
     *
     * @param videoItem the entity to save.
     * @return the persisted entity.
     */
    public VideoItem update(VideoItem videoItem) {
        log.debug("Request to update VideoItem : {}", videoItem);
        return videoItemRepository.save(videoItem);
    }

    /**
     * Partially update a videoItem.
     *
     * @param videoItem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VideoItem> partialUpdate(VideoItem videoItem) {
        log.debug("Request to partially update VideoItem : {}", videoItem);

        return videoItemRepository
            .findById(videoItem.getId())
            .map(existingVideoItem -> {
                if (videoItem.getPrevious() != null) {
                    existingVideoItem.setPrevious(videoItem.getPrevious());
                }
                if (videoItem.getCode() != null) {
                    existingVideoItem.setCode(videoItem.getCode());
                }
                if (videoItem.getNext() != null) {
                    existingVideoItem.setNext(videoItem.getNext());
                }

                return existingVideoItem;
            })
            .map(videoItemRepository::save);
    }

    /**
     * Get all the videoItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VideoItem> findAll(Pageable pageable) {
        log.debug("Request to get all VideoItems");
        return videoItemRepository.findAll(pageable);
    }

    /**
     * Get one videoItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VideoItem> findOne(Long id) {
        log.debug("Request to get VideoItem : {}", id);
        return videoItemRepository.findById(id);
    }

    /**
     * Delete the videoItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VideoItem : {}", id);
        videoItemRepository.deleteById(id);
    }
}
