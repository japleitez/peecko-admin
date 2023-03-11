package com.peecko.admin.service;

import com.peecko.admin.domain.enumeration.ContactType;
import com.peecko.admin.service.dto.ContactDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.peecko.admin.domain.Contact}.
 */
public interface ContactService {
    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save.
     * @return the persisted entity.
     */
    ContactDTO save(ContactDTO contactDTO);

    /**
     * Updates a contact.
     *
     * @param contactDTO the entity to update.
     * @return the persisted entity.
     */
    ContactDTO update(ContactDTO contactDTO);

    /**
     * Partially updates a contact.
     *
     * @param contactDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactDTO> partialUpdate(ContactDTO contactDTO);

    /**
     * Get all the contacts.
     *
     * @return the list of entities.
     */
    List<ContactDTO> findAll();

    /**
     * Get the "id" contact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactDTO> findOne(Long id);

    /**
     * Delete the "id" contact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<ContactDTO> findByCompanyAndType(Long companyId, ContactType type);
}
