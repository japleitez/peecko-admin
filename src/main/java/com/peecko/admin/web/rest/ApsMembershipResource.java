package com.peecko.admin.web.rest;

import com.peecko.admin.domain.ApsMembership;
import com.peecko.admin.repository.ApsMembershipRepository;
import com.peecko.admin.service.ApsMembershipService;
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
 * REST controller for managing {@link com.peecko.admin.domain.ApsMembership}.
 */
@RestController
@RequestMapping("/api/aps-memberships")
public class ApsMembershipResource {

    private final Logger log = LoggerFactory.getLogger(ApsMembershipResource.class);

    private static final String ENTITY_NAME = "apsMembership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApsMembershipService apsMembershipService;

    private final ApsMembershipRepository apsMembershipRepository;

    public ApsMembershipResource(ApsMembershipService apsMembershipService, ApsMembershipRepository apsMembershipRepository) {
        this.apsMembershipService = apsMembershipService;
        this.apsMembershipRepository = apsMembershipRepository;
    }

    /**
     * {@code POST  /aps-memberships} : Create a new apsMembership.
     *
     * @param apsMembership the apsMembership to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apsMembership, or with status {@code 400 (Bad Request)} if the apsMembership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApsMembership> createApsMembership(@Valid @RequestBody ApsMembership apsMembership) throws URISyntaxException {
        log.debug("REST request to save ApsMembership : {}", apsMembership);
        if (apsMembership.getId() != null) {
            throw new BadRequestAlertException("A new apsMembership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApsMembership result = apsMembershipService.save(apsMembership);
        return ResponseEntity
            .created(new URI("/api/aps-memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aps-memberships/:id} : Updates an existing apsMembership.
     *
     * @param id the id of the apsMembership to save.
     * @param apsMembership the apsMembership to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsMembership,
     * or with status {@code 400 (Bad Request)} if the apsMembership is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apsMembership couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApsMembership> updateApsMembership(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApsMembership apsMembership
    ) throws URISyntaxException {
        log.debug("REST request to update ApsMembership : {}, {}", id, apsMembership);
        if (apsMembership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsMembership.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsMembershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApsMembership result = apsMembershipService.update(apsMembership);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsMembership.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aps-memberships/:id} : Partial updates given fields of an existing apsMembership, field will ignore if it is null
     *
     * @param id the id of the apsMembership to save.
     * @param apsMembership the apsMembership to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsMembership,
     * or with status {@code 400 (Bad Request)} if the apsMembership is not valid,
     * or with status {@code 404 (Not Found)} if the apsMembership is not found,
     * or with status {@code 500 (Internal Server Error)} if the apsMembership couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApsMembership> partialUpdateApsMembership(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApsMembership apsMembership
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApsMembership partially : {}, {}", id, apsMembership);
        if (apsMembership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsMembership.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsMembershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApsMembership> result = apsMembershipService.partialUpdate(apsMembership);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsMembership.getId().toString())
        );
    }

    /**
     * {@code GET  /aps-memberships} : get all the apsMemberships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apsMemberships in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ApsMembership>> getAllApsMemberships(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ApsMemberships");
        Page<ApsMembership> page = apsMembershipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aps-memberships/:id} : get the "id" apsMembership.
     *
     * @param id the id of the apsMembership to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apsMembership, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApsMembership> getApsMembership(@PathVariable Long id) {
        log.debug("REST request to get ApsMembership : {}", id);
        Optional<ApsMembership> apsMembership = apsMembershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apsMembership);
    }

    /**
     * {@code DELETE  /aps-memberships/:id} : delete the "id" apsMembership.
     *
     * @param id the id of the apsMembership to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApsMembership(@PathVariable Long id) {
        log.debug("REST request to delete ApsMembership : {}", id);
        apsMembershipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
