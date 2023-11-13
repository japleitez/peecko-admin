package com.peecko.admin.domain;

import static com.peecko.admin.domain.PlaylistTestSamples.*;
import static com.peecko.admin.domain.VideoItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideoItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoItem.class);
        VideoItem videoItem1 = getVideoItemSample1();
        VideoItem videoItem2 = new VideoItem();
        assertThat(videoItem1).isNotEqualTo(videoItem2);

        videoItem2.setId(videoItem1.getId());
        assertThat(videoItem1).isEqualTo(videoItem2);

        videoItem2 = getVideoItemSample2();
        assertThat(videoItem1).isNotEqualTo(videoItem2);
    }

    @Test
    void playlistTest() throws Exception {
        VideoItem videoItem = getVideoItemRandomSampleGenerator();
        Playlist playlistBack = getPlaylistRandomSampleGenerator();

        videoItem.setPlaylist(playlistBack);
        assertThat(videoItem.getPlaylist()).isEqualTo(playlistBack);

        videoItem.playlist(null);
        assertThat(videoItem.getPlaylist()).isNull();
    }
}
