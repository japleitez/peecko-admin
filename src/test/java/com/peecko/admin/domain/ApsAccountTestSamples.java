package com.peecko.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApsAccountTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ApsAccount getApsAccountSample1() {
        return new ApsAccount().id(1L).name("name1").username("username1").email("email1").license("license1").password("password1");
    }

    public static ApsAccount getApsAccountSample2() {
        return new ApsAccount().id(2L).name("name2").username("username2").email("email2").license("license2").password("password2");
    }

    public static ApsAccount getApsAccountRandomSampleGenerator() {
        return new ApsAccount()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .username(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .license(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString());
    }
}
