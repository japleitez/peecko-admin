package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.LikedVideo;
import com.peecko.admin.repository.LikedVideoRepository;
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
 * Integration tests for the {@link LikedVideoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LikedVideoResourceIT {

    private static final Long DEFAULT_VIDEO_ID = 1L;
    private static final Long UPDATED_VIDEO_ID = 2L;

    private static final Long DEFAULT_PERSON_ID = 1L;
    private static final Long UPDATED_PERSON_ID = 2L;

    private static final Long DEFAULT_COACH_ID = 1L;
    private static final Long UPDATED_COACH_ID = 2L;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/liked-videos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LikedVideoRepository likedVideoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikedVideoMockMvc;

    private LikedVideo likedVideo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikedVideo createEntity(EntityManager em) {
        LikedVideo likedVideo = new LikedVideo()
            .videoId(DEFAULT_VIDEO_ID)
            .personId(DEFAULT_PERSON_ID)
            .coachId(DEFAULT_COACH_ID)
            .created(DEFAULT_CREATED);
        return likedVideo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikedVideo createUpdatedEntity(EntityManager em) {
        LikedVideo likedVideo = new LikedVideo()
            .videoId(UPDATED_VIDEO_ID)
            .personId(UPDATED_PERSON_ID)
            .coachId(UPDATED_COACH_ID)
            .created(UPDATED_CREATED);
        return likedVideo;
    }

    @BeforeEach
    public void initTest() {
        likedVideo = createEntity(em);
    }

    @Test
    @Transactional
    void createLikedVideo() throws Exception {
        int databaseSizeBeforeCreate = likedVideoRepository.findAll().size();
        // Create the LikedVideo
        restLikedVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likedVideo)))
            .andExpect(status().isCreated());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeCreate + 1);
        LikedVideo testLikedVideo = likedVideoList.get(likedVideoList.size() - 1);
        assertThat(testLikedVideo.getVideoId()).isEqualTo(DEFAULT_VIDEO_ID);
        assertThat(testLikedVideo.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testLikedVideo.getCoachId()).isEqualTo(DEFAULT_COACH_ID);
        assertThat(testLikedVideo.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    void createLikedVideoWithExistingId() throws Exception {
        // Create the LikedVideo with an existing ID
        likedVideo.setId(1L);

        int databaseSizeBeforeCreate = likedVideoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikedVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likedVideo)))
            .andExpect(status().isBadRequest());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVideoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = likedVideoRepository.findAll().size();
        // set the field null
        likedVideo.setVideoId(null);

        // Create the LikedVideo, which fails.

        restLikedVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likedVideo)))
            .andExpect(status().isBadRequest());

        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPersonIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = likedVideoRepository.findAll().size();
        // set the field null
        likedVideo.setPersonId(null);

        // Create the LikedVideo, which fails.

        restLikedVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likedVideo)))
            .andExpect(status().isBadRequest());

        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoachIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = likedVideoRepository.findAll().size();
        // set the field null
        likedVideo.setCoachId(null);

        // Create the LikedVideo, which fails.

        restLikedVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likedVideo)))
            .andExpect(status().isBadRequest());

        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = likedVideoRepository.findAll().size();
        // set the field null
        likedVideo.setCreated(null);

        // Create the LikedVideo, which fails.

        restLikedVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likedVideo)))
            .andExpect(status().isBadRequest());

        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLikedVideos() throws Exception {
        // Initialize the database
        likedVideoRepository.saveAndFlush(likedVideo);

        // Get all the likedVideoList
        restLikedVideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likedVideo.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoId").value(hasItem(DEFAULT_VIDEO_ID.intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.intValue())))
            .andExpect(jsonPath("$.[*].coachId").value(hasItem(DEFAULT_COACH_ID.intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }

    @Test
    @Transactional
    void getLikedVideo() throws Exception {
        // Initialize the database
        likedVideoRepository.saveAndFlush(likedVideo);

        // Get the likedVideo
        restLikedVideoMockMvc
            .perform(get(ENTITY_API_URL_ID, likedVideo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(likedVideo.getId().intValue()))
            .andExpect(jsonPath("$.videoId").value(DEFAULT_VIDEO_ID.intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.intValue()))
            .andExpect(jsonPath("$.coachId").value(DEFAULT_COACH_ID.intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLikedVideo() throws Exception {
        // Get the likedVideo
        restLikedVideoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLikedVideo() throws Exception {
        // Initialize the database
        likedVideoRepository.saveAndFlush(likedVideo);

        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();

        // Update the likedVideo
        LikedVideo updatedLikedVideo = likedVideoRepository.findById(likedVideo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLikedVideo are not directly saved in db
        em.detach(updatedLikedVideo);
        updatedLikedVideo.videoId(UPDATED_VIDEO_ID).personId(UPDATED_PERSON_ID).coachId(UPDATED_COACH_ID).created(UPDATED_CREATED);

        restLikedVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLikedVideo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLikedVideo))
            )
            .andExpect(status().isOk());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
        LikedVideo testLikedVideo = likedVideoList.get(likedVideoList.size() - 1);
        assertThat(testLikedVideo.getVideoId()).isEqualTo(UPDATED_VIDEO_ID);
        assertThat(testLikedVideo.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testLikedVideo.getCoachId()).isEqualTo(UPDATED_COACH_ID);
        assertThat(testLikedVideo.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    void putNonExistingLikedVideo() throws Exception {
        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();
        likedVideo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikedVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, likedVideo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likedVideo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLikedVideo() throws Exception {
        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();
        likedVideo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikedVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likedVideo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLikedVideo() throws Exception {
        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();
        likedVideo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikedVideoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likedVideo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLikedVideoWithPatch() throws Exception {
        // Initialize the database
        likedVideoRepository.saveAndFlush(likedVideo);

        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();

        // Update the likedVideo using partial update
        LikedVideo partialUpdatedLikedVideo = new LikedVideo();
        partialUpdatedLikedVideo.setId(likedVideo.getId());

        partialUpdatedLikedVideo.coachId(UPDATED_COACH_ID);

        restLikedVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLikedVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLikedVideo))
            )
            .andExpect(status().isOk());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
        LikedVideo testLikedVideo = likedVideoList.get(likedVideoList.size() - 1);
        assertThat(testLikedVideo.getVideoId()).isEqualTo(DEFAULT_VIDEO_ID);
        assertThat(testLikedVideo.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testLikedVideo.getCoachId()).isEqualTo(UPDATED_COACH_ID);
        assertThat(testLikedVideo.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    void fullUpdateLikedVideoWithPatch() throws Exception {
        // Initialize the database
        likedVideoRepository.saveAndFlush(likedVideo);

        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();

        // Update the likedVideo using partial update
        LikedVideo partialUpdatedLikedVideo = new LikedVideo();
        partialUpdatedLikedVideo.setId(likedVideo.getId());

        partialUpdatedLikedVideo.videoId(UPDATED_VIDEO_ID).personId(UPDATED_PERSON_ID).coachId(UPDATED_COACH_ID).created(UPDATED_CREATED);

        restLikedVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLikedVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLikedVideo))
            )
            .andExpect(status().isOk());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
        LikedVideo testLikedVideo = likedVideoList.get(likedVideoList.size() - 1);
        assertThat(testLikedVideo.getVideoId()).isEqualTo(UPDATED_VIDEO_ID);
        assertThat(testLikedVideo.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testLikedVideo.getCoachId()).isEqualTo(UPDATED_COACH_ID);
        assertThat(testLikedVideo.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    void patchNonExistingLikedVideo() throws Exception {
        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();
        likedVideo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikedVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, likedVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(likedVideo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLikedVideo() throws Exception {
        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();
        likedVideo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikedVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(likedVideo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLikedVideo() throws Exception {
        int databaseSizeBeforeUpdate = likedVideoRepository.findAll().size();
        likedVideo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikedVideoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(likedVideo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LikedVideo in the database
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLikedVideo() throws Exception {
        // Initialize the database
        likedVideoRepository.saveAndFlush(likedVideo);

        int databaseSizeBeforeDelete = likedVideoRepository.findAll().size();

        // Delete the likedVideo
        restLikedVideoMockMvc
            .perform(delete(ENTITY_API_URL_ID, likedVideo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LikedVideo> likedVideoList = likedVideoRepository.findAll();
        assertThat(likedVideoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
