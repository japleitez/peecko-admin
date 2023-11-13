package com.peecko.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CodeTranslationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CodeTranslation getCodeTranslationSample1() {
        return new CodeTranslation().id(1L).code("code1").translation("translation1");
    }

    public static CodeTranslation getCodeTranslationSample2() {
        return new CodeTranslation().id(2L).code("code2").translation("translation2");
    }

    public static CodeTranslation getCodeTranslationRandomSampleGenerator() {
        return new CodeTranslation()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .translation(UUID.randomUUID().toString());
    }
}
