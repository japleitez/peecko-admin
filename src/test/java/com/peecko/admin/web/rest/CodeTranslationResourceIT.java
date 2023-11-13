package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.CodeTranslation;
import com.peecko.admin.domain.enumeration.Language;
import com.peecko.admin.repository.CodeTranslationRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link CodeTranslationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodeTranslationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Language DEFAULT_LANG = Language.EN;
    private static final Language UPDATED_LANG = Language.DE;

    private static final String DEFAULT_TRANSLATION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/code-translations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CodeTranslationRepository codeTranslationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodeTranslationMockMvc;

    private CodeTranslation codeTranslation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeTranslation createEntity(EntityManager em) {
        CodeTranslation codeTranslation = new CodeTranslation().code(DEFAULT_CODE).lang(DEFAULT_LANG).translation(DEFAULT_TRANSLATION);
        return codeTranslation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeTranslation createUpdatedEntity(EntityManager em) {
        CodeTranslation codeTranslation = new CodeTranslation().code(UPDATED_CODE).lang(UPDATED_LANG).translation(UPDATED_TRANSLATION);
        return codeTranslation;
    }

    @BeforeEach
    public void initTest() {
        codeTranslation = createEntity(em);
    }

    @Test
    @Transactional
    void createCodeTranslation() throws Exception {
        int databaseSizeBeforeCreate = codeTranslationRepository.findAll().size();
        // Create the CodeTranslation
        restCodeTranslationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isCreated());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeCreate + 1);
        CodeTranslation testCodeTranslation = codeTranslationList.get(codeTranslationList.size() - 1);
        assertThat(testCodeTranslation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCodeTranslation.getLang()).isEqualTo(DEFAULT_LANG);
        assertThat(testCodeTranslation.getTranslation()).isEqualTo(DEFAULT_TRANSLATION);
    }

    @Test
    @Transactional
    void createCodeTranslationWithExistingId() throws Exception {
        // Create the CodeTranslation with an existing ID
        codeTranslation.setId(1L);

        int databaseSizeBeforeCreate = codeTranslationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeTranslationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = codeTranslationRepository.findAll().size();
        // set the field null
        codeTranslation.setCode(null);

        // Create the CodeTranslation, which fails.

        restCodeTranslationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isBadRequest());

        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLangIsRequired() throws Exception {
        int databaseSizeBeforeTest = codeTranslationRepository.findAll().size();
        // set the field null
        codeTranslation.setLang(null);

        // Create the CodeTranslation, which fails.

        restCodeTranslationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isBadRequest());

        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTranslationIsRequired() throws Exception {
        int databaseSizeBeforeTest = codeTranslationRepository.findAll().size();
        // set the field null
        codeTranslation.setTranslation(null);

        // Create the CodeTranslation, which fails.

        restCodeTranslationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isBadRequest());

        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCodeTranslations() throws Exception {
        // Initialize the database
        codeTranslationRepository.saveAndFlush(codeTranslation);

        // Get all the codeTranslationList
        restCodeTranslationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codeTranslation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG.toString())))
            .andExpect(jsonPath("$.[*].translation").value(hasItem(DEFAULT_TRANSLATION)));
    }

    @Test
    @Transactional
    void getCodeTranslation() throws Exception {
        // Initialize the database
        codeTranslationRepository.saveAndFlush(codeTranslation);

        // Get the codeTranslation
        restCodeTranslationMockMvc
            .perform(get(ENTITY_API_URL_ID, codeTranslation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codeTranslation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG.toString()))
            .andExpect(jsonPath("$.translation").value(DEFAULT_TRANSLATION));
    }

    @Test
    @Transactional
    void getNonExistingCodeTranslation() throws Exception {
        // Get the codeTranslation
        restCodeTranslationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCodeTranslation() throws Exception {
        // Initialize the database
        codeTranslationRepository.saveAndFlush(codeTranslation);

        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();

        // Update the codeTranslation
        CodeTranslation updatedCodeTranslation = codeTranslationRepository.findById(codeTranslation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCodeTranslation are not directly saved in db
        em.detach(updatedCodeTranslation);
        updatedCodeTranslation.code(UPDATED_CODE).lang(UPDATED_LANG).translation(UPDATED_TRANSLATION);

        restCodeTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCodeTranslation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCodeTranslation))
            )
            .andExpect(status().isOk());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
        CodeTranslation testCodeTranslation = codeTranslationList.get(codeTranslationList.size() - 1);
        assertThat(testCodeTranslation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCodeTranslation.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testCodeTranslation.getTranslation()).isEqualTo(UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    void putNonExistingCodeTranslation() throws Exception {
        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();
        codeTranslation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codeTranslation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCodeTranslation() throws Exception {
        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();
        codeTranslation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCodeTranslation() throws Exception {
        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();
        codeTranslation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeTranslationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCodeTranslationWithPatch() throws Exception {
        // Initialize the database
        codeTranslationRepository.saveAndFlush(codeTranslation);

        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();

        // Update the codeTranslation using partial update
        CodeTranslation partialUpdatedCodeTranslation = new CodeTranslation();
        partialUpdatedCodeTranslation.setId(codeTranslation.getId());

        partialUpdatedCodeTranslation.lang(UPDATED_LANG);

        restCodeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodeTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodeTranslation))
            )
            .andExpect(status().isOk());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
        CodeTranslation testCodeTranslation = codeTranslationList.get(codeTranslationList.size() - 1);
        assertThat(testCodeTranslation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCodeTranslation.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testCodeTranslation.getTranslation()).isEqualTo(DEFAULT_TRANSLATION);
    }

    @Test
    @Transactional
    void fullUpdateCodeTranslationWithPatch() throws Exception {
        // Initialize the database
        codeTranslationRepository.saveAndFlush(codeTranslation);

        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();

        // Update the codeTranslation using partial update
        CodeTranslation partialUpdatedCodeTranslation = new CodeTranslation();
        partialUpdatedCodeTranslation.setId(codeTranslation.getId());

        partialUpdatedCodeTranslation.code(UPDATED_CODE).lang(UPDATED_LANG).translation(UPDATED_TRANSLATION);

        restCodeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodeTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodeTranslation))
            )
            .andExpect(status().isOk());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
        CodeTranslation testCodeTranslation = codeTranslationList.get(codeTranslationList.size() - 1);
        assertThat(testCodeTranslation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCodeTranslation.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testCodeTranslation.getTranslation()).isEqualTo(UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    void patchNonExistingCodeTranslation() throws Exception {
        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();
        codeTranslation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, codeTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCodeTranslation() throws Exception {
        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();
        codeTranslation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCodeTranslation() throws Exception {
        int databaseSizeBeforeUpdate = codeTranslationRepository.findAll().size();
        codeTranslation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codeTranslation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodeTranslation in the database
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCodeTranslation() throws Exception {
        // Initialize the database
        codeTranslationRepository.saveAndFlush(codeTranslation);

        int databaseSizeBeforeDelete = codeTranslationRepository.findAll().size();

        // Delete the codeTranslation
        restCodeTranslationMockMvc
            .perform(delete(ENTITY_API_URL_ID, codeTranslation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodeTranslation> codeTranslationList = codeTranslationRepository.findAll();
        assertThat(codeTranslationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
