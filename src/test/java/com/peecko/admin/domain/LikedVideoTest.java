package com.peecko.admin.domain;

import static com.peecko.admin.domain.LikedVideoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LikedVideoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikedVideo.class);
        LikedVideo likedVideo1 = getLikedVideoSample1();
        LikedVideo likedVideo2 = new LikedVideo();
        assertThat(likedVideo1).isNotEqualTo(likedVideo2);

        likedVideo2.setId(likedVideo1.getId());
        assertThat(likedVideo1).isEqualTo(likedVideo2);

        likedVideo2 = getLikedVideoSample2();
        assertThat(likedVideo1).isNotEqualTo(likedVideo2);
    }
}
