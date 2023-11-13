package com.peecko.admin.service;

import com.peecko.admin.domain.ApsPlan;
import com.peecko.admin.repository.ApsPlanRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ApsPlan}.
 */
@Service
@Transactional
public class ApsPlanService {

    private final Logger log = LoggerFactory.getLogger(ApsPlanService.class);

    private final ApsPlanRepository apsPlanRepository;

    public ApsPlanService(ApsPlanRepository apsPlanRepository) {
        this.apsPlanRepository = apsPlanRepository;
    }

    /**
     * Save a apsPlan.
     *
     * @param apsPlan the entity to save.
     * @return the persisted entity.
     */
    public ApsPlan save(ApsPlan apsPlan) {
        log.debug("Request to save ApsPlan : {}", apsPlan);
        return apsPlanRepository.save(apsPlan);
    }

    /**
     * Update a apsPlan.
     *
     * @param apsPlan the entity to save.
     * @return the persisted entity.
     */
    public ApsPlan update(ApsPlan apsPlan) {
        log.debug("Request to update ApsPlan : {}", apsPlan);
        return apsPlanRepository.save(apsPlan);
    }

    /**
     * Partially update a apsPlan.
     *
     * @param apsPlan the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApsPlan> partialUpdate(ApsPlan apsPlan) {
        log.debug("Request to partially update ApsPlan : {}", apsPlan);

        return apsPlanRepository
            .findById(apsPlan.getId())
            .map(existingApsPlan -> {
                if (apsPlan.getContract() != null) {
                    existingApsPlan.setContract(apsPlan.getContract());
                }
                if (apsPlan.getPricing() != null) {
                    existingApsPlan.setPricing(apsPlan.getPricing());
                }
                if (apsPlan.getState() != null) {
                    existingApsPlan.setState(apsPlan.getState());
                }
                if (apsPlan.getLicense() != null) {
                    existingApsPlan.setLicense(apsPlan.getLicense());
                }
                if (apsPlan.getStarts() != null) {
                    existingApsPlan.setStarts(apsPlan.getStarts());
                }
                if (apsPlan.getEnds() != null) {
                    existingApsPlan.setEnds(apsPlan.getEnds());
                }
                if (apsPlan.getTrialStarts() != null) {
                    existingApsPlan.setTrialStarts(apsPlan.getTrialStarts());
                }
                if (apsPlan.getTrialEnds() != null) {
                    existingApsPlan.setTrialEnds(apsPlan.getTrialEnds());
                }
                if (apsPlan.getUnitPrice() != null) {
                    existingApsPlan.setUnitPrice(apsPlan.getUnitPrice());
                }
                if (apsPlan.getNotes() != null) {
                    existingApsPlan.setNotes(apsPlan.getNotes());
                }
                if (apsPlan.getCreated() != null) {
                    existingApsPlan.setCreated(apsPlan.getCreated());
                }
                if (apsPlan.getUpdated() != null) {
                    existingApsPlan.setUpdated(apsPlan.getUpdated());
                }

                return existingApsPlan;
            })
            .map(apsPlanRepository::save);
    }

    /**
     * Get all the apsPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsPlan> findAll(Pageable pageable) {
        log.debug("Request to get all ApsPlans");
        return apsPlanRepository.findAll(pageable);
    }

    /**
     * Get one apsPlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApsPlan> findOne(Long id) {
        log.debug("Request to get ApsPlan : {}", id);
        return apsPlanRepository.findById(id);
    }

    /**
     * Delete the apsPlan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApsPlan : {}", id);
        apsPlanRepository.deleteById(id);
    }
}
