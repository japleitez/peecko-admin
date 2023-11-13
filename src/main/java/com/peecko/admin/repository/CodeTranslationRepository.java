package com.peecko.admin.repository;

import com.peecko.admin.domain.CodeTranslation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CodeTranslation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeTranslationRepository extends JpaRepository<CodeTranslation, Long> {}
