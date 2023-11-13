package com.peecko.admin.domain;

import static com.peecko.admin.domain.SecuredRequestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecuredRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecuredRequest.class);
        SecuredRequest securedRequest1 = getSecuredRequestSample1();
        SecuredRequest securedRequest2 = new SecuredRequest();
        assertThat(securedRequest1).isNotEqualTo(securedRequest2);

        securedRequest2.setId(securedRequest1.getId());
        assertThat(securedRequest1).isEqualTo(securedRequest2);

        securedRequest2 = getSecuredRequestSample2();
        assertThat(securedRequest1).isNotEqualTo(securedRequest2);
    }
}
