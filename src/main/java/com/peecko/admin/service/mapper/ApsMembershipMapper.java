package com.peecko.admin.service.mapper;

import com.peecko.admin.domain.ApsMembership;
import com.peecko.admin.domain.ApsOrder;
import com.peecko.admin.service.dto.ApsMembershipDTO;
import org.springframework.stereotype.Service;

@Service
public class ApsMembershipMapper {
    public ApsMembership buildEntity(ApsOrder apsOrder, ApsMembershipDTO dto) {
        ApsMembership entity = new ApsMembership();
        entity.setApsOrder(new ApsOrder(apsOrder.getId()));
        entity.setLicense(apsOrder.getLicense());
        entity.setPeriod(dto.getPeriod());
        entity.setUsername(dto.getUsername());
        return entity;
    }
}
