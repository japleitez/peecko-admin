package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.SecuredRequest;
import com.peecko.admin.repository.SecuredRequestRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SecuredRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SecuredRequestResourceIT {

    private static final String DEFAULT_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/secured-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SecuredRequestRepository securedRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecuredRequestMockMvc;

    private SecuredRequest securedRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecuredRequest createEntity(EntityManager em) {
        SecuredRequest securedRequest = new SecuredRequest()
            .requestId(DEFAULT_REQUEST_ID)
            .pinCode(DEFAULT_PIN_CODE)
            .email(DEFAULT_EMAIL)
            .created(DEFAULT_CREATED)
            .expires(DEFAULT_EXPIRES);
        return securedRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecuredRequest createUpdatedEntity(EntityManager em) {
        SecuredRequest securedRequest = new SecuredRequest()
            .requestId(UPDATED_REQUEST_ID)
            .pinCode(UPDATED_PIN_CODE)
            .email(UPDATED_EMAIL)
            .created(UPDATED_CREATED)
            .expires(UPDATED_EXPIRES);
        return securedRequest;
    }

    @BeforeEach
    public void initTest() {
        securedRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createSecuredRequest() throws Exception {
        int databaseSizeBeforeCreate = securedRequestRepository.findAll().size();
        // Create the SecuredRequest
        restSecuredRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isCreated());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeCreate + 1);
        SecuredRequest testSecuredRequest = securedRequestList.get(securedRequestList.size() - 1);
        assertThat(testSecuredRequest.getRequestId()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testSecuredRequest.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testSecuredRequest.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSecuredRequest.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testSecuredRequest.getExpires()).isEqualTo(DEFAULT_EXPIRES);
    }

    @Test
    @Transactional
    void createSecuredRequestWithExistingId() throws Exception {
        // Create the SecuredRequest with an existing ID
        securedRequest.setId(1L);

        int databaseSizeBeforeCreate = securedRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecuredRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = securedRequestRepository.findAll().size();
        // set the field null
        securedRequest.setRequestId(null);

        // Create the SecuredRequest, which fails.

        restSecuredRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPinCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = securedRequestRepository.findAll().size();
        // set the field null
        securedRequest.setPinCode(null);

        // Create the SecuredRequest, which fails.

        restSecuredRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = securedRequestRepository.findAll().size();
        // set the field null
        securedRequest.setEmail(null);

        // Create the SecuredRequest, which fails.

        restSecuredRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = securedRequestRepository.findAll().size();
        // set the field null
        securedRequest.setCreated(null);

        // Create the SecuredRequest, which fails.

        restSecuredRequestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSecuredRequests() throws Exception {
        // Initialize the database
        securedRequestRepository.saveAndFlush(securedRequest);

        // Get all the securedRequestList
        restSecuredRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(securedRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].expires").value(hasItem(DEFAULT_EXPIRES.toString())));
    }

    @Test
    @Transactional
    void getSecuredRequest() throws Exception {
        // Initialize the database
        securedRequestRepository.saveAndFlush(securedRequest);

        // Get the securedRequest
        restSecuredRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, securedRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(securedRequest.getId().intValue()))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.expires").value(DEFAULT_EXPIRES.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSecuredRequest() throws Exception {
        // Get the securedRequest
        restSecuredRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSecuredRequest() throws Exception {
        // Initialize the database
        securedRequestRepository.saveAndFlush(securedRequest);

        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();

        // Update the securedRequest
        SecuredRequest updatedSecuredRequest = securedRequestRepository.findById(securedRequest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSecuredRequest are not directly saved in db
        em.detach(updatedSecuredRequest);
        updatedSecuredRequest
            .requestId(UPDATED_REQUEST_ID)
            .pinCode(UPDATED_PIN_CODE)
            .email(UPDATED_EMAIL)
            .created(UPDATED_CREATED)
            .expires(UPDATED_EXPIRES);

        restSecuredRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSecuredRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSecuredRequest))
            )
            .andExpect(status().isOk());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
        SecuredRequest testSecuredRequest = securedRequestList.get(securedRequestList.size() - 1);
        assertThat(testSecuredRequest.getRequestId()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testSecuredRequest.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testSecuredRequest.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSecuredRequest.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testSecuredRequest.getExpires()).isEqualTo(UPDATED_EXPIRES);
    }

    @Test
    @Transactional
    void putNonExistingSecuredRequest() throws Exception {
        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();
        securedRequest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecuredRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, securedRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSecuredRequest() throws Exception {
        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();
        securedRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecuredRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSecuredRequest() throws Exception {
        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();
        securedRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecuredRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(securedRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSecuredRequestWithPatch() throws Exception {
        // Initialize the database
        securedRequestRepository.saveAndFlush(securedRequest);

        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();

        // Update the securedRequest using partial update
        SecuredRequest partialUpdatedSecuredRequest = new SecuredRequest();
        partialUpdatedSecuredRequest.setId(securedRequest.getId());

        partialUpdatedSecuredRequest.email(UPDATED_EMAIL).expires(UPDATED_EXPIRES);

        restSecuredRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecuredRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecuredRequest))
            )
            .andExpect(status().isOk());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
        SecuredRequest testSecuredRequest = securedRequestList.get(securedRequestList.size() - 1);
        assertThat(testSecuredRequest.getRequestId()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testSecuredRequest.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testSecuredRequest.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSecuredRequest.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testSecuredRequest.getExpires()).isEqualTo(UPDATED_EXPIRES);
    }

    @Test
    @Transactional
    void fullUpdateSecuredRequestWithPatch() throws Exception {
        // Initialize the database
        securedRequestRepository.saveAndFlush(securedRequest);

        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();

        // Update the securedRequest using partial update
        SecuredRequest partialUpdatedSecuredRequest = new SecuredRequest();
        partialUpdatedSecuredRequest.setId(securedRequest.getId());

        partialUpdatedSecuredRequest
            .requestId(UPDATED_REQUEST_ID)
            .pinCode(UPDATED_PIN_CODE)
            .email(UPDATED_EMAIL)
            .created(UPDATED_CREATED)
            .expires(UPDATED_EXPIRES);

        restSecuredRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecuredRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecuredRequest))
            )
            .andExpect(status().isOk());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
        SecuredRequest testSecuredRequest = securedRequestList.get(securedRequestList.size() - 1);
        assertThat(testSecuredRequest.getRequestId()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testSecuredRequest.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testSecuredRequest.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSecuredRequest.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testSecuredRequest.getExpires()).isEqualTo(UPDATED_EXPIRES);
    }

    @Test
    @Transactional
    void patchNonExistingSecuredRequest() throws Exception {
        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();
        securedRequest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecuredRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, securedRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSecuredRequest() throws Exception {
        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();
        securedRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecuredRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSecuredRequest() throws Exception {
        int databaseSizeBeforeUpdate = securedRequestRepository.findAll().size();
        securedRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecuredRequestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(securedRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecuredRequest in the database
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSecuredRequest() throws Exception {
        // Initialize the database
        securedRequestRepository.saveAndFlush(securedRequest);

        int databaseSizeBeforeDelete = securedRequestRepository.findAll().size();

        // Delete the securedRequest
        restSecuredRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, securedRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecuredRequest> securedRequestList = securedRequestRepository.findAll();
        assertThat(securedRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
