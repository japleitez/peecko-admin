package com.peecko.admin.web.rest;

import com.peecko.admin.domain.ApsPlan;
import com.peecko.admin.repository.ApsPlanRepository;
import com.peecko.admin.service.ApsPlanService;
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
 * REST controller for managing {@link com.peecko.admin.domain.ApsPlan}.
 */
@RestController
@RequestMapping("/api/aps-plans")
public class ApsPlanResource {

    private final Logger log = LoggerFactory.getLogger(ApsPlanResource.class);

    private static final String ENTITY_NAME = "apsPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApsPlanService apsPlanService;

    private final ApsPlanRepository apsPlanRepository;

    public ApsPlanResource(ApsPlanService apsPlanService, ApsPlanRepository apsPlanRepository) {
        this.apsPlanService = apsPlanService;
        this.apsPlanRepository = apsPlanRepository;
    }

    /**
     * {@code POST  /aps-plans} : Create a new apsPlan.
     *
     * @param apsPlan the apsPlan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apsPlan, or with status {@code 400 (Bad Request)} if the apsPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApsPlan> createApsPlan(@Valid @RequestBody ApsPlan apsPlan) throws URISyntaxException {
        log.debug("REST request to save ApsPlan : {}", apsPlan);
        if (apsPlan.getId() != null) {
            throw new BadRequestAlertException("A new apsPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApsPlan result = apsPlanService.save(apsPlan);
        return ResponseEntity
            .created(new URI("/api/aps-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aps-plans/:id} : Updates an existing apsPlan.
     *
     * @param id the id of the apsPlan to save.
     * @param apsPlan the apsPlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsPlan,
     * or with status {@code 400 (Bad Request)} if the apsPlan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apsPlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApsPlan> updateApsPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApsPlan apsPlan
    ) throws URISyntaxException {
        log.debug("REST request to update ApsPlan : {}, {}", id, apsPlan);
        if (apsPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsPlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApsPlan result = apsPlanService.update(apsPlan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsPlan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aps-plans/:id} : Partial updates given fields of an existing apsPlan, field will ignore if it is null
     *
     * @param id the id of the apsPlan to save.
     * @param apsPlan the apsPlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsPlan,
     * or with status {@code 400 (Bad Request)} if the apsPlan is not valid,
     * or with status {@code 404 (Not Found)} if the apsPlan is not found,
     * or with status {@code 500 (Internal Server Error)} if the apsPlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApsPlan> partialUpdateApsPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApsPlan apsPlan
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApsPlan partially : {}, {}", id, apsPlan);
        if (apsPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsPlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApsPlan> result = apsPlanService.partialUpdate(apsPlan);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsPlan.getId().toString())
        );
    }

    /**
     * {@code GET  /aps-plans} : get all the apsPlans.
     *
     * @param pageable the pagination information.
     * @param customerId the customer ID to filter by.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apsPlans in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ApsPlan>> getAllApsPlans(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "customerId", required = false) Long customerId
    ) {
        log.debug("REST request to get a page of ApsPlans");
        Page<ApsPlan> page;
        if (Objects.isNull(customerId)) {
            page = apsPlanService.findAll(pageable);
        } else {
            page = apsPlanService.findByCustomer(customerId, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aps-plans/:id} : get the "id" apsPlan.
     *
     * @param id the id of the apsPlan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apsPlan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApsPlan> getApsPlan(@PathVariable Long id) {
        log.debug("REST request to get ApsPlan : {}", id);
        Optional<ApsPlan> apsPlan = apsPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apsPlan);
    }

    /**
     * {@code DELETE  /aps-plans/:id} : delete the "id" apsPlan.
     *
     * @param id the id of the apsPlan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApsPlan(@PathVariable Long id) {
        log.debug("REST request to delete ApsPlan : {}", id);
        apsPlanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
