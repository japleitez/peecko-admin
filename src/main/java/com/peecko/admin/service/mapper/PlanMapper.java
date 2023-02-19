package com.peecko.admin.service.mapper;

import com.peecko.admin.domain.Company;
import com.peecko.admin.domain.Plan;
import com.peecko.admin.service.dto.CompanyDTO;
import com.peecko.admin.service.dto.PlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plan} and its DTO {@link PlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanMapper extends EntityMapper<PlanDTO, Plan> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    PlanDTO toDto(Plan s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
