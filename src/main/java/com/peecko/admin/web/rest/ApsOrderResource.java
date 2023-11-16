package com.peecko.admin.web.rest;

import com.peecko.admin.domain.ApsOrder;
import com.peecko.admin.repository.ApsOrderRepository;
import com.peecko.admin.service.ApsOrderService;
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
 * REST controller for managing {@link com.peecko.admin.domain.ApsOrder}.
 */
@RestController
@RequestMapping("/api/aps-orders")
public class ApsOrderResource {

    private final Logger log = LoggerFactory.getLogger(ApsOrderResource.class);

    private static final String ENTITY_NAME = "apsOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApsOrderService apsOrderService;

    private final ApsOrderRepository apsOrderRepository;

    public ApsOrderResource(ApsOrderService apsOrderService, ApsOrderRepository apsOrderRepository) {
        this.apsOrderService = apsOrderService;
        this.apsOrderRepository = apsOrderRepository;
    }

    /**
     * {@code POST  /aps-orders} : Create a new apsOrder.
     *
     * @param apsOrder the apsOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apsOrder, or with status {@code 400 (Bad Request)} if the apsOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApsOrder> createApsOrder(@Valid @RequestBody ApsOrder apsOrder) throws URISyntaxException {
        log.debug("REST request to save ApsOrder : {}", apsOrder);
        if (apsOrder.getId() != null) {
            throw new BadRequestAlertException("A new apsOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApsOrder result = apsOrderService.save(apsOrder);
        return ResponseEntity
            .created(new URI("/api/aps-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aps-orders/:id} : Updates an existing apsOrder.
     *
     * @param id the id of the apsOrder to save.
     * @param apsOrder the apsOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsOrder,
     * or with status {@code 400 (Bad Request)} if the apsOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apsOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApsOrder> updateApsOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApsOrder apsOrder
    ) throws URISyntaxException {
        log.debug("REST request to update ApsOrder : {}, {}", id, apsOrder);
        if (apsOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApsOrder result = apsOrderService.update(apsOrder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aps-orders/:id} : Partial updates given fields of an existing apsOrder, field will ignore if it is null
     *
     * @param id the id of the apsOrder to save.
     * @param apsOrder the apsOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsOrder,
     * or with status {@code 400 (Bad Request)} if the apsOrder is not valid,
     * or with status {@code 404 (Not Found)} if the apsOrder is not found,
     * or with status {@code 500 (Internal Server Error)} if the apsOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApsOrder> partialUpdateApsOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApsOrder apsOrder
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApsOrder partially : {}, {}", id, apsOrder);
        if (apsOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApsOrder> result = apsOrderService.partialUpdate(apsOrder);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsOrder.getId().toString())
        );
    }

    /**
     * {@code GET  /aps-orders} : get all the apsOrders.
     *
     * @param apsPlanId the ApsPlan ID to filter by.
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apsOrders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ApsOrder>> getAllApsOrders(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                          @RequestParam(name = "apsPlanId", required = false) Long apsPlanId) {
        log.debug("REST request to get a page of ApsOrders");
        Page<ApsOrder> page;
        if (Objects.isNull(apsPlanId)) {
            page = apsOrderService.findAll(pageable);
        } else {
            page = apsOrderService.findByApsPlan(apsPlanId, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aps-orders/:id} : get the "id" apsOrder.
     *
     * @param id the id of the apsOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apsOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApsOrder> getApsOrder(@PathVariable Long id) {
        log.debug("REST request to get ApsOrder : {}", id);
        Optional<ApsOrder> apsOrder = apsOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apsOrder);
    }

    /**
     * {@code DELETE  /aps-orders/:id} : delete the "id" apsOrder.
     *
     * @param id the id of the apsOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApsOrder(@PathVariable Long id) {
        log.debug("REST request to delete ApsOrder : {}", id);
        apsOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
