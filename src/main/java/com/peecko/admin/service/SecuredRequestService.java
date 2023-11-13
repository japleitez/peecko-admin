package com.peecko.admin.service;

import com.peecko.admin.domain.SecuredRequest;
import com.peecko.admin.repository.SecuredRequestRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.SecuredRequest}.
 */
@Service
@Transactional
public class SecuredRequestService {

    private final Logger log = LoggerFactory.getLogger(SecuredRequestService.class);

    private final SecuredRequestRepository securedRequestRepository;

    public SecuredRequestService(SecuredRequestRepository securedRequestRepository) {
        this.securedRequestRepository = securedRequestRepository;
    }

    /**
     * Save a securedRequest.
     *
     * @param securedRequest the entity to save.
     * @return the persisted entity.
     */
    public SecuredRequest save(SecuredRequest securedRequest) {
        log.debug("Request to save SecuredRequest : {}", securedRequest);
        return securedRequestRepository.save(securedRequest);
    }

    /**
     * Update a securedRequest.
     *
     * @param securedRequest the entity to save.
     * @return the persisted entity.
     */
    public SecuredRequest update(SecuredRequest securedRequest) {
        log.debug("Request to update SecuredRequest : {}", securedRequest);
        return securedRequestRepository.save(securedRequest);
    }

    /**
     * Partially update a securedRequest.
     *
     * @param securedRequest the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SecuredRequest> partialUpdate(SecuredRequest securedRequest) {
        log.debug("Request to partially update SecuredRequest : {}", securedRequest);

        return securedRequestRepository
            .findById(securedRequest.getId())
            .map(existingSecuredRequest -> {
                if (securedRequest.getRequestId() != null) {
                    existingSecuredRequest.setRequestId(securedRequest.getRequestId());
                }
                if (securedRequest.getPinCode() != null) {
                    existingSecuredRequest.setPinCode(securedRequest.getPinCode());
                }
                if (securedRequest.getEmail() != null) {
                    existingSecuredRequest.setEmail(securedRequest.getEmail());
                }
                if (securedRequest.getCreated() != null) {
                    existingSecuredRequest.setCreated(securedRequest.getCreated());
                }
                if (securedRequest.getExpires() != null) {
                    existingSecuredRequest.setExpires(securedRequest.getExpires());
                }

                return existingSecuredRequest;
            })
            .map(securedRequestRepository::save);
    }

    /**
     * Get all the securedRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SecuredRequest> findAll(Pageable pageable) {
        log.debug("Request to get all SecuredRequests");
        return securedRequestRepository.findAll(pageable);
    }

    /**
     * Get one securedRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SecuredRequest> findOne(Long id) {
        log.debug("Request to get SecuredRequest : {}", id);
        return securedRequestRepository.findById(id);
    }

    /**
     * Delete the securedRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecuredRequest : {}", id);
        securedRequestRepository.deleteById(id);
    }
}
