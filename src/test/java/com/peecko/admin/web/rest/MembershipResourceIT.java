package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.Membership;
import com.peecko.admin.repository.MembershipRepository;
import com.peecko.admin.service.dto.MembershipDTO;
import com.peecko.admin.service.mapper.MembershipMapper;
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
 * Integration tests for the {@link MembershipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MembershipResourceIT {

    private static final String DEFAULT_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSE = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/memberships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MembershipMapper membershipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembershipMockMvc;

    private Membership membership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Membership createEntity(EntityManager em) {
        Membership membership = new Membership().period(DEFAULT_PERIOD).license(DEFAULT_LICENSE).email(DEFAULT_EMAIL);
        return membership;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Membership createUpdatedEntity(EntityManager em) {
        Membership membership = new Membership().period(UPDATED_PERIOD).license(UPDATED_LICENSE).email(UPDATED_EMAIL);
        return membership;
    }

    @BeforeEach
    public void initTest() {
        membership = createEntity(em);
    }

    @Test
    @Transactional
    void createMembership() throws Exception {
        int databaseSizeBeforeCreate = membershipRepository.findAll().size();
        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);
        restMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeCreate + 1);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testMembership.getLicense()).isEqualTo(DEFAULT_LICENSE);
        assertThat(testMembership.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createMembershipWithExistingId() throws Exception {
        // Create the Membership with an existing ID
        membership.setId(1L);
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        int databaseSizeBeforeCreate = membershipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipRepository.findAll().size();
        // set the field null
        membership.setPeriod(null);

        // Create the Membership, which fails.
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        restMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isBadRequest());

        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLicenseIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipRepository.findAll().size();
        // set the field null
        membership.setLicense(null);

        // Create the Membership, which fails.
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        restMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isBadRequest());

        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipRepository.findAll().size();
        // set the field null
        membership.setEmail(null);

        // Create the Membership, which fails.
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        restMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isBadRequest());

        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMemberships() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        // Get all the membershipList
        restMembershipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membership.getId().intValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].license").value(hasItem(DEFAULT_LICENSE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getMembership() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        // Get the membership
        restMembershipMockMvc
            .perform(get(ENTITY_API_URL_ID, membership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membership.getId().intValue()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.license").value(DEFAULT_LICENSE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingMembership() throws Exception {
        // Get the membership
        restMembershipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMembership() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // Update the membership
        Membership updatedMembership = membershipRepository.findById(membership.getId()).get();
        // Disconnect from session so that the updates on updatedMembership are not directly saved in db
        em.detach(updatedMembership);
        updatedMembership.period(UPDATED_PERIOD).license(UPDATED_LICENSE).email(UPDATED_EMAIL);
        MembershipDTO membershipDTO = membershipMapper.toDto(updatedMembership);

        restMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, membershipDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isOk());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testMembership.getLicense()).isEqualTo(UPDATED_LICENSE);
        assertThat(testMembership.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();
        membership.setId(count.incrementAndGet());

        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, membershipDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();
        membership.setId(count.incrementAndGet());

        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();
        membership.setId(count.incrementAndGet());

        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMembershipWithPatch() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // Update the membership using partial update
        Membership partialUpdatedMembership = new Membership();
        partialUpdatedMembership.setId(membership.getId());

        partialUpdatedMembership.period(UPDATED_PERIOD).license(UPDATED_LICENSE);

        restMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembership.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembership))
            )
            .andExpect(status().isOk());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testMembership.getLicense()).isEqualTo(UPDATED_LICENSE);
        assertThat(testMembership.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateMembershipWithPatch() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // Update the membership using partial update
        Membership partialUpdatedMembership = new Membership();
        partialUpdatedMembership.setId(membership.getId());

        partialUpdatedMembership.period(UPDATED_PERIOD).license(UPDATED_LICENSE).email(UPDATED_EMAIL);

        restMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembership.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembership))
            )
            .andExpect(status().isOk());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testMembership.getLicense()).isEqualTo(UPDATED_LICENSE);
        assertThat(testMembership.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();
        membership.setId(count.incrementAndGet());

        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, membershipDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();
        membership.setId(count.incrementAndGet());

        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();
        membership.setId(count.incrementAndGet());

        // Create the Membership
        MembershipDTO membershipDTO = membershipMapper.toDto(membership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMembership() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        int databaseSizeBeforeDelete = membershipRepository.findAll().size();

        // Delete the membership
        restMembershipMockMvc
            .perform(delete(ENTITY_API_URL_ID, membership.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
