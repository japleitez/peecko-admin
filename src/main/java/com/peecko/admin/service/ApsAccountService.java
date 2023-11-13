package com.peecko.admin.service;

import com.peecko.admin.domain.ApsAccount;
import com.peecko.admin.repository.ApsAccountRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ApsAccount}.
 */
@Service
@Transactional
public class ApsAccountService {

    private final Logger log = LoggerFactory.getLogger(ApsAccountService.class);

    private final ApsAccountRepository apsAccountRepository;

    public ApsAccountService(ApsAccountRepository apsAccountRepository) {
        this.apsAccountRepository = apsAccountRepository;
    }

    /**
     * Save a apsAccount.
     *
     * @param apsAccount the entity to save.
     * @return the persisted entity.
     */
    public ApsAccount save(ApsAccount apsAccount) {
        log.debug("Request to save ApsAccount : {}", apsAccount);
        return apsAccountRepository.save(apsAccount);
    }

    /**
     * Update a apsAccount.
     *
     * @param apsAccount the entity to save.
     * @return the persisted entity.
     */
    public ApsAccount update(ApsAccount apsAccount) {
        log.debug("Request to update ApsAccount : {}", apsAccount);
        return apsAccountRepository.save(apsAccount);
    }

    /**
     * Partially update a apsAccount.
     *
     * @param apsAccount the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApsAccount> partialUpdate(ApsAccount apsAccount) {
        log.debug("Request to partially update ApsAccount : {}", apsAccount);

        return apsAccountRepository
            .findById(apsAccount.getId())
            .map(existingApsAccount -> {
                if (apsAccount.getName() != null) {
                    existingApsAccount.setName(apsAccount.getName());
                }
                if (apsAccount.getUsername() != null) {
                    existingApsAccount.setUsername(apsAccount.getUsername());
                }
                if (apsAccount.getVerified() != null) {
                    existingApsAccount.setVerified(apsAccount.getVerified());
                }
                if (apsAccount.getLanguage() != null) {
                    existingApsAccount.setLanguage(apsAccount.getLanguage());
                }
                if (apsAccount.getEmail() != null) {
                    existingApsAccount.setEmail(apsAccount.getEmail());
                }
                if (apsAccount.getEmailVerified() != null) {
                    existingApsAccount.setEmailVerified(apsAccount.getEmailVerified());
                }
                if (apsAccount.getLicense() != null) {
                    existingApsAccount.setLicense(apsAccount.getLicense());
                }
                if (apsAccount.getActive() != null) {
                    existingApsAccount.setActive(apsAccount.getActive());
                }
                if (apsAccount.getPassword() != null) {
                    existingApsAccount.setPassword(apsAccount.getPassword());
                }
                if (apsAccount.getCreated() != null) {
                    existingApsAccount.setCreated(apsAccount.getCreated());
                }
                if (apsAccount.getUpdated() != null) {
                    existingApsAccount.setUpdated(apsAccount.getUpdated());
                }

                return existingApsAccount;
            })
            .map(apsAccountRepository::save);
    }

    /**
     * Get all the apsAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsAccount> findAll(Pageable pageable) {
        log.debug("Request to get all ApsAccounts");
        return apsAccountRepository.findAll(pageable);
    }

    /**
     * Get one apsAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApsAccount> findOne(Long id) {
        log.debug("Request to get ApsAccount : {}", id);
        return apsAccountRepository.findById(id);
    }

    /**
     * Delete the apsAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApsAccount : {}", id);
        apsAccountRepository.deleteById(id);
    }
}
