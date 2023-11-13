package com.peecko.admin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LikedVideo.
 */
@Entity
@Table(name = "liked_video")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LikedVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @NotNull
    @Column(name = "person_id", nullable = false)
    private Long personId;

    @NotNull
    @Column(name = "coach_id", nullable = false)
    private Long coachId;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LikedVideo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return this.videoId;
    }

    public LikedVideo videoId(Long videoId) {
        this.setVideoId(videoId);
        return this;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getPersonId() {
        return this.personId;
    }

    public LikedVideo personId(Long personId) {
        this.setPersonId(personId);
        return this;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getCoachId() {
        return this.coachId;
    }

    public LikedVideo coachId(Long coachId) {
        this.setCoachId(coachId);
        return this;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public Instant getCreated() {
        return this.created;
    }

    public LikedVideo created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LikedVideo)) {
            return false;
        }
        return getId() != null && getId().equals(((LikedVideo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikedVideo{" +
            "id=" + getId() +
            ", videoId=" + getVideoId() +
            ", personId=" + getPersonId() +
            ", coachId=" + getCoachId() +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
