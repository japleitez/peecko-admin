package com.peecko.admin.service.mapper;

import com.peecko.admin.domain.Company;
import com.peecko.admin.domain.Membership;
import com.peecko.admin.service.dto.CompanyDTO;
import com.peecko.admin.service.dto.MembershipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Membership} and its DTO {@link MembershipDTO}.
 */
@Mapper(componentModel = "spring")
public interface MembershipMapper extends EntityMapper<MembershipDTO, Membership> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    MembershipDTO toDto(Membership s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
