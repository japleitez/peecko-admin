package com.peecko.admin.web.rest;

import com.peecko.admin.domain.ApsAccount;
import com.peecko.admin.repository.ApsAccountRepository;
import com.peecko.admin.service.ApsAccountService;
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
 * REST controller for managing {@link com.peecko.admin.domain.ApsAccount}.
 */
@RestController
@RequestMapping("/api/aps-accounts")
public class ApsAccountResource {

    private final Logger log = LoggerFactory.getLogger(ApsAccountResource.class);

    private static final String ENTITY_NAME = "apsAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApsAccountService apsAccountService;

    private final ApsAccountRepository apsAccountRepository;

    public ApsAccountResource(ApsAccountService apsAccountService, ApsAccountRepository apsAccountRepository) {
        this.apsAccountService = apsAccountService;
        this.apsAccountRepository = apsAccountRepository;
    }

    /**
     * {@code POST  /aps-accounts} : Create a new apsAccount.
     *
     * @param apsAccount the apsAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apsAccount, or with status {@code 400 (Bad Request)} if the apsAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApsAccount> createApsAccount(@Valid @RequestBody ApsAccount apsAccount) throws URISyntaxException {
        log.debug("REST request to save ApsAccount : {}", apsAccount);
        if (apsAccount.getId() != null) {
            throw new BadRequestAlertException("A new apsAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApsAccount result = apsAccountService.save(apsAccount);
        return ResponseEntity
            .created(new URI("/api/aps-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aps-accounts/:id} : Updates an existing apsAccount.
     *
     * @param id the id of the apsAccount to save.
     * @param apsAccount the apsAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsAccount,
     * or with status {@code 400 (Bad Request)} if the apsAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apsAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApsAccount> updateApsAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApsAccount apsAccount
    ) throws URISyntaxException {
        log.debug("REST request to update ApsAccount : {}, {}", id, apsAccount);
        if (apsAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApsAccount result = apsAccountService.update(apsAccount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aps-accounts/:id} : Partial updates given fields of an existing apsAccount, field will ignore if it is null
     *
     * @param id the id of the apsAccount to save.
     * @param apsAccount the apsAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsAccount,
     * or with status {@code 400 (Bad Request)} if the apsAccount is not valid,
     * or with status {@code 404 (Not Found)} if the apsAccount is not found,
     * or with status {@code 500 (Internal Server Error)} if the apsAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApsAccount> partialUpdateApsAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApsAccount apsAccount
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApsAccount partially : {}, {}", id, apsAccount);
        if (apsAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApsAccount> result = apsAccountService.partialUpdate(apsAccount);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsAccount.getId().toString())
        );
    }

    /**
     * {@code GET  /aps-accounts} : get all the apsAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apsAccounts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ApsAccount>> getAllApsAccounts(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ApsAccounts");
        Page<ApsAccount> page = apsAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aps-accounts/:id} : get the "id" apsAccount.
     *
     * @param id the id of the apsAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apsAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApsAccount> getApsAccount(@PathVariable Long id) {
        log.debug("REST request to get ApsAccount : {}", id);
        Optional<ApsAccount> apsAccount = apsAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apsAccount);
    }

    /**
     * {@code DELETE  /aps-accounts/:id} : delete the "id" apsAccount.
     *
     * @param id the id of the apsAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApsAccount(@PathVariable Long id) {
        log.debug("REST request to delete ApsAccount : {}", id);
        apsAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
