package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.VideoStat;
import com.peecko.admin.repository.VideoStatRepository;
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
 * Integration tests for the {@link VideoStatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VideoStatResourceIT {

    private static final Long DEFAULT_VIDEO_ID = 1L;
    private static final Long UPDATED_VIDEO_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Long DEFAULT_COACH_ID = 1L;
    private static final Long UPDATED_COACH_ID = 2L;

    private static final Integer DEFAULT_LIKED = 1;
    private static final Integer UPDATED_LIKED = 2;

    private static final Integer DEFAULT_VIEWED = 1;
    private static final Integer UPDATED_VIEWED = 2;

    private static final String ENTITY_API_URL = "/api/video-stats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VideoStatRepository videoStatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideoStatMockMvc;

    private VideoStat videoStat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoStat createEntity(EntityManager em) {
        VideoStat videoStat = new VideoStat()
            .videoId(DEFAULT_VIDEO_ID)
            .categoryId(DEFAULT_CATEGORY_ID)
            .coachId(DEFAULT_COACH_ID)
            .liked(DEFAULT_LIKED)
            .viewed(DEFAULT_VIEWED);
        return videoStat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoStat createUpdatedEntity(EntityManager em) {
        VideoStat videoStat = new VideoStat()
            .videoId(UPDATED_VIDEO_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .coachId(UPDATED_COACH_ID)
            .liked(UPDATED_LIKED)
            .viewed(UPDATED_VIEWED);
        return videoStat;
    }

    @BeforeEach
    public void initTest() {
        videoStat = createEntity(em);
    }

    @Test
    @Transactional
    void createVideoStat() throws Exception {
        int databaseSizeBeforeCreate = videoStatRepository.findAll().size();
        // Create the VideoStat
        restVideoStatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoStat)))
            .andExpect(status().isCreated());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeCreate + 1);
        VideoStat testVideoStat = videoStatList.get(videoStatList.size() - 1);
        assertThat(testVideoStat.getVideoId()).isEqualTo(DEFAULT_VIDEO_ID);
        assertThat(testVideoStat.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testVideoStat.getCoachId()).isEqualTo(DEFAULT_COACH_ID);
        assertThat(testVideoStat.getLiked()).isEqualTo(DEFAULT_LIKED);
        assertThat(testVideoStat.getViewed()).isEqualTo(DEFAULT_VIEWED);
    }

    @Test
    @Transactional
    void createVideoStatWithExistingId() throws Exception {
        // Create the VideoStat with an existing ID
        videoStat.setId(1L);

        int databaseSizeBeforeCreate = videoStatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoStatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoStat)))
            .andExpect(status().isBadRequest());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVideoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoStatRepository.findAll().size();
        // set the field null
        videoStat.setVideoId(null);

        // Create the VideoStat, which fails.

        restVideoStatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoStat)))
            .andExpect(status().isBadRequest());

        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoStatRepository.findAll().size();
        // set the field null
        videoStat.setCategoryId(null);

        // Create the VideoStat, which fails.

        restVideoStatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoStat)))
            .andExpect(status().isBadRequest());

        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoachIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoStatRepository.findAll().size();
        // set the field null
        videoStat.setCoachId(null);

        // Create the VideoStat, which fails.

        restVideoStatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoStat)))
            .andExpect(status().isBadRequest());

        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLikedIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoStatRepository.findAll().size();
        // set the field null
        videoStat.setLiked(null);

        // Create the VideoStat, which fails.

        restVideoStatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoStat)))
            .andExpect(status().isBadRequest());

        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkViewedIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoStatRepository.findAll().size();
        // set the field null
        videoStat.setViewed(null);

        // Create the VideoStat, which fails.

        restVideoStatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoStat)))
            .andExpect(status().isBadRequest());

        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVideoStats() throws Exception {
        // Initialize the database
        videoStatRepository.saveAndFlush(videoStat);

        // Get all the videoStatList
        restVideoStatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoStat.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoId").value(hasItem(DEFAULT_VIDEO_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].coachId").value(hasItem(DEFAULT_COACH_ID.intValue())))
            .andExpect(jsonPath("$.[*].liked").value(hasItem(DEFAULT_LIKED)))
            .andExpect(jsonPath("$.[*].viewed").value(hasItem(DEFAULT_VIEWED)));
    }

    @Test
    @Transactional
    void getVideoStat() throws Exception {
        // Initialize the database
        videoStatRepository.saveAndFlush(videoStat);

        // Get the videoStat
        restVideoStatMockMvc
            .perform(get(ENTITY_API_URL_ID, videoStat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(videoStat.getId().intValue()))
            .andExpect(jsonPath("$.videoId").value(DEFAULT_VIDEO_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.coachId").value(DEFAULT_COACH_ID.intValue()))
            .andExpect(jsonPath("$.liked").value(DEFAULT_LIKED))
            .andExpect(jsonPath("$.viewed").value(DEFAULT_VIEWED));
    }

    @Test
    @Transactional
    void getNonExistingVideoStat() throws Exception {
        // Get the videoStat
        restVideoStatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVideoStat() throws Exception {
        // Initialize the database
        videoStatRepository.saveAndFlush(videoStat);

        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();

        // Update the videoStat
        VideoStat updatedVideoStat = videoStatRepository.findById(videoStat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVideoStat are not directly saved in db
        em.detach(updatedVideoStat);
        updatedVideoStat
            .videoId(UPDATED_VIDEO_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .coachId(UPDATED_COACH_ID)
            .liked(UPDATED_LIKED)
            .viewed(UPDATED_VIEWED);

        restVideoStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVideoStat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVideoStat))
            )
            .andExpect(status().isOk());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
        VideoStat testVideoStat = videoStatList.get(videoStatList.size() - 1);
        assertThat(testVideoStat.getVideoId()).isEqualTo(UPDATED_VIDEO_ID);
        assertThat(testVideoStat.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testVideoStat.getCoachId()).isEqualTo(UPDATED_COACH_ID);
        assertThat(testVideoStat.getLiked()).isEqualTo(UPDATED_LIKED);
        assertThat(testVideoStat.getViewed()).isEqualTo(UPDATED_VIEWED);
    }

    @Test
    @Transactional
    void putNonExistingVideoStat() throws Exception {
        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();
        videoStat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videoStat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videoStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVideoStat() throws Exception {
        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();
        videoStat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoStatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videoStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVideoStat() throws Exception {
        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();
        videoStat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoStatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoStat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVideoStatWithPatch() throws Exception {
        // Initialize the database
        videoStatRepository.saveAndFlush(videoStat);

        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();

        // Update the videoStat using partial update
        VideoStat partialUpdatedVideoStat = new VideoStat();
        partialUpdatedVideoStat.setId(videoStat.getId());

        partialUpdatedVideoStat.videoId(UPDATED_VIDEO_ID).categoryId(UPDATED_CATEGORY_ID).coachId(UPDATED_COACH_ID).liked(UPDATED_LIKED);

        restVideoStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideoStat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideoStat))
            )
            .andExpect(status().isOk());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
        VideoStat testVideoStat = videoStatList.get(videoStatList.size() - 1);
        assertThat(testVideoStat.getVideoId()).isEqualTo(UPDATED_VIDEO_ID);
        assertThat(testVideoStat.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testVideoStat.getCoachId()).isEqualTo(UPDATED_COACH_ID);
        assertThat(testVideoStat.getLiked()).isEqualTo(UPDATED_LIKED);
        assertThat(testVideoStat.getViewed()).isEqualTo(DEFAULT_VIEWED);
    }

    @Test
    @Transactional
    void fullUpdateVideoStatWithPatch() throws Exception {
        // Initialize the database
        videoStatRepository.saveAndFlush(videoStat);

        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();

        // Update the videoStat using partial update
        VideoStat partialUpdatedVideoStat = new VideoStat();
        partialUpdatedVideoStat.setId(videoStat.getId());

        partialUpdatedVideoStat
            .videoId(UPDATED_VIDEO_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .coachId(UPDATED_COACH_ID)
            .liked(UPDATED_LIKED)
            .viewed(UPDATED_VIEWED);

        restVideoStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideoStat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideoStat))
            )
            .andExpect(status().isOk());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
        VideoStat testVideoStat = videoStatList.get(videoStatList.size() - 1);
        assertThat(testVideoStat.getVideoId()).isEqualTo(UPDATED_VIDEO_ID);
        assertThat(testVideoStat.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testVideoStat.getCoachId()).isEqualTo(UPDATED_COACH_ID);
        assertThat(testVideoStat.getLiked()).isEqualTo(UPDATED_LIKED);
        assertThat(testVideoStat.getViewed()).isEqualTo(UPDATED_VIEWED);
    }

    @Test
    @Transactional
    void patchNonExistingVideoStat() throws Exception {
        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();
        videoStat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, videoStat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(videoStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVideoStat() throws Exception {
        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();
        videoStat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoStatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(videoStat))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVideoStat() throws Exception {
        int databaseSizeBeforeUpdate = videoStatRepository.findAll().size();
        videoStat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoStatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(videoStat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VideoStat in the database
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVideoStat() throws Exception {
        // Initialize the database
        videoStatRepository.saveAndFlush(videoStat);

        int databaseSizeBeforeDelete = videoStatRepository.findAll().size();

        // Delete the videoStat
        restVideoStatMockMvc
            .perform(delete(ENTITY_API_URL_ID, videoStat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VideoStat> videoStatList = videoStatRepository.findAll();
        assertThat(videoStatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
