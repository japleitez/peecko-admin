package com.peecko.admin.domain;

import static com.peecko.admin.domain.ApsAccountTestSamples.*;
import static com.peecko.admin.domain.PlaylistTestSamples.*;
import static com.peecko.admin.domain.VideoItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlaylistTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Playlist.class);
        Playlist playlist1 = getPlaylistSample1();
        Playlist playlist2 = new Playlist();
        assertThat(playlist1).isNotEqualTo(playlist2);

        playlist2.setId(playlist1.getId());
        assertThat(playlist1).isEqualTo(playlist2);

        playlist2 = getPlaylistSample2();
        assertThat(playlist1).isNotEqualTo(playlist2);
    }

    @Test
    void videoItemTest() throws Exception {
        Playlist playlist = getPlaylistRandomSampleGenerator();
        VideoItem videoItemBack = getVideoItemRandomSampleGenerator();

        playlist.addVideoItem(videoItemBack);
        assertThat(playlist.getVideoItems()).containsOnly(videoItemBack);
        assertThat(videoItemBack.getPlaylist()).isEqualTo(playlist);

        playlist.removeVideoItem(videoItemBack);
        assertThat(playlist.getVideoItems()).doesNotContain(videoItemBack);
        assertThat(videoItemBack.getPlaylist()).isNull();

        playlist.videoItems(new HashSet<>(Set.of(videoItemBack)));
        assertThat(playlist.getVideoItems()).containsOnly(videoItemBack);
        assertThat(videoItemBack.getPlaylist()).isEqualTo(playlist);

        playlist.setVideoItems(new HashSet<>());
        assertThat(playlist.getVideoItems()).doesNotContain(videoItemBack);
        assertThat(videoItemBack.getPlaylist()).isNull();
    }

    @Test
    void apsAccountTest() throws Exception {
        Playlist playlist = getPlaylistRandomSampleGenerator();
        ApsAccount apsAccountBack = getApsAccountRandomSampleGenerator();

        playlist.setApsAccount(apsAccountBack);
        assertThat(playlist.getApsAccount()).isEqualTo(apsAccountBack);

        playlist.apsAccount(null);
        assertThat(playlist.getApsAccount()).isNull();
    }
}
