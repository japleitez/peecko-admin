package com.peecko.admin.service;

import com.peecko.admin.domain.ApsPricing;
import com.peecko.admin.repository.ApsPricingRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ApsPricing}.
 */
@Service
@Transactional
public class ApsPricingService {

    private final Logger log = LoggerFactory.getLogger(ApsPricingService.class);

    private final ApsPricingRepository apsPricingRepository;

    public ApsPricingService(ApsPricingRepository apsPricingRepository) {
        this.apsPricingRepository = apsPricingRepository;
    }

    /**
     * Save a apsPricing.
     *
     * @param apsPricing the entity to save.
     * @return the persisted entity.
     */
    public ApsPricing save(ApsPricing apsPricing) {
        log.debug("Request to save ApsPricing : {}", apsPricing);
        return apsPricingRepository.save(apsPricing);
    }

    /**
     * Update a apsPricing.
     *
     * @param apsPricing the entity to save.
     * @return the persisted entity.
     */
    public ApsPricing update(ApsPricing apsPricing) {
        log.debug("Request to update ApsPricing : {}", apsPricing);
        return apsPricingRepository.save(apsPricing);
    }

    /**
     * Partially update a apsPricing.
     *
     * @param apsPricing the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApsPricing> partialUpdate(ApsPricing apsPricing) {
        log.debug("Request to partially update ApsPricing : {}", apsPricing);

        return apsPricingRepository
            .findById(apsPricing.getId())
            .map(existingApsPricing -> {
                if (apsPricing.getCustomerId() != null) {
                    existingApsPricing.setCustomerId(apsPricing.getCustomerId());
                }
                if (apsPricing.getIndex() != null) {
                    existingApsPricing.setIndex(apsPricing.getIndex());
                }
                if (apsPricing.getMinQuantity() != null) {
                    existingApsPricing.setMinQuantity(apsPricing.getMinQuantity());
                }
                if (apsPricing.getUnitPrice() != null) {
                    existingApsPricing.setUnitPrice(apsPricing.getUnitPrice());
                }

                return existingApsPricing;
            })
            .map(apsPricingRepository::save);
    }

    /**
     * Get all the apsPricings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsPricing> findAll(Pageable pageable) {
        log.debug("Request to get all ApsPricings");
        return apsPricingRepository.findAll(pageable);
    }

    /**
     * Get one apsPricing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApsPricing> findOne(Long id) {
        log.debug("Request to get ApsPricing : {}", id);
        return apsPricingRepository.findById(id);
    }

    /**
     * Delete the apsPricing by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApsPricing : {}", id);
        apsPricingRepository.deleteById(id);
    }
}
