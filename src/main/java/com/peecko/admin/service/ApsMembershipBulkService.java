package com.peecko.admin.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.peecko.admin.domain.ApsMembership;
import com.peecko.admin.domain.ApsOrder;
import com.peecko.admin.repository.ApsMembershipRepository;
import com.peecko.admin.service.data.Member;
import com.peecko.admin.service.dto.ApsMembershipDTO;
import com.peecko.admin.service.mapper.ApsMembershipMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ApsMembershipBulkService {

    private final ApsMembershipRepository apsMembershipRepository;
    private final ApsMembershipMapper apsMembershipMapper;

    public ApsMembershipBulkService(ApsMembershipRepository apsMembershipRepository, ApsMembershipMapper apsMembershipMapper) {
        this.apsMembershipRepository = apsMembershipRepository;
        this.apsMembershipMapper = apsMembershipMapper;
    }

    public List<ApsMembership> saveOrUpdateMemberList(ApsOrder apsOrder, List<ApsMembershipDTO> members) {
        return members.stream().map(m -> saveOrUpdateMember(apsOrder, m)).toList();
    }

    private ApsMembership saveOrUpdateMember(ApsOrder apsOrder, ApsMembershipDTO member) {
        ApsMembership apsMembership = apsMembershipRepository.findByApsOrderAndUsername(apsOrder, member.getUsername());
        if (apsMembership == null) {
            apsMembership = apsMembershipMapper.buildEntity(apsOrder, member);
        } else {
            if (Objects.equals(apsMembership.getLicense(), apsOrder.getLicense())) {
                return apsMembership;
            }
            apsMembership.setLicense(apsOrder.getLicense());
        }
        return apsMembershipRepository.save(apsMembership);
    }

    public List<ApsMembershipDTO> convertFile(ApsOrder apsOrder, final MultipartFile file) {
        List<ApsMembershipDTO> result = new ArrayList<>();
        Integer period = apsOrder.getPeriod();
        String license = apsOrder.getLicense();
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        try (InputStream is = new BufferedInputStream(file.getInputStream())) {
            MappingIterator<Member> rows = mapper.reader(Member.class).with(bootstrapSchema).readValues(is);
            rows.readAll().stream().forEach(m -> result.add(ApsMembershipDTO.of(period, license, m.getUsername())));
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert file to records", e);
        }
        return result;
    }

}
