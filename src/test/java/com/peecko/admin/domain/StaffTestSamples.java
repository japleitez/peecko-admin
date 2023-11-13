package com.peecko.admin.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class StaffTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Staff getStaffSample1() {
        return new Staff().id(1L).staffId(1L);
    }

    public static Staff getStaffSample2() {
        return new Staff().id(2L).staffId(2L);
    }

    public static Staff getStaffRandomSampleGenerator() {
        return new Staff().id(longCount.incrementAndGet()).staffId(longCount.incrementAndGet());
    }
}
