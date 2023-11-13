package com.peecko.admin.web.rest;

import com.peecko.admin.domain.VideoStat;
import com.peecko.admin.repository.VideoStatRepository;
import com.peecko.admin.service.VideoStatService;
import com.peecko.admin.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.peecko.admin.domain.VideoStat}.
 */
@RestController
@RequestMapping("/api/video-stats")
public class VideoStatResource {

    private final Logger log = LoggerFactory.getLogger(VideoStatResource.class);

    private static final String ENTITY_NAME = "videoStat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoStatService videoStatService;

    private final VideoStatRepository videoStatRepository;

    public VideoStatResource(VideoStatService videoStatService, VideoStatRepository videoStatRepository) {
        this.videoStatService = videoStatService;
        this.videoStatRepository = videoStatRepository;
    }

    /**
     * {@code POST  /video-stats} : Create a new videoStat.
     *
     * @param videoStat the videoStat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videoStat, or with status {@code 400 (Bad Request)} if the videoStat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VideoStat> createVideoStat(@Valid @RequestBody VideoStat videoStat) throws URISyntaxException {
        log.debug("REST request to save VideoStat : {}", videoStat);
        if (videoStat.getId() != null) {
            throw new BadRequestAlertException("A new videoStat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoStat result = videoStatService.save(videoStat);
        return ResponseEntity
            .created(new URI("/api/video-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /video-stats/:id} : Updates an existing videoStat.
     *
     * @param id the id of the videoStat to save.
     * @param videoStat the videoStat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoStat,
     * or with status {@code 400 (Bad Request)} if the videoStat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videoStat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VideoStat> updateVideoStat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VideoStat videoStat
    ) throws URISyntaxException {
        log.debug("REST request to update VideoStat : {}, {}", id, videoStat);
        if (videoStat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videoStat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoStatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VideoStat result = videoStatService.update(videoStat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, videoStat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /video-stats/:id} : Partial updates given fields of an existing videoStat, field will ignore if it is null
     *
     * @param id the id of the videoStat to save.
     * @param videoStat the videoStat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoStat,
     * or with status {@code 400 (Bad Request)} if the videoStat is not valid,
     * or with status {@code 404 (Not Found)} if the videoStat is not found,
     * or with status {@code 500 (Internal Server Error)} if the videoStat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VideoStat> partialUpdateVideoStat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VideoStat videoStat
    ) throws URISyntaxException {
        log.debug("REST request to partial update VideoStat partially : {}, {}", id, videoStat);
        if (videoStat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videoStat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoStatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VideoStat> result = videoStatService.partialUpdate(videoStat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, videoStat.getId().toString())
        );
    }

    /**
     * {@code GET  /video-stats} : get all the videoStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videoStats in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VideoStat>> getAllVideoStats(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of VideoStats");
        Page<VideoStat> page = videoStatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /video-stats/:id} : get the "id" videoStat.
     *
     * @param id the id of the videoStat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoStat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VideoStat> getVideoStat(@PathVariable Long id) {
        log.debug("REST request to get VideoStat : {}", id);
        Optional<VideoStat> videoStat = videoStatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoStat);
    }

    /**
     * {@code DELETE  /video-stats/:id} : delete the "id" videoStat.
     *
     * @param id the id of the videoStat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoStat(@PathVariable Long id) {
        log.debug("REST request to delete VideoStat : {}", id);
        videoStatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
