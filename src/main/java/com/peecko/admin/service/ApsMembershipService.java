package com.peecko.admin.service;

import com.peecko.admin.domain.ApsMembership;
import com.peecko.admin.domain.ApsOrder;
import com.peecko.admin.repository.ApsMembershipRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ApsMembership}.
 */
@Service
@Transactional
public class ApsMembershipService {

    private final Logger log = LoggerFactory.getLogger(ApsMembershipService.class);

    private final ApsMembershipRepository apsMembershipRepository;

    public ApsMembershipService(ApsMembershipRepository apsMembershipRepository) {
        this.apsMembershipRepository = apsMembershipRepository;
    }

    /**
     * Save a apsMembership.
     *
     * @param apsMembership the entity to save.
     * @return the persisted entity.
     */
    public ApsMembership save(ApsMembership apsMembership) {
        log.debug("Request to save ApsMembership : {}", apsMembership);
        return apsMembershipRepository.save(apsMembership);
    }

    /**
     * Update a apsMembership.
     *
     * @param apsMembership the entity to save.
     * @return the persisted entity.
     */
    public ApsMembership update(ApsMembership apsMembership) {
        log.debug("Request to update ApsMembership : {}", apsMembership);
        return apsMembershipRepository.save(apsMembership);
    }

    /**
     * Partially update a apsMembership.
     *
     * @param apsMembership the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApsMembership> partialUpdate(ApsMembership apsMembership) {
        log.debug("Request to partially update ApsMembership : {}", apsMembership);

        return apsMembershipRepository
            .findById(apsMembership.getId())
            .map(existingApsMembership -> {
                if (apsMembership.getPeriod() != null) {
                    existingApsMembership.setPeriod(apsMembership.getPeriod());
                }
                if (apsMembership.getLicense() != null) {
                    existingApsMembership.setLicense(apsMembership.getLicense());
                }
                if (apsMembership.getUsername() != null) {
                    existingApsMembership.setUsername(apsMembership.getUsername());
                }

                return existingApsMembership;
            })
            .map(apsMembershipRepository::save);
    }

    /**
     * Get all the apsMemberships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsMembership> findAll(Pageable pageable) {
        log.debug("Request to get all ApsMemberships");
        return apsMembershipRepository.findAll(pageable);
    }

    /**
     * Get the apsMemberships by ApsOrder.
     *
     * @param apsOrderId the ApsOrder ID to filter by.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsMembership> findByApsOrder(Long apsOrderId, Pageable pageable) {
        ApsOrder apsOrder = new ApsOrder();
        apsOrder.setId(apsOrderId);
        return apsMembershipRepository.findByApsOrder(apsOrder, pageable);
    }

    /**
     * Get one apsMembership by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApsMembership> findOne(Long id) {
        log.debug("Request to get ApsMembership : {}", id);
        return apsMembershipRepository.findById(id);
    }

    /**
     * Delete the apsMembership by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApsMembership : {}", id);
        apsMembershipRepository.deleteById(id);
    }
}
