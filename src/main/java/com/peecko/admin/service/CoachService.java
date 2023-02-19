package com.peecko.admin.service;

import com.peecko.admin.service.dto.CoachDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.peecko.admin.domain.Coach}.
 */
public interface CoachService {
    /**
     * Save a coach.
     *
     * @param coachDTO the entity to save.
     * @return the persisted entity.
     */
    CoachDTO save(CoachDTO coachDTO);

    /**
     * Updates a coach.
     *
     * @param coachDTO the entity to update.
     * @return the persisted entity.
     */
    CoachDTO update(CoachDTO coachDTO);

    /**
     * Partially updates a coach.
     *
     * @param coachDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CoachDTO> partialUpdate(CoachDTO coachDTO);

    /**
     * Get all the coaches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoachDTO> findAll(Pageable pageable);

    /**
     * Get the "id" coach.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoachDTO> findOne(Long id);

    /**
     * Delete the "id" coach.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
