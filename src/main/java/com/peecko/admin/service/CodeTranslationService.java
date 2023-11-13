package com.peecko.admin.service;

import com.peecko.admin.domain.CodeTranslation;
import com.peecko.admin.repository.CodeTranslationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.CodeTranslation}.
 */
@Service
@Transactional
public class CodeTranslationService {

    private final Logger log = LoggerFactory.getLogger(CodeTranslationService.class);

    private final CodeTranslationRepository codeTranslationRepository;

    public CodeTranslationService(CodeTranslationRepository codeTranslationRepository) {
        this.codeTranslationRepository = codeTranslationRepository;
    }

    /**
     * Save a codeTranslation.
     *
     * @param codeTranslation the entity to save.
     * @return the persisted entity.
     */
    public CodeTranslation save(CodeTranslation codeTranslation) {
        log.debug("Request to save CodeTranslation : {}", codeTranslation);
        return codeTranslationRepository.save(codeTranslation);
    }

    /**
     * Update a codeTranslation.
     *
     * @param codeTranslation the entity to save.
     * @return the persisted entity.
     */
    public CodeTranslation update(CodeTranslation codeTranslation) {
        log.debug("Request to update CodeTranslation : {}", codeTranslation);
        return codeTranslationRepository.save(codeTranslation);
    }

    /**
     * Partially update a codeTranslation.
     *
     * @param codeTranslation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CodeTranslation> partialUpdate(CodeTranslation codeTranslation) {
        log.debug("Request to partially update CodeTranslation : {}", codeTranslation);

        return codeTranslationRepository
            .findById(codeTranslation.getId())
            .map(existingCodeTranslation -> {
                if (codeTranslation.getCode() != null) {
                    existingCodeTranslation.setCode(codeTranslation.getCode());
                }
                if (codeTranslation.getLang() != null) {
                    existingCodeTranslation.setLang(codeTranslation.getLang());
                }
                if (codeTranslation.getTranslation() != null) {
                    existingCodeTranslation.setTranslation(codeTranslation.getTranslation());
                }

                return existingCodeTranslation;
            })
            .map(codeTranslationRepository::save);
    }

    /**
     * Get all the codeTranslations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CodeTranslation> findAll(Pageable pageable) {
        log.debug("Request to get all CodeTranslations");
        return codeTranslationRepository.findAll(pageable);
    }

    /**
     * Get one codeTranslation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CodeTranslation> findOne(Long id) {
        log.debug("Request to get CodeTranslation : {}", id);
        return codeTranslationRepository.findById(id);
    }

    /**
     * Delete the codeTranslation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CodeTranslation : {}", id);
        codeTranslationRepository.deleteById(id);
    }
}
