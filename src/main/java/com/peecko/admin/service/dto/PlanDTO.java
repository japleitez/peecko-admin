package com.peecko.admin.service.dto;

import com.peecko.admin.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.peecko.admin.domain.Plan} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanDTO implements Serializable {

    private Long id;

    @NotNull
    private YesNo trial;

    @NotNull
    private YesNo active;

    @NotNull
    private Double price;

    @NotNull
    private LocalDate start;

    private LocalDate end;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YesNo getTrial() {
        return trial;
    }

    public void setTrial(YesNo trial) {
        this.trial = trial;
    }

    public YesNo getActive() {
        return active;
    }

    public void setActive(YesNo active) {
        this.active = active;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
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
        if (!(o instanceof PlanDTO)) {
            return false;
        }

        PlanDTO planDTO = (PlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, planDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanDTO{" +
            "id=" + getId() +
            ", trial='" + getTrial() + "'" +
            ", active='" + getActive() + "'" +
            ", price=" + getPrice() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", company=" + getCompany() +
            "}";
    }
}
