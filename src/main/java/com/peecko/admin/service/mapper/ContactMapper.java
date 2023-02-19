package com.peecko.admin.service.mapper;

import com.peecko.admin.domain.Company;
import com.peecko.admin.domain.Contact;
import com.peecko.admin.service.dto.CompanyDTO;
import com.peecko.admin.service.dto.ContactDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    ContactDTO toDto(Contact s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
