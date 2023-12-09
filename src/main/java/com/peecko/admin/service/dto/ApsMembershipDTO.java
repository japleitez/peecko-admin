package com.peecko.admin.service.dto;

import java.io.Serializable;

public class ApsMembershipDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private final Integer period;

    private final String license;

    private final String username;

    public ApsMembershipDTO(Long id, Integer period, String license, String username) {
        this.id = id;
        this.period = period;
        this.license = license;
        this.username = username;
    }
    public static ApsMembershipDTO of(Integer period, String license, String username) {
        return new ApsMembershipDTO(null, period, license, username);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriod() {
        return period;
    }

    public String getLicense() {
        return license;
    }

    public String getUsername() {
        return username;
    }
}
