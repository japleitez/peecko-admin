package com.peecko.admin.service;

import com.peecko.admin.domain.Video;
import com.peecko.admin.repository.VideoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.Video}.
 */
@Service
@Transactional
public class VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoService.class);

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    /**
     * Save a video.
     *
     * @param video the entity to save.
     * @return the persisted entity.
     */
    public Video save(Video video) {
        log.debug("Request to save Video : {}", video);
        return videoRepository.save(video);
    }

    /**
     * Update a video.
     *
     * @param video the entity to save.
     * @return the persisted entity.
     */
    public Video update(Video video) {
        log.debug("Request to update Video : {}", video);
        return videoRepository.save(video);
    }

    /**
     * Partially update a video.
     *
     * @param video the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Video> partialUpdate(Video video) {
        log.debug("Request to partially update Video : {}", video);

        return videoRepository
            .findById(video.getId())
            .map(existingVideo -> {
                if (video.getCode() != null) {
                    existingVideo.setCode(video.getCode());
                }
                if (video.getTitle() != null) {
                    existingVideo.setTitle(video.getTitle());
                }
                if (video.getDuration() != null) {
                    existingVideo.setDuration(video.getDuration());
                }
                if (video.getLanguage() != null) {
                    existingVideo.setLanguage(video.getLanguage());
                }
                if (video.getPlayer() != null) {
                    existingVideo.setPlayer(video.getPlayer());
                }
                if (video.getThumbnail() != null) {
                    existingVideo.setThumbnail(video.getThumbnail());
                }
                if (video.getUrl() != null) {
                    existingVideo.setUrl(video.getUrl());
                }
                if (video.getAudience() != null) {
                    existingVideo.setAudience(video.getAudience());
                }
                if (video.getIntensity() != null) {
                    existingVideo.setIntensity(video.getIntensity());
                }
                if (video.getTags() != null) {
                    existingVideo.setTags(video.getTags());
                }
                if (video.getFilename() != null) {
                    existingVideo.setFilename(video.getFilename());
                }
                if (video.getDescription() != null) {
                    existingVideo.setDescription(video.getDescription());
                }
                if (video.getCreated() != null) {
                    existingVideo.setCreated(video.getCreated());
                }
                if (video.getReleased() != null) {
                    existingVideo.setReleased(video.getReleased());
                }
                if (video.getArchived() != null) {
                    existingVideo.setArchived(video.getArchived());
                }

                return existingVideo;
            })
            .map(videoRepository::save);
    }

    /**
     * Get all the videos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Video> findAll(Pageable pageable) {
        log.debug("Request to get all Videos");
        return videoRepository.findAll(pageable);
    }

    /**
     * Get one video by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Video> findOne(Long id) {
        log.debug("Request to get Video : {}", id);
        return videoRepository.findById(id);
    }

    /**
     * Delete the video by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Video : {}", id);
        videoRepository.deleteById(id);
    }
}
