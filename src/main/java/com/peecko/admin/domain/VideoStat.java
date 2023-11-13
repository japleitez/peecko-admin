package com.peecko.admin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VideoStat.
 */
@Entity
@Table(name = "video_stat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VideoStat implements Serializable {

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
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @NotNull
    @Column(name = "coach_id", nullable = false)
    private Long coachId;

    @NotNull
    @Column(name = "liked", nullable = false)
    private Integer liked;

    @NotNull
    @Column(name = "viewed", nullable = false)
    private Integer viewed;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VideoStat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return this.videoId;
    }

    public VideoStat videoId(Long videoId) {
        this.setVideoId(videoId);
        return this;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public VideoStat categoryId(Long categoryId) {
        this.setCategoryId(categoryId);
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCoachId() {
        return this.coachId;
    }

    public VideoStat coachId(Long coachId) {
        this.setCoachId(coachId);
        return this;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public Integer getLiked() {
        return this.liked;
    }

    public VideoStat liked(Integer liked) {
        this.setLiked(liked);
        return this;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public Integer getViewed() {
        return this.viewed;
    }

    public VideoStat viewed(Integer viewed) {
        this.setViewed(viewed);
        return this;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoStat)) {
            return false;
        }
        return getId() != null && getId().equals(((VideoStat) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VideoStat{" +
            "id=" + getId() +
            ", videoId=" + getVideoId() +
            ", categoryId=" + getCategoryId() +
            ", coachId=" + getCoachId() +
            ", liked=" + getLiked() +
            ", viewed=" + getViewed() +
            "}";
    }
}
