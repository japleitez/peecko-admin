package com.peecko.admin.service.mapper;

import com.peecko.admin.domain.Category;
import com.peecko.admin.domain.Coach;
import com.peecko.admin.domain.Video;
import com.peecko.admin.service.dto.CategoryDTO;
import com.peecko.admin.service.dto.CoachDTO;
import com.peecko.admin.service.dto.VideoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Video} and its DTO {@link VideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface VideoMapper extends EntityMapper<VideoDTO, Video> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "coach", source = "coach", qualifiedByName = "coachId")
    VideoDTO toDto(Video s);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("coachId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CoachDTO toDtoCoachId(Coach coach);
}
