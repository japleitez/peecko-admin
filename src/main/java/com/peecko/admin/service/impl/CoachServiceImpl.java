package com.peecko.admin.service.impl;

import com.peecko.admin.domain.Coach;
import com.peecko.admin.repository.CoachRepository;
import com.peecko.admin.service.CoachService;
import com.peecko.admin.service.dto.CoachDTO;
import com.peecko.admin.service.mapper.CoachMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Coach}.
 */
@Service
@Transactional
public class CoachServiceImpl implements CoachService {

    private final Logger log = LoggerFactory.getLogger(CoachServiceImpl.class);

    private final CoachRepository coachRepository;

    private final CoachMapper coachMapper;

    public CoachServiceImpl(CoachRepository coachRepository, CoachMapper coachMapper) {
        this.coachRepository = coachRepository;
        this.coachMapper = coachMapper;
    }

    @Override
    public CoachDTO save(CoachDTO coachDTO) {
        log.debug("Request to save Coach : {}", coachDTO);
        Coach coach = coachMapper.toEntity(coachDTO);
        coach = coachRepository.save(coach);
        return coachMapper.toDto(coach);
    }

    @Override
    public CoachDTO update(CoachDTO coachDTO) {
        log.debug("Request to update Coach : {}", coachDTO);
        Coach coach = coachMapper.toEntity(coachDTO);
        coach = coachRepository.save(coach);
        return coachMapper.toDto(coach);
    }

    @Override
    public Optional<CoachDTO> partialUpdate(CoachDTO coachDTO) {
        log.debug("Request to partially update Coach : {}", coachDTO);

        return coachRepository
            .findById(coachDTO.getId())
            .map(existingCoach -> {
                coachMapper.partialUpdate(existingCoach, coachDTO);

                return existingCoach;
            })
            .map(coachRepository::save)
            .map(coachMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CoachDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coaches");
        return coachRepository.findAll(pageable).map(coachMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CoachDTO> findOne(Long id) {
        log.debug("Request to get Coach : {}", id);
        return coachRepository.findById(id).map(coachMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coach : {}", id);
        coachRepository.deleteById(id);
    }
}
