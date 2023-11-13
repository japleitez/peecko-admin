package com.peecko.admin.domain;

import static com.peecko.admin.domain.VideoStatTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideoStatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoStat.class);
        VideoStat videoStat1 = getVideoStatSample1();
        VideoStat videoStat2 = new VideoStat();
        assertThat(videoStat1).isNotEqualTo(videoStat2);

        videoStat2.setId(videoStat1.getId());
        assertThat(videoStat1).isEqualTo(videoStat2);

        videoStat2 = getVideoStatSample2();
        assertThat(videoStat1).isNotEqualTo(videoStat2);
    }
}
