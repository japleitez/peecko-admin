package com.peecko.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SecuredRequestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SecuredRequest getSecuredRequestSample1() {
        return new SecuredRequest().id(1L).requestId("requestId1").pinCode("pinCode1").email("email1");
    }

    public static SecuredRequest getSecuredRequestSample2() {
        return new SecuredRequest().id(2L).requestId("requestId2").pinCode("pinCode2").email("email2");
    }

    public static SecuredRequest getSecuredRequestRandomSampleGenerator() {
        return new SecuredRequest()
            .id(longCount.incrementAndGet())
            .requestId(UUID.randomUUID().toString())
            .pinCode(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
