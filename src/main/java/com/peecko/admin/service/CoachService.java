package com.peecko.admin.service;

import com.peecko.admin.domain.Coach;
import com.peecko.admin.repository.CoachRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.Coach}.
 */
@Service
@Transactional
public class CoachService {

    private final Logger log = LoggerFactory.getLogger(CoachService.class);

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    /**
     * Save a coach.
     *
     * @param coach the entity to save.
     * @return the persisted entity.
     */
    public Coach save(Coach coach) {
        log.debug("Request to save Coach : {}", coach);
        return coachRepository.save(coach);
    }

    /**
     * Update a coach.
     *
     * @param coach the entity to save.
     * @return the persisted entity.
     */
    public Coach update(Coach coach) {
        log.debug("Request to update Coach : {}", coach);
        return coachRepository.save(coach);
    }

    /**
     * Partially update a coach.
     *
     * @param coach the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Coach> partialUpdate(Coach coach) {
        log.debug("Request to partially update Coach : {}", coach);

        return coachRepository
            .findById(coach.getId())
            .map(existingCoach -> {
                if (coach.getName() != null) {
                    existingCoach.setName(coach.getName());
                }
                if (coach.getEmail() != null) {
                    existingCoach.setEmail(coach.getEmail());
                }
                if (coach.getWebsite() != null) {
                    existingCoach.setWebsite(coach.getWebsite());
                }
                if (coach.getInstagram() != null) {
                    existingCoach.setInstagram(coach.getInstagram());
                }
                if (coach.getPhoneNumber() != null) {
                    existingCoach.setPhoneNumber(coach.getPhoneNumber());
                }
                if (coach.getCountry() != null) {
                    existingCoach.setCountry(coach.getCountry());
                }
                if (coach.getSpeaks() != null) {
                    existingCoach.setSpeaks(coach.getSpeaks());
                }
                if (coach.getResume() != null) {
                    existingCoach.setResume(coach.getResume());
                }
                if (coach.getNotes() != null) {
                    existingCoach.setNotes(coach.getNotes());
                }
                if (coach.getCreated() != null) {
                    existingCoach.setCreated(coach.getCreated());
                }
                if (coach.getUpdated() != null) {
                    existingCoach.setUpdated(coach.getUpdated());
                }

                return existingCoach;
            })
            .map(coachRepository::save);
    }

    /**
     * Get all the coaches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Coach> findAll(Pageable pageable) {
        log.debug("Request to get all Coaches");
        return coachRepository.findAll(pageable);
    }

    /**
     * Get one coach by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Coach> findOne(Long id) {
        log.debug("Request to get Coach : {}", id);
        return coachRepository.findById(id);
    }

    /**
     * Delete the coach by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Coach : {}", id);
        coachRepository.deleteById(id);
    }
}
