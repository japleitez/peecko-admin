package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.Plan;
import com.peecko.admin.domain.enumeration.YesNo;
import com.peecko.admin.domain.enumeration.YesNo;
import com.peecko.admin.repository.PlanRepository;
import com.peecko.admin.service.dto.PlanDTO;
import com.peecko.admin.service.mapper.PlanMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanResourceIT {

    private static final YesNo DEFAULT_TRIAL = YesNo.Y;
    private static final YesNo UPDATED_TRIAL = YesNo.N;

    private static final YesNo DEFAULT_ACTIVE = YesNo.Y;
    private static final YesNo UPDATED_ACTIVE = YesNo.N;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanMockMvc;

    private Plan plan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plan createEntity(EntityManager em) {
        Plan plan = new Plan().trial(DEFAULT_TRIAL).active(DEFAULT_ACTIVE).price(DEFAULT_PRICE).start(DEFAULT_START).end(DEFAULT_END);
        return plan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plan createUpdatedEntity(EntityManager em) {
        Plan plan = new Plan().trial(UPDATED_TRIAL).active(UPDATED_ACTIVE).price(UPDATED_PRICE).start(UPDATED_START).end(UPDATED_END);
        return plan;
    }

    @BeforeEach
    public void initTest() {
        plan = createEntity(em);
    }

    @Test
    @Transactional
    void createPlan() throws Exception {
        int databaseSizeBeforeCreate = planRepository.findAll().size();
        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);
        restPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeCreate + 1);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getTrial()).isEqualTo(DEFAULT_TRIAL);
        assertThat(testPlan.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testPlan.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPlan.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testPlan.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    void createPlanWithExistingId() throws Exception {
        // Create the Plan with an existing ID
        plan.setId(1L);
        PlanDTO planDTO = planMapper.toDto(plan);

        int databaseSizeBeforeCreate = planRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrialIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setTrial(null);

        // Create the Plan, which fails.
        PlanDTO planDTO = planMapper.toDto(plan);

        restPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setActive(null);

        // Create the Plan, which fails.
        PlanDTO planDTO = planMapper.toDto(plan);

        restPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setPrice(null);

        // Create the Plan, which fails.
        PlanDTO planDTO = planMapper.toDto(plan);

        restPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setStart(null);

        // Create the Plan, which fails.
        PlanDTO planDTO = planMapper.toDto(plan);

        restPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlans() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get all the planList
        restPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plan.getId().intValue())))
            .andExpect(jsonPath("$.[*].trial").value(hasItem(DEFAULT_TRIAL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }

    @Test
    @Transactional
    void getPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get the plan
        restPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, plan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plan.getId().intValue()))
            .andExpect(jsonPath("$.trial").value(DEFAULT_TRIAL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlan() throws Exception {
        // Get the plan
        restPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan
        Plan updatedPlan = planRepository.findById(plan.getId()).get();
        // Disconnect from session so that the updates on updatedPlan are not directly saved in db
        em.detach(updatedPlan);
        updatedPlan.trial(UPDATED_TRIAL).active(UPDATED_ACTIVE).price(UPDATED_PRICE).start(UPDATED_START).end(UPDATED_END);
        PlanDTO planDTO = planMapper.toDto(updatedPlan);

        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getTrial()).isEqualTo(UPDATED_TRIAL);
        assertThat(testPlan.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPlan.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPlan.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPlan.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void putNonExistingPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanWithPatch() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan using partial update
        Plan partialUpdatedPlan = new Plan();
        partialUpdatedPlan.setId(plan.getId());

        partialUpdatedPlan.active(UPDATED_ACTIVE).end(UPDATED_END);

        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlan.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlan))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getTrial()).isEqualTo(DEFAULT_TRIAL);
        assertThat(testPlan.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPlan.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPlan.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testPlan.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void fullUpdatePlanWithPatch() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan using partial update
        Plan partialUpdatedPlan = new Plan();
        partialUpdatedPlan.setId(plan.getId());

        partialUpdatedPlan.trial(UPDATED_TRIAL).active(UPDATED_ACTIVE).price(UPDATED_PRICE).start(UPDATED_START).end(UPDATED_END);

        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlan.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlan))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getTrial()).isEqualTo(UPDATED_TRIAL);
        assertThat(testPlan.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPlan.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPlan.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPlan.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void patchNonExistingPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // Create the Plan
        PlanDTO planDTO = planMapper.toDto(plan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeDelete = planRepository.findAll().size();

        // Delete the plan
        restPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, plan.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
