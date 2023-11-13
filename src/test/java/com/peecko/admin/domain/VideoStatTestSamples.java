package com.peecko.admin.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class VideoStatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static VideoStat getVideoStatSample1() {
        return new VideoStat().id(1L).videoId(1L).categoryId(1L).coachId(1L).liked(1).viewed(1);
    }

    public static VideoStat getVideoStatSample2() {
        return new VideoStat().id(2L).videoId(2L).categoryId(2L).coachId(2L).liked(2).viewed(2);
    }

    public static VideoStat getVideoStatRandomSampleGenerator() {
        return new VideoStat()
            .id(longCount.incrementAndGet())
            .videoId(longCount.incrementAndGet())
            .categoryId(longCount.incrementAndGet())
            .coachId(longCount.incrementAndGet())
            .liked(intCount.incrementAndGet())
            .viewed(intCount.incrementAndGet());
    }
}
