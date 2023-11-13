package com.peecko.admin.domain;

import static com.peecko.admin.domain.ApsAccountTestSamples.*;
import static com.peecko.admin.domain.ApsDeviceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApsDeviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApsDevice.class);
        ApsDevice apsDevice1 = getApsDeviceSample1();
        ApsDevice apsDevice2 = new ApsDevice();
        assertThat(apsDevice1).isNotEqualTo(apsDevice2);

        apsDevice2.setId(apsDevice1.getId());
        assertThat(apsDevice1).isEqualTo(apsDevice2);

        apsDevice2 = getApsDeviceSample2();
        assertThat(apsDevice1).isNotEqualTo(apsDevice2);
    }

    @Test
    void apsAccountTest() throws Exception {
        ApsDevice apsDevice = getApsDeviceRandomSampleGenerator();
        ApsAccount apsAccountBack = getApsAccountRandomSampleGenerator();

        apsDevice.setApsAccount(apsAccountBack);
        assertThat(apsDevice.getApsAccount()).isEqualTo(apsAccountBack);

        apsDevice.apsAccount(null);
        assertThat(apsDevice.getApsAccount()).isNull();
    }
}
