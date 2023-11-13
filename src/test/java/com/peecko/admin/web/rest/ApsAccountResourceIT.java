package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.ApsAccount;
import com.peecko.admin.domain.enumeration.Language;
import com.peecko.admin.domain.enumeration.YesNo;
import com.peecko.admin.domain.enumeration.YesNo;
import com.peecko.admin.domain.enumeration.YesNo;
import com.peecko.admin.repository.ApsAccountRepository;
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
 * Integration tests for the {@link ApsAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApsAccountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final YesNo DEFAULT_VERIFIED = YesNo.Y;
    private static final YesNo UPDATED_VERIFIED = YesNo.N;

    private static final Language DEFAULT_LANGUAGE = Language.EN;
    private static final Language UPDATED_LANGUAGE = Language.DE;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final YesNo DEFAULT_EMAIL_VERIFIED = YesNo.Y;
    private static final YesNo UPDATED_EMAIL_VERIFIED = YesNo.N;

    private static final String DEFAULT_LICENSE = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE = "BBBBBBBBBB";

    private static final YesNo DEFAULT_ACTIVE = YesNo.Y;
    private static final YesNo UPDATED_ACTIVE = YesNo.N;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/aps-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApsAccountRepository apsAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApsAccountMockMvc;

    private ApsAccount apsAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApsAccount createEntity(EntityManager em) {
        ApsAccount apsAccount = new ApsAccount()
            .name(DEFAULT_NAME)
            .username(DEFAULT_USERNAME)
            .verified(DEFAULT_VERIFIED)
            .language(DEFAULT_LANGUAGE)
            .email(DEFAULT_EMAIL)
            .emailVerified(DEFAULT_EMAIL_VERIFIED)
            .license(DEFAULT_LICENSE)
            .active(DEFAULT_ACTIVE)
            .password(DEFAULT_PASSWORD)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return apsAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApsAccount createUpdatedEntity(EntityManager em) {
        ApsAccount apsAccount = new ApsAccount()
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .verified(UPDATED_VERIFIED)
            .language(UPDATED_LANGUAGE)
            .email(UPDATED_EMAIL)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .license(UPDATED_LICENSE)
            .active(UPDATED_ACTIVE)
            .password(UPDATED_PASSWORD)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return apsAccount;
    }

    @BeforeEach
    public void initTest() {
        apsAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createApsAccount() throws Exception {
        int databaseSizeBeforeCreate = apsAccountRepository.findAll().size();
        // Create the ApsAccount
        restApsAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isCreated());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ApsAccount testApsAccount = apsAccountList.get(apsAccountList.size() - 1);
        assertThat(testApsAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApsAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testApsAccount.getVerified()).isEqualTo(DEFAULT_VERIFIED);
        assertThat(testApsAccount.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testApsAccount.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testApsAccount.getEmailVerified()).isEqualTo(DEFAULT_EMAIL_VERIFIED);
        assertThat(testApsAccount.getLicense()).isEqualTo(DEFAULT_LICENSE);
        assertThat(testApsAccount.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApsAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testApsAccount.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testApsAccount.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createApsAccountWithExistingId() throws Exception {
        // Create the ApsAccount with an existing ID
        apsAccount.setId(1L);

        int databaseSizeBeforeCreate = apsAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApsAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isBadRequest());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apsAccountRepository.findAll().size();
        // set the field null
        apsAccount.setName(null);

        // Create the ApsAccount, which fails.

        restApsAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isBadRequest());

        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apsAccountRepository.findAll().size();
        // set the field null
        apsAccount.setUsername(null);

        // Create the ApsAccount, which fails.

        restApsAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isBadRequest());

        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVerifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = apsAccountRepository.findAll().size();
        // set the field null
        apsAccount.setVerified(null);

        // Create the ApsAccount, which fails.

        restApsAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isBadRequest());

        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = apsAccountRepository.findAll().size();
        // set the field null
        apsAccount.setLanguage(null);

        // Create the ApsAccount, which fails.

        restApsAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isBadRequest());

        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailVerifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = apsAccountRepository.findAll().size();
        // set the field null
        apsAccount.setEmailVerified(null);

        // Create the ApsAccount, which fails.

        restApsAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isBadRequest());

        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = apsAccountRepository.findAll().size();
        // set the field null
        apsAccount.setActive(null);

        // Create the ApsAccount, which fails.

        restApsAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isBadRequest());

        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApsAccounts() throws Exception {
        // Initialize the database
        apsAccountRepository.saveAndFlush(apsAccount);

        // Get all the apsAccountList
        restApsAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apsAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].emailVerified").value(hasItem(DEFAULT_EMAIL_VERIFIED.toString())))
            .andExpect(jsonPath("$.[*].license").value(hasItem(DEFAULT_LICENSE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getApsAccount() throws Exception {
        // Initialize the database
        apsAccountRepository.saveAndFlush(apsAccount);

        // Get the apsAccount
        restApsAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, apsAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apsAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.emailVerified").value(DEFAULT_EMAIL_VERIFIED.toString()))
            .andExpect(jsonPath("$.license").value(DEFAULT_LICENSE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApsAccount() throws Exception {
        // Get the apsAccount
        restApsAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApsAccount() throws Exception {
        // Initialize the database
        apsAccountRepository.saveAndFlush(apsAccount);

        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();

        // Update the apsAccount
        ApsAccount updatedApsAccount = apsAccountRepository.findById(apsAccount.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApsAccount are not directly saved in db
        em.detach(updatedApsAccount);
        updatedApsAccount
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .verified(UPDATED_VERIFIED)
            .language(UPDATED_LANGUAGE)
            .email(UPDATED_EMAIL)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .license(UPDATED_LICENSE)
            .active(UPDATED_ACTIVE)
            .password(UPDATED_PASSWORD)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restApsAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApsAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApsAccount))
            )
            .andExpect(status().isOk());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
        ApsAccount testApsAccount = apsAccountList.get(apsAccountList.size() - 1);
        assertThat(testApsAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApsAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testApsAccount.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testApsAccount.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testApsAccount.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApsAccount.getEmailVerified()).isEqualTo(UPDATED_EMAIL_VERIFIED);
        assertThat(testApsAccount.getLicense()).isEqualTo(UPDATED_LICENSE);
        assertThat(testApsAccount.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApsAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testApsAccount.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testApsAccount.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingApsAccount() throws Exception {
        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();
        apsAccount.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApsAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apsAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apsAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApsAccount() throws Exception {
        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();
        apsAccount.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApsAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apsAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApsAccount() throws Exception {
        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();
        apsAccount.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApsAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apsAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApsAccountWithPatch() throws Exception {
        // Initialize the database
        apsAccountRepository.saveAndFlush(apsAccount);

        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();

        // Update the apsAccount using partial update
        ApsAccount partialUpdatedApsAccount = new ApsAccount();
        partialUpdatedApsAccount.setId(apsAccount.getId());

        partialUpdatedApsAccount
            .verified(UPDATED_VERIFIED)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .license(UPDATED_LICENSE)
            .password(UPDATED_PASSWORD)
            .updated(UPDATED_UPDATED);

        restApsAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApsAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApsAccount))
            )
            .andExpect(status().isOk());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
        ApsAccount testApsAccount = apsAccountList.get(apsAccountList.size() - 1);
        assertThat(testApsAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApsAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testApsAccount.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testApsAccount.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testApsAccount.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testApsAccount.getEmailVerified()).isEqualTo(UPDATED_EMAIL_VERIFIED);
        assertThat(testApsAccount.getLicense()).isEqualTo(UPDATED_LICENSE);
        assertThat(testApsAccount.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApsAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testApsAccount.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testApsAccount.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateApsAccountWithPatch() throws Exception {
        // Initialize the database
        apsAccountRepository.saveAndFlush(apsAccount);

        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();

        // Update the apsAccount using partial update
        ApsAccount partialUpdatedApsAccount = new ApsAccount();
        partialUpdatedApsAccount.setId(apsAccount.getId());

        partialUpdatedApsAccount
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .verified(UPDATED_VERIFIED)
            .language(UPDATED_LANGUAGE)
            .email(UPDATED_EMAIL)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .license(UPDATED_LICENSE)
            .active(UPDATED_ACTIVE)
            .password(UPDATED_PASSWORD)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restApsAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApsAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApsAccount))
            )
            .andExpect(status().isOk());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
        ApsAccount testApsAccount = apsAccountList.get(apsAccountList.size() - 1);
        assertThat(testApsAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApsAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testApsAccount.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testApsAccount.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testApsAccount.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApsAccount.getEmailVerified()).isEqualTo(UPDATED_EMAIL_VERIFIED);
        assertThat(testApsAccount.getLicense()).isEqualTo(UPDATED_LICENSE);
        assertThat(testApsAccount.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApsAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testApsAccount.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testApsAccount.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingApsAccount() throws Exception {
        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();
        apsAccount.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApsAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apsAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apsAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApsAccount() throws Exception {
        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();
        apsAccount.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApsAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apsAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApsAccount() throws Exception {
        int databaseSizeBeforeUpdate = apsAccountRepository.findAll().size();
        apsAccount.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApsAccountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apsAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApsAccount in the database
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApsAccount() throws Exception {
        // Initialize the database
        apsAccountRepository.saveAndFlush(apsAccount);

        int databaseSizeBeforeDelete = apsAccountRepository.findAll().size();

        // Delete the apsAccount
        restApsAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, apsAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApsAccount> apsAccountList = apsAccountRepository.findAll();
        assertThat(apsAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
