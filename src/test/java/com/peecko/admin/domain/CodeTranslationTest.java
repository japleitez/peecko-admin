package com.peecko.admin.domain;

import static com.peecko.admin.domain.CodeTranslationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodeTranslationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeTranslation.class);
        CodeTranslation codeTranslation1 = getCodeTranslationSample1();
        CodeTranslation codeTranslation2 = new CodeTranslation();
        assertThat(codeTranslation1).isNotEqualTo(codeTranslation2);

        codeTranslation2.setId(codeTranslation1.getId());
        assertThat(codeTranslation1).isEqualTo(codeTranslation2);

        codeTranslation2 = getCodeTranslationSample2();
        assertThat(codeTranslation1).isNotEqualTo(codeTranslation2);
    }
}
