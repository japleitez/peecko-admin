package com.peecko.admin.web.rest;

import com.peecko.admin.domain.SecuredRequest;
import com.peecko.admin.repository.SecuredRequestRepository;
import com.peecko.admin.service.SecuredRequestService;
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
 * REST controller for managing {@link com.peecko.admin.domain.SecuredRequest}.
 */
@RestController
@RequestMapping("/api/secured-requests")
public class SecuredRequestResource {

    private final Logger log = LoggerFactory.getLogger(SecuredRequestResource.class);

    private static final String ENTITY_NAME = "securedRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecuredRequestService securedRequestService;

    private final SecuredRequestRepository securedRequestRepository;

    public SecuredRequestResource(SecuredRequestService securedRequestService, SecuredRequestRepository securedRequestRepository) {
        this.securedRequestService = securedRequestService;
        this.securedRequestRepository = securedRequestRepository;
    }

    /**
     * {@code POST  /secured-requests} : Create a new securedRequest.
     *
     * @param securedRequest the securedRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securedRequest, or with status {@code 400 (Bad Request)} if the securedRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SecuredRequest> createSecuredRequest(@Valid @RequestBody SecuredRequest securedRequest)
        throws URISyntaxException {
        log.debug("REST request to save SecuredRequest : {}", securedRequest);
        if (securedRequest.getId() != null) {
            throw new BadRequestAlertException("A new securedRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecuredRequest result = securedRequestService.save(securedRequest);
        return ResponseEntity
            .created(new URI("/api/secured-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /secured-requests/:id} : Updates an existing securedRequest.
     *
     * @param id the id of the securedRequest to save.
     * @param securedRequest the securedRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securedRequest,
     * or with status {@code 400 (Bad Request)} if the securedRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securedRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SecuredRequest> updateSecuredRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SecuredRequest securedRequest
    ) throws URISyntaxException {
        log.debug("REST request to update SecuredRequest : {}, {}", id, securedRequest);
        if (securedRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securedRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securedRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SecuredRequest result = securedRequestService.update(securedRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, securedRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /secured-requests/:id} : Partial updates given fields of an existing securedRequest, field will ignore if it is null
     *
     * @param id the id of the securedRequest to save.
     * @param securedRequest the securedRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securedRequest,
     * or with status {@code 400 (Bad Request)} if the securedRequest is not valid,
     * or with status {@code 404 (Not Found)} if the securedRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the securedRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecuredRequest> partialUpdateSecuredRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SecuredRequest securedRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecuredRequest partially : {}, {}", id, securedRequest);
        if (securedRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securedRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securedRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecuredRequest> result = securedRequestService.partialUpdate(securedRequest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, securedRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /secured-requests} : get all the securedRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securedRequests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SecuredRequest>> getAllSecuredRequests(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SecuredRequests");
        Page<SecuredRequest> page = securedRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /secured-requests/:id} : get the "id" securedRequest.
     *
     * @param id the id of the securedRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securedRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SecuredRequest> getSecuredRequest(@PathVariable Long id) {
        log.debug("REST request to get SecuredRequest : {}", id);
        Optional<SecuredRequest> securedRequest = securedRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(securedRequest);
    }

    /**
     * {@code DELETE  /secured-requests/:id} : delete the "id" securedRequest.
     *
     * @param id the id of the securedRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecuredRequest(@PathVariable Long id) {
        log.debug("REST request to delete SecuredRequest : {}", id);
        securedRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
