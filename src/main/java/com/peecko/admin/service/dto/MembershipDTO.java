package com.peecko.admin.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.peecko.admin.domain.Membership} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MembershipDTO implements Serializable {

    private Long id;

    @NotNull
    private String period;

    @NotNull
    private String license;

    @NotNull
    private String email;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipDTO)) {
            return false;
        }

        MembershipDTO membershipDTO = (MembershipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, membershipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipDTO{" +
            "id=" + getId() +
            ", period='" + getPeriod() + "'" +
            ", license='" + getLicense() + "'" +
            ", email='" + getEmail() + "'" +
            ", company=" + getCompany() +
            "}";
    }
}
