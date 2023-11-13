package com.peecko.admin.web.rest;

import com.peecko.admin.domain.LikedVideo;
import com.peecko.admin.repository.LikedVideoRepository;
import com.peecko.admin.service.LikedVideoService;
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
 * REST controller for managing {@link com.peecko.admin.domain.LikedVideo}.
 */
@RestController
@RequestMapping("/api/liked-videos")
public class LikedVideoResource {

    private final Logger log = LoggerFactory.getLogger(LikedVideoResource.class);

    private static final String ENTITY_NAME = "likedVideo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikedVideoService likedVideoService;

    private final LikedVideoRepository likedVideoRepository;

    public LikedVideoResource(LikedVideoService likedVideoService, LikedVideoRepository likedVideoRepository) {
        this.likedVideoService = likedVideoService;
        this.likedVideoRepository = likedVideoRepository;
    }

    /**
     * {@code POST  /liked-videos} : Create a new likedVideo.
     *
     * @param likedVideo the likedVideo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likedVideo, or with status {@code 400 (Bad Request)} if the likedVideo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LikedVideo> createLikedVideo(@Valid @RequestBody LikedVideo likedVideo) throws URISyntaxException {
        log.debug("REST request to save LikedVideo : {}", likedVideo);
        if (likedVideo.getId() != null) {
            throw new BadRequestAlertException("A new likedVideo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikedVideo result = likedVideoService.save(likedVideo);
        return ResponseEntity
            .created(new URI("/api/liked-videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /liked-videos/:id} : Updates an existing likedVideo.
     *
     * @param id the id of the likedVideo to save.
     * @param likedVideo the likedVideo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likedVideo,
     * or with status {@code 400 (Bad Request)} if the likedVideo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likedVideo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LikedVideo> updateLikedVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LikedVideo likedVideo
    ) throws URISyntaxException {
        log.debug("REST request to update LikedVideo : {}, {}", id, likedVideo);
        if (likedVideo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, likedVideo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likedVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LikedVideo result = likedVideoService.update(likedVideo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, likedVideo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /liked-videos/:id} : Partial updates given fields of an existing likedVideo, field will ignore if it is null
     *
     * @param id the id of the likedVideo to save.
     * @param likedVideo the likedVideo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likedVideo,
     * or with status {@code 400 (Bad Request)} if the likedVideo is not valid,
     * or with status {@code 404 (Not Found)} if the likedVideo is not found,
     * or with status {@code 500 (Internal Server Error)} if the likedVideo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LikedVideo> partialUpdateLikedVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LikedVideo likedVideo
    ) throws URISyntaxException {
        log.debug("REST request to partial update LikedVideo partially : {}, {}", id, likedVideo);
        if (likedVideo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, likedVideo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likedVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LikedVideo> result = likedVideoService.partialUpdate(likedVideo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, likedVideo.getId().toString())
        );
    }

    /**
     * {@code GET  /liked-videos} : get all the likedVideos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likedVideos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LikedVideo>> getAllLikedVideos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LikedVideos");
        Page<LikedVideo> page = likedVideoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /liked-videos/:id} : get the "id" likedVideo.
     *
     * @param id the id of the likedVideo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likedVideo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LikedVideo> getLikedVideo(@PathVariable Long id) {
        log.debug("REST request to get LikedVideo : {}", id);
        Optional<LikedVideo> likedVideo = likedVideoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likedVideo);
    }

    /**
     * {@code DELETE  /liked-videos/:id} : delete the "id" likedVideo.
     *
     * @param id the id of the likedVideo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLikedVideo(@PathVariable Long id) {
        log.debug("REST request to delete LikedVideo : {}", id);
        likedVideoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
