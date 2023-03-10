package com.peecko.admin.service;

import com.peecko.admin.service.dto.MembershipDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.peecko.admin.domain.Membership}.
 */
public interface MembershipService {
    /**
     * Save a membership.
     *
     * @param membershipDTO the entity to save.
     * @return the persisted entity.
     */
    MembershipDTO save(MembershipDTO membershipDTO);

    /**
     * Updates a membership.
     *
     * @param membershipDTO the entity to update.
     * @return the persisted entity.
     */
    MembershipDTO update(MembershipDTO membershipDTO);

    /**
     * Partially updates a membership.
     *
     * @param membershipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MembershipDTO> partialUpdate(MembershipDTO membershipDTO);

    /**
     * Get all the memberships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MembershipDTO> findAll(Pageable pageable);

    /**
     * Get the "id" membership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MembershipDTO> findOne(Long id);

    /**
     * Delete the "id" membership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
