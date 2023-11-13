package com.peecko.admin.service;

import com.peecko.admin.domain.Agency;
import com.peecko.admin.repository.AgencyRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.Agency}.
 */
@Service
@Transactional
public class AgencyService {

    private final Logger log = LoggerFactory.getLogger(AgencyService.class);

    private final AgencyRepository agencyRepository;

    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    /**
     * Save a agency.
     *
     * @param agency the entity to save.
     * @return the persisted entity.
     */
    public Agency save(Agency agency) {
        log.debug("Request to save Agency : {}", agency);
        return agencyRepository.save(agency);
    }

    /**
     * Update a agency.
     *
     * @param agency the entity to save.
     * @return the persisted entity.
     */
    public Agency update(Agency agency) {
        log.debug("Request to update Agency : {}", agency);
        return agencyRepository.save(agency);
    }

    /**
     * Partially update a agency.
     *
     * @param agency the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Agency> partialUpdate(Agency agency) {
        log.debug("Request to partially update Agency : {}", agency);

        return agencyRepository
            .findById(agency.getId())
            .map(existingAgency -> {
                if (agency.getCode() != null) {
                    existingAgency.setCode(agency.getCode());
                }
                if (agency.getName() != null) {
                    existingAgency.setName(agency.getName());
                }
                if (agency.getLine1() != null) {
                    existingAgency.setLine1(agency.getLine1());
                }
                if (agency.getLine2() != null) {
                    existingAgency.setLine2(agency.getLine2());
                }
                if (agency.getZip() != null) {
                    existingAgency.setZip(agency.getZip());
                }
                if (agency.getCity() != null) {
                    existingAgency.setCity(agency.getCity());
                }
                if (agency.getCountry() != null) {
                    existingAgency.setCountry(agency.getCountry());
                }
                if (agency.getLanguage() != null) {
                    existingAgency.setLanguage(agency.getLanguage());
                }
                if (agency.getEmail() != null) {
                    existingAgency.setEmail(agency.getEmail());
                }
                if (agency.getPhone() != null) {
                    existingAgency.setPhone(agency.getPhone());
                }
                if (agency.getBillingEmail() != null) {
                    existingAgency.setBillingEmail(agency.getBillingEmail());
                }
                if (agency.getBillingPhone() != null) {
                    existingAgency.setBillingPhone(agency.getBillingPhone());
                }
                if (agency.getBank() != null) {
                    existingAgency.setBank(agency.getBank());
                }
                if (agency.getIban() != null) {
                    existingAgency.setIban(agency.getIban());
                }
                if (agency.getRcs() != null) {
                    existingAgency.setRcs(agency.getRcs());
                }
                if (agency.getVatId() != null) {
                    existingAgency.setVatId(agency.getVatId());
                }
                if (agency.getVatRate() != null) {
                    existingAgency.setVatRate(agency.getVatRate());
                }
                if (agency.getNotes() != null) {
                    existingAgency.setNotes(agency.getNotes());
                }
                if (agency.getCreated() != null) {
                    existingAgency.setCreated(agency.getCreated());
                }
                if (agency.getUpdated() != null) {
                    existingAgency.setUpdated(agency.getUpdated());
                }

                return existingAgency;
            })
            .map(agencyRepository::save);
    }

    /**
     * Get all the agencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Agency> findAll(Pageable pageable) {
        log.debug("Request to get all Agencies");
        return agencyRepository.findAll(pageable);
    }

    /**
     * Get one agency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Agency> findOne(Long id) {
        log.debug("Request to get Agency : {}", id);
        return agencyRepository.findById(id);
    }

    /**
     * Delete the agency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Agency : {}", id);
        agencyRepository.deleteById(id);
    }
}
