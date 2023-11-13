package com.peecko.admin.domain;

import static com.peecko.admin.domain.ApsAccountTestSamples.*;
import static com.peecko.admin.domain.ApsDeviceTestSamples.*;
import static com.peecko.admin.domain.PlaylistTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApsAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApsAccount.class);
        ApsAccount apsAccount1 = getApsAccountSample1();
        ApsAccount apsAccount2 = new ApsAccount();
        assertThat(apsAccount1).isNotEqualTo(apsAccount2);

        apsAccount2.setId(apsAccount1.getId());
        assertThat(apsAccount1).isEqualTo(apsAccount2);

        apsAccount2 = getApsAccountSample2();
        assertThat(apsAccount1).isNotEqualTo(apsAccount2);
    }

    @Test
    void apsDeviceTest() throws Exception {
        ApsAccount apsAccount = getApsAccountRandomSampleGenerator();
        ApsDevice apsDeviceBack = getApsDeviceRandomSampleGenerator();

        apsAccount.addApsDevice(apsDeviceBack);
        assertThat(apsAccount.getApsDevices()).containsOnly(apsDeviceBack);
        assertThat(apsDeviceBack.getApsAccount()).isEqualTo(apsAccount);

        apsAccount.removeApsDevice(apsDeviceBack);
        assertThat(apsAccount.getApsDevices()).doesNotContain(apsDeviceBack);
        assertThat(apsDeviceBack.getApsAccount()).isNull();

        apsAccount.apsDevices(new HashSet<>(Set.of(apsDeviceBack)));
        assertThat(apsAccount.getApsDevices()).containsOnly(apsDeviceBack);
        assertThat(apsDeviceBack.getApsAccount()).isEqualTo(apsAccount);

        apsAccount.setApsDevices(new HashSet<>());
        assertThat(apsAccount.getApsDevices()).doesNotContain(apsDeviceBack);
        assertThat(apsDeviceBack.getApsAccount()).isNull();
    }

    @Test
    void playlistTest() throws Exception {
        ApsAccount apsAccount = getApsAccountRandomSampleGenerator();
        Playlist playlistBack = getPlaylistRandomSampleGenerator();

        apsAccount.addPlaylist(playlistBack);
        assertThat(apsAccount.getPlaylists()).containsOnly(playlistBack);
        assertThat(playlistBack.getApsAccount()).isEqualTo(apsAccount);

        apsAccount.removePlaylist(playlistBack);
        assertThat(apsAccount.getPlaylists()).doesNotContain(playlistBack);
        assertThat(playlistBack.getApsAccount()).isNull();

        apsAccount.playlists(new HashSet<>(Set.of(playlistBack)));
        assertThat(apsAccount.getPlaylists()).containsOnly(playlistBack);
        assertThat(playlistBack.getApsAccount()).isEqualTo(apsAccount);

        apsAccount.setPlaylists(new HashSet<>());
        assertThat(apsAccount.getPlaylists()).doesNotContain(playlistBack);
        assertThat(playlistBack.getApsAccount()).isNull();
    }
}
