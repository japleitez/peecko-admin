package com.peecko.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peecko.admin.domain.enumeration.Lang;
import com.peecko.admin.domain.enumeration.LiveState;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Video.
 */
@Entity
@Table(name = "video")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Video implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private LiveState state;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "tags", nullable = false)
    private String tags;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lang", nullable = false)
    private Lang lang;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @JsonIgnoreProperties(value = { "videos" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "videos" }, allowSetters = true)
    private Coach coach;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Video id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Video code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LiveState getState() {
        return this.state;
    }

    public Video state(LiveState state) {
        this.setState(state);
        return this;
    }

    public void setState(LiveState state) {
        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    public Video name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return this.tags;
    }

    public Video tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Lang getLang() {
        return this.lang;
    }

    public Video lang(Lang lang) {
        this.setLang(lang);
        return this;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getUrl() {
        return this.url;
    }

    public Video url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Video category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Coach getCoach() {
        return this.coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Video coach(Coach coach) {
        this.setCoach(coach);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Video)) {
            return false;
        }
        return id != null && id.equals(((Video) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Video{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", state='" + getState() + "'" +
            ", name='" + getName() + "'" +
            ", tags='" + getTags() + "'" +
            ", lang='" + getLang() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
