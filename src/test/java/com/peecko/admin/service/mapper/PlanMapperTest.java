package com.peecko.admin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanMapperTest {

    private PlanMapper planMapper;

    @BeforeEach
    public void setUp() {
        planMapper = new PlanMapperImpl();
    }
}
