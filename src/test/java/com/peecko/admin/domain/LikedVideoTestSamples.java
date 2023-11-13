package com.peecko.admin.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LikedVideoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LikedVideo getLikedVideoSample1() {
        return new LikedVideo().id(1L).videoId(1L).personId(1L).coachId(1L);
    }

    public static LikedVideo getLikedVideoSample2() {
        return new LikedVideo().id(2L).videoId(2L).personId(2L).coachId(2L);
    }

    public static LikedVideo getLikedVideoRandomSampleGenerator() {
        return new LikedVideo()
            .id(longCount.incrementAndGet())
            .videoId(longCount.incrementAndGet())
            .personId(longCount.incrementAndGet())
            .coachId(longCount.incrementAndGet());
    }
}
