package com.peecko.admin.service;

import com.peecko.admin.domain.Customer;
import com.peecko.admin.repository.CustomerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.Customer}.
 */
@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Update a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    public Customer update(Customer customer) {
        log.debug("Request to update Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Partially update a customer.
     *
     * @param customer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Customer> partialUpdate(Customer customer) {
        log.debug("Request to partially update Customer : {}", customer);

        return customerRepository
            .findById(customer.getId())
            .map(existingCustomer -> {
                if (customer.getCode() != null) {
                    existingCustomer.setCode(customer.getCode());
                }
                if (customer.getName() != null) {
                    existingCustomer.setName(customer.getName());
                }
                if (customer.getCountry() != null) {
                    existingCustomer.setCountry(customer.getCountry());
                }
                if (customer.getMembership() != null) {
                    existingCustomer.setMembership(customer.getMembership());
                }
                if (customer.getState() != null) {
                    existingCustomer.setState(customer.getState());
                }
                if (customer.getCloseReason() != null) {
                    existingCustomer.setCloseReason(customer.getCloseReason());
                }
                if (customer.getEmailDomains() != null) {
                    existingCustomer.setEmailDomains(customer.getEmailDomains());
                }
                if (customer.getVatId() != null) {
                    existingCustomer.setVatId(customer.getVatId());
                }
                if (customer.getBank() != null) {
                    existingCustomer.setBank(customer.getBank());
                }
                if (customer.getIban() != null) {
                    existingCustomer.setIban(customer.getIban());
                }
                if (customer.getLogo() != null) {
                    existingCustomer.setLogo(customer.getLogo());
                }
                if (customer.getNotes() != null) {
                    existingCustomer.setNotes(customer.getNotes());
                }
                if (customer.getCreated() != null) {
                    existingCustomer.setCreated(customer.getCreated());
                }
                if (customer.getUpdated() != null) {
                    existingCustomer.setUpdated(customer.getUpdated());
                }
                if (customer.getTrialed() != null) {
                    existingCustomer.setTrialed(customer.getTrialed());
                }
                if (customer.getDeclined() != null) {
                    existingCustomer.setDeclined(customer.getDeclined());
                }
                if (customer.getActivated() != null) {
                    existingCustomer.setActivated(customer.getActivated());
                }
                if (customer.getClosed() != null) {
                    existingCustomer.setClosed(customer.getClosed());
                }

                return existingCustomer;
            })
            .map(customerRepository::save);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }
}
