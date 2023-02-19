package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.Coach;
import com.peecko.admin.repository.CoachRepository;
import com.peecko.admin.service.dto.CoachDTO;
import com.peecko.admin.service.mapper.CoachMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CoachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoachResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME = "AAAAAAAAAA";
    private static final String UPDATED_RESUME = "BBBBBBBBBB";

    private static final String DEFAULT_LANGS = "AAAAAAAAAA";
    private static final String UPDATED_LANGS = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/coaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private CoachMapper coachMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoachMockMvc;

    private Coach coach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coach createEntity(EntityManager em) {
        Coach coach = new Coach()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .resume(DEFAULT_RESUME)
            .langs(DEFAULT_LANGS)
            .tags(DEFAULT_TAGS);
        return coach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coach createUpdatedEntity(EntityManager em) {
        Coach coach = new Coach()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .resume(UPDATED_RESUME)
            .langs(UPDATED_LANGS)
            .tags(UPDATED_TAGS);
        return coach;
    }

    @BeforeEach
    public void initTest() {
        coach = createEntity(em);
    }

    @Test
    @Transactional
    void createCoach() throws Exception {
        int databaseSizeBeforeCreate = coachRepository.findAll().size();
        // Create the Coach
        CoachDTO coachDTO = coachMapper.toDto(coach);
        restCoachMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeCreate + 1);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoach.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testCoach.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCoach.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCoach.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testCoach.getLangs()).isEqualTo(DEFAULT_LANGS);
        assertThat(testCoach.getTags()).isEqualTo(DEFAULT_TAGS);
    }

    @Test
    @Transactional
    void createCoachWithExistingId() throws Exception {
        // Create the Coach with an existing ID
        coach.setId(1L);
        CoachDTO coachDTO = coachMapper.toDto(coach);

        int databaseSizeBeforeCreate = coachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoachMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setName(null);

        // Create the Coach, which fails.
        CoachDTO coachDTO = coachMapper.toDto(coach);

        restCoachMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setSurname(null);

        // Create the Coach, which fails.
        CoachDTO coachDTO = coachMapper.toDto(coach);

        restCoachMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setEmail(null);

        // Create the Coach, which fails.
        CoachDTO coachDTO = coachMapper.toDto(coach);

        restCoachMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCoaches() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        // Get all the coachList
        restCoachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coach.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME)))
            .andExpect(jsonPath("$.[*].langs").value(hasItem(DEFAULT_LANGS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));
    }

    @Test
    @Transactional
    void getCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        // Get the coach
        restCoachMockMvc
            .perform(get(ENTITY_API_URL_ID, coach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coach.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME))
            .andExpect(jsonPath("$.langs").value(DEFAULT_LANGS))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS));
    }

    @Test
    @Transactional
    void getNonExistingCoach() throws Exception {
        // Get the coach
        restCoachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeUpdate = coachRepository.findAll().size();

        // Update the coach
        Coach updatedCoach = coachRepository.findById(coach.getId()).get();
        // Disconnect from session so that the updates on updatedCoach are not directly saved in db
        em.detach(updatedCoach);
        updatedCoach
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .resume(UPDATED_RESUME)
            .langs(UPDATED_LANGS)
            .tags(UPDATED_TAGS);
        CoachDTO coachDTO = coachMapper.toDto(updatedCoach);

        restCoachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coachDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isOk());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoach.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testCoach.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCoach.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCoach.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testCoach.getLangs()).isEqualTo(UPDATED_LANGS);
        assertThat(testCoach.getTags()).isEqualTo(UPDATED_TAGS);
    }

    @Test
    @Transactional
    void putNonExistingCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(count.incrementAndGet());

        // Create the Coach
        CoachDTO coachDTO = coachMapper.toDto(coach);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coachDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(count.incrementAndGet());

        // Create the Coach
        CoachDTO coachDTO = coachMapper.toDto(coach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(count.incrementAndGet());

        // Create the Coach
        CoachDTO coachDTO = coachMapper.toDto(coach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoachWithPatch() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeUpdate = coachRepository.findAll().size();

        // Update the coach using partial update
        Coach partialUpdatedCoach = new Coach();
        partialUpdatedCoach.setId(coach.getId());

        partialUpdatedCoach.email(UPDATED_EMAIL);

        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoach.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoach))
            )
            .andExpect(status().isOk());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoach.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testCoach.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCoach.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCoach.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testCoach.getLangs()).isEqualTo(DEFAULT_LANGS);
        assertThat(testCoach.getTags()).isEqualTo(DEFAULT_TAGS);
    }

    @Test
    @Transactional
    void fullUpdateCoachWithPatch() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeUpdate = coachRepository.findAll().size();

        // Update the coach using partial update
        Coach partialUpdatedCoach = new Coach();
        partialUpdatedCoach.setId(coach.getId());

        partialUpdatedCoach
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .resume(UPDATED_RESUME)
            .langs(UPDATED_LANGS)
            .tags(UPDATED_TAGS);

        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoach.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoach))
            )
            .andExpect(status().isOk());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoach.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testCoach.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCoach.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCoach.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testCoach.getLangs()).isEqualTo(UPDATED_LANGS);
        assertThat(testCoach.getTags()).isEqualTo(UPDATED_TAGS);
    }

    @Test
    @Transactional
    void patchNonExistingCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(count.incrementAndGet());

        // Create the Coach
        CoachDTO coachDTO = coachMapper.toDto(coach);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coachDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(count.incrementAndGet());

        // Create the Coach
        CoachDTO coachDTO = coachMapper.toDto(coach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(count.incrementAndGet());

        // Create the Coach
        CoachDTO coachDTO = coachMapper.toDto(coach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coachDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeDelete = coachRepository.findAll().size();

        // Delete the coach
        restCoachMockMvc
            .perform(delete(ENTITY_API_URL_ID, coach.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
