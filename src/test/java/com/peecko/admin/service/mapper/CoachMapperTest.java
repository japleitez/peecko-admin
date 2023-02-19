package com.peecko.admin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoachMapperTest {

    private CoachMapper coachMapper;

    @BeforeEach
    public void setUp() {
        coachMapper = new CoachMapperImpl();
    }
}
