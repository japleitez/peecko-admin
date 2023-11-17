package com.peecko.admin.service;

import com.peecko.admin.domain.ApsOrder;
import com.peecko.admin.domain.ApsPlan;
import com.peecko.admin.repository.ApsOrderRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ApsOrder}.
 */
@Service
@Transactional
public class ApsOrderService {

    private final Logger log = LoggerFactory.getLogger(ApsOrderService.class);

    private final ApsOrderRepository apsOrderRepository;

    public ApsOrderService(ApsOrderRepository apsOrderRepository) {
        this.apsOrderRepository = apsOrderRepository;
    }

    /**
     * Save a apsOrder.
     *
     * @param apsOrder the entity to save.
     * @return the persisted entity.
     */
    public ApsOrder save(ApsOrder apsOrder) {
        log.debug("Request to save ApsOrder : {}", apsOrder);
        return apsOrderRepository.save(apsOrder);
    }

    /**
     * Update a apsOrder.
     *
     * @param apsOrder the entity to save.
     * @return the persisted entity.
     */
    public ApsOrder update(ApsOrder apsOrder) {
        log.debug("Request to update ApsOrder : {}", apsOrder);
        return apsOrderRepository.save(apsOrder);
    }

    /**
     * Partially update a apsOrder.
     *
     * @param apsOrder the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApsOrder> partialUpdate(ApsOrder apsOrder) {
        log.debug("Request to partially update ApsOrder : {}", apsOrder);

        return apsOrderRepository
            .findById(apsOrder.getId())
            .map(existingApsOrder -> {
                if (apsOrder.getContract() != null) {
                    existingApsOrder.setContract(apsOrder.getContract());
                }
                if (apsOrder.getPeriod() != null) {
                    existingApsOrder.setPeriod(apsOrder.getPeriod());
                }
                if (apsOrder.getLicense() != null) {
                    existingApsOrder.setLicense(apsOrder.getLicense());
                }
                if (apsOrder.getUnitPrice() != null) {
                    existingApsOrder.setUnitPrice(apsOrder.getUnitPrice());
                }
                if (apsOrder.getVatRate() != null) {
                    existingApsOrder.setVatRate(apsOrder.getVatRate());
                }
                if (apsOrder.getNumberOfUsers() != null) {
                    existingApsOrder.setNumberOfUsers(apsOrder.getNumberOfUsers());
                }
                if (apsOrder.getInvoiceNumber() != null) {
                    existingApsOrder.setInvoiceNumber(apsOrder.getInvoiceNumber());
                }

                return existingApsOrder;
            })
            .map(apsOrderRepository::save);
    }

    /**
     * Get all the apsOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsOrder> findAll(Pageable pageable) {
        log.debug("Request to get all ApsOrders");
        return apsOrderRepository.findAll(pageable);
    }

    /**
     * Get the apsOrders by apsPlan.
     *
     * @param apsPlanId the ApsPlan ID to filter by.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsOrder> findByApsPlan(Long apsPlanId, Pageable pageable) {
        log.debug("Request to get ApsOrders by ApsPlan");
        ApsPlan apsPlan = new ApsPlan();
        apsPlan.setId(apsPlanId);
        return apsOrderRepository.findByApsPlan(apsPlan, pageable);
    }

    /**
     * Get one apsOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApsOrder> findOne(Long id) {
        log.debug("Request to get ApsOrder : {}", id);
        return apsOrderRepository.findById(id);
    }

    /**
     * Delete the apsOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApsOrder : {}", id);
        apsOrderRepository.deleteById(id);
    }
}
