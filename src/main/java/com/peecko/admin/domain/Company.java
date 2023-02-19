package com.peecko.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peecko.admin.domain.enumeration.ClientState;
import com.peecko.admin.domain.enumeration.Country;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private Country country;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private ClientState state;

    @NotNull
    @Column(name = "license", nullable = false)
    private String license;

    @Column(name = "vatin")
    private String vatin;

    @OneToMany(mappedBy = "company")
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private Set<Plan> plans = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private Set<Membership> memberships = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Company code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return this.country;
    }

    public Company country(Country country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public ClientState getState() {
        return this.state;
    }

    public Company state(ClientState state) {
        this.setState(state);
        return this;
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public String getLicense() {
        return this.license;
    }

    public Company license(String license) {
        this.setLicense(license);
        return this;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getVatin() {
        return this.vatin;
    }

    public Company vatin(String vatin) {
        this.setVatin(vatin);
        return this;
    }

    public void setVatin(String vatin) {
        this.vatin = vatin;
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        if (this.contacts != null) {
            this.contacts.forEach(i -> i.setCompany(null));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.setCompany(this));
        }
        this.contacts = contacts;
    }

    public Company contacts(Set<Contact> contacts) {
        this.setContacts(contacts);
        return this;
    }

    public Company addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setCompany(this);
        return this;
    }

    public Company removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setCompany(null);
        return this;
    }

    public Set<Plan> getPlans() {
        return this.plans;
    }

    public void setPlans(Set<Plan> plans) {
        if (this.plans != null) {
            this.plans.forEach(i -> i.setCompany(null));
        }
        if (plans != null) {
            plans.forEach(i -> i.setCompany(this));
        }
        this.plans = plans;
    }

    public Company plans(Set<Plan> plans) {
        this.setPlans(plans);
        return this;
    }

    public Company addPlan(Plan plan) {
        this.plans.add(plan);
        plan.setCompany(this);
        return this;
    }

    public Company removePlan(Plan plan) {
        this.plans.remove(plan);
        plan.setCompany(null);
        return this;
    }

    public Set<Membership> getMemberships() {
        return this.memberships;
    }

    public void setMemberships(Set<Membership> memberships) {
        if (this.memberships != null) {
            this.memberships.forEach(i -> i.setCompany(null));
        }
        if (memberships != null) {
            memberships.forEach(i -> i.setCompany(this));
        }
        this.memberships = memberships;
    }

    public Company memberships(Set<Membership> memberships) {
        this.setMemberships(memberships);
        return this;
    }

    public Company addMembership(Membership membership) {
        this.memberships.add(membership);
        membership.setCompany(this);
        return this;
    }

    public Company removeMembership(Membership membership) {
        this.memberships.remove(membership);
        membership.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", state='" + getState() + "'" +
            ", license='" + getLicense() + "'" +
            ", vatin='" + getVatin() + "'" +
            "}";
    }
}
