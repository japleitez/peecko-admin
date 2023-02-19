package com.peecko.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peecko.admin.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Plan.
 */
@Entity
@Table(name = "plan")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trial", nullable = false)
    private YesNo trial;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "active", nullable = false)
    private YesNo active;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "start", nullable = false)
    private LocalDate start;

    @Column(name = "jhi_end")
    private LocalDate end;

    @ManyToOne
    @JsonIgnoreProperties(value = { "contacts", "plans", "memberships" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YesNo getTrial() {
        return this.trial;
    }

    public Plan trial(YesNo trial) {
        this.setTrial(trial);
        return this;
    }

    public void setTrial(YesNo trial) {
        this.trial = trial;
    }

    public YesNo getActive() {
        return this.active;
    }

    public Plan active(YesNo active) {
        this.setActive(active);
        return this;
    }

    public void setActive(YesNo active) {
        this.active = active;
    }

    public Double getPrice() {
        return this.price;
    }

    public Plan price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public Plan start(LocalDate start) {
        this.setStart(start);
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    public Plan end(LocalDate end) {
        this.setEnd(end);
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Plan company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plan)) {
            return false;
        }
        return id != null && id.equals(((Plan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plan{" +
            "id=" + getId() +
            ", trial='" + getTrial() + "'" +
            ", active='" + getActive() + "'" +
            ", price=" + getPrice() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
