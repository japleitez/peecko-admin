package com.peecko.admin.service.mapper;

import com.peecko.admin.domain.Coach;
import com.peecko.admin.service.dto.CoachDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Coach} and its DTO {@link CoachDTO}.
 */
@Mapper(componentModel = "spring")
public interface CoachMapper extends EntityMapper<CoachDTO, Coach> {}
