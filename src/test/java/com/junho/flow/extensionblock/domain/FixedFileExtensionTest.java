package com.junho.flow.extensionblock.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FixedFileExtensionTest {

    @Test
    @DisplayName("고정 확장자를 소문자로 저장하고 반환한다")
    void fixedFileExtensions_are_savedLowerCase() {
        // given
        var userId = 1L;
        var docxExtension = FileExtension.EXE;
        var expectedExtension = "exe";
        var fixedFileExtension = new FixedFileExtension(docxExtension, userId);

        // when
        String actualFileExtension = fixedFileExtension.getFileExtension();

        // then
        assertThat(actualFileExtension).isEqualTo(expectedExtension);
    }

    @Test
    @DisplayName("고정 확장자를 체크를 하면 제한 대상이 된다")
    void checked_fixedExtensions_are_restricted() {
        // given
        Long userId = 1L;
        boolean checkedValue = true;
        FixedFileExtension unchecked = new FixedFileExtension(FileExtension.DOC, userId);

        // when
        unchecked.check(checkedValue);

        // then
        assertThat(unchecked.isRestricted()).isTrue();
    }

}
