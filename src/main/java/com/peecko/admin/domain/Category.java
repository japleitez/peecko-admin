package com.peecko.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peecko.admin.domain.enumeration.CategoryType;
import com.peecko.admin.domain.enumeration.LiveState;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

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
    @Column(name = "type", nullable = false)
    private CategoryType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private LiveState state;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "label")
    private String label;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = { "category", "coach" }, allowSetters = true)
    private Set<Video> videos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Category code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CategoryType getType() {
        return this.type;
    }

    public Category type(CategoryType type) {
        this.setType(type);
        return this;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public LiveState getState() {
        return this.state;
    }

    public Category state(LiveState state) {
        this.setState(state);
        return this;
    }

    public void setState(LiveState state) {
        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    public Category name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return this.label;
    }

    public Category label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<Video> videos) {
        if (this.videos != null) {
            this.videos.forEach(i -> i.setCategory(null));
        }
        if (videos != null) {
            videos.forEach(i -> i.setCategory(this));
        }
        this.videos = videos;
    }

    public Category videos(Set<Video> videos) {
        this.setVideos(videos);
        return this;
    }

    public Category addVideo(Video video) {
        this.videos.add(video);
        video.setCategory(this);
        return this;
    }

    public Category removeVideo(Video video) {
        this.videos.remove(video);
        video.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            ", name='" + getName() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
