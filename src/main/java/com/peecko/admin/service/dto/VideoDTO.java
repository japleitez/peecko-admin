package com.peecko.admin.service.dto;

import com.peecko.admin.domain.enumeration.Lang;
import com.peecko.admin.domain.enumeration.LiveState;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.peecko.admin.domain.Video} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VideoDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private LiveState state;

    @NotNull
    private String name;

    @NotNull
    private String tags;

    @NotNull
    private Lang lang;

    @NotNull
    private String url;

    private CategoryDTO category;

    private CoachDTO coach;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LiveState getState() {
        return state;
    }

    public void setState(LiveState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public CoachDTO getCoach() {
        return coach;
    }

    public void setCoach(CoachDTO coach) {
        this.coach = coach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoDTO)) {
            return false;
        }

        VideoDTO videoDTO = (VideoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, videoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VideoDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", state='" + getState() + "'" +
            ", name='" + getName() + "'" +
            ", tags='" + getTags() + "'" +
            ", lang='" + getLang() + "'" +
            ", url='" + getUrl() + "'" +
            ", category=" + getCategory() +
            ", coach=" + getCoach() +
            "}";
    }
}
