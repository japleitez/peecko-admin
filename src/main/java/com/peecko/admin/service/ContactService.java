package com.peecko.admin.service;

import com.peecko.admin.domain.Contact;
import com.peecko.admin.domain.Customer;
import com.peecko.admin.repository.ContactRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.Contact}.
 */
@Service
@Transactional
public class ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Save a contact.
     *
     * @param contact the entity to save.
     * @return the persisted entity.
     */
    public Contact save(Contact contact) {
        log.debug("Request to save Contact : {}", contact);
        return contactRepository.save(contact);
    }

    /**
     * Update a contact.
     *
     * @param contact the entity to save.
     * @return the persisted entity.
     */
    public Contact update(Contact contact) {
        log.debug("Request to update Contact : {}", contact);
        return contactRepository.save(contact);
    }

    /**
     * Partially update a contact.
     *
     * @param contact the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Contact> partialUpdate(Contact contact) {
        log.debug("Request to partially update Contact : {}", contact);

        return contactRepository
            .findById(contact.getId())
            .map(existingContact -> {
                if (contact.getType() != null) {
                    existingContact.setType(contact.getType());
                }
                if (contact.getName() != null) {
                    existingContact.setName(contact.getName());
                }
                if (contact.getLine1() != null) {
                    existingContact.setLine1(contact.getLine1());
                }
                if (contact.getLine2() != null) {
                    existingContact.setLine2(contact.getLine2());
                }
                if (contact.getZip() != null) {
                    existingContact.setZip(contact.getZip());
                }
                if (contact.getCity() != null) {
                    existingContact.setCity(contact.getCity());
                }
                if (contact.getCountry() != null) {
                    existingContact.setCountry(contact.getCountry());
                }
                if (contact.getEmail() != null) {
                    existingContact.setEmail(contact.getEmail());
                }
                if (contact.getPhone() != null) {
                    existingContact.setPhone(contact.getPhone());
                }
                if (contact.getNotes() != null) {
                    existingContact.setNotes(contact.getNotes());
                }
                if (contact.getCreated() != null) {
                    existingContact.setCreated(contact.getCreated());
                }
                if (contact.getUpdated() != null) {
                    existingContact.setUpdated(contact.getUpdated());
                }

                return existingContact;
            })
            .map(contactRepository::save);
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Contact> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll(pageable);
    }

    /**
     * Get the contacts by customer.
     *
     * @param customerId the customer to filter by.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Contact> findByCustomer(Long customerId, Pageable pageable) {
        log.debug("Request to find Contacts by Customer");
        Customer customer = new Customer();
        customer.setId(customerId);
        return contactRepository.findByCustomer(customer, pageable);
    }

    /**
     * Get one contact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Contact> findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findById(id);
    }

    /**
     * Delete the contact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}
