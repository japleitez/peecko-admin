package com.peecko.admin.service;

import com.peecko.admin.domain.ApsDevice;
import com.peecko.admin.repository.ApsDeviceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ApsDevice}.
 */
@Service
@Transactional
public class ApsDeviceService {

    private final Logger log = LoggerFactory.getLogger(ApsDeviceService.class);

    private final ApsDeviceRepository apsDeviceRepository;

    public ApsDeviceService(ApsDeviceRepository apsDeviceRepository) {
        this.apsDeviceRepository = apsDeviceRepository;
    }

    /**
     * Save a apsDevice.
     *
     * @param apsDevice the entity to save.
     * @return the persisted entity.
     */
    public ApsDevice save(ApsDevice apsDevice) {
        log.debug("Request to save ApsDevice : {}", apsDevice);
        return apsDeviceRepository.save(apsDevice);
    }

    /**
     * Update a apsDevice.
     *
     * @param apsDevice the entity to save.
     * @return the persisted entity.
     */
    public ApsDevice update(ApsDevice apsDevice) {
        log.debug("Request to update ApsDevice : {}", apsDevice);
        return apsDeviceRepository.save(apsDevice);
    }

    /**
     * Partially update a apsDevice.
     *
     * @param apsDevice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApsDevice> partialUpdate(ApsDevice apsDevice) {
        log.debug("Request to partially update ApsDevice : {}", apsDevice);

        return apsDeviceRepository
            .findById(apsDevice.getId())
            .map(existingApsDevice -> {
                if (apsDevice.getUsername() != null) {
                    existingApsDevice.setUsername(apsDevice.getUsername());
                }
                if (apsDevice.getDeviceId() != null) {
                    existingApsDevice.setDeviceId(apsDevice.getDeviceId());
                }
                if (apsDevice.getPhoneModel() != null) {
                    existingApsDevice.setPhoneModel(apsDevice.getPhoneModel());
                }
                if (apsDevice.getOsVersion() != null) {
                    existingApsDevice.setOsVersion(apsDevice.getOsVersion());
                }
                if (apsDevice.getInstalledOn() != null) {
                    existingApsDevice.setInstalledOn(apsDevice.getInstalledOn());
                }

                return existingApsDevice;
            })
            .map(apsDeviceRepository::save);
    }

    /**
     * Get all the apsDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsDevice> findAll(Pageable pageable) {
        log.debug("Request to get all ApsDevices");
        return apsDeviceRepository.findAll(pageable);
    }

    /**
     * Get one apsDevice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApsDevice> findOne(Long id) {
        log.debug("Request to get ApsDevice : {}", id);
        return apsDeviceRepository.findById(id);
    }

    /**
     * Delete the apsDevice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApsDevice : {}", id);
        apsDeviceRepository.deleteById(id);
    }
}
