package com.junho.flow.extensionblock.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileExtensionTest {

    @ParameterizedTest
    @CsvSource(value = {"bat/BAT", "cmd/CMD", "com/COM", "cpl/CPL", "exe/EXE", "scr/SCR", "js/JS"}, delimiter = '/')
    @DisplayName("파일 확장자를 반환할 때 소문자로 반환한다")
    void get_FileExtension_withLowerCase(String expectedFileExtension, FileExtension fileExtension) {
        // when
        var extension = fileExtension.getExtension();

        // then
        assertThat(extension).isEqualTo(expectedFileExtension);
    }

    @Test
    @DisplayName("고정확장자 리스트를 반환한다")
    void return_fixedFileExtensions() {
        // given
        var expectedFixedFileExtensions = List.of(
                FileExtension.BAT,
                FileExtension.CMD,
                FileExtension.COM,
                FileExtension.CPL,
                FileExtension.EXE,
                FileExtension.SCR,
                FileExtension.JS
        );

        // when
        var actualFixedFileExtensions = FileExtension.getFixedExtensions();

        // then
        assertThat(actualFixedFileExtensions).containsExactlyElementsOf(expectedFixedFileExtensions);
    }
    
    @Test
    @DisplayName("String형의 파일 확장자를 Enum으로 변환한다")
    void change_StringType_to_FileExtensionEnumType() {
        // given
        var fileExtension = "bat";
        var expectedFileExtension = FileExtension.BAT;

        // when
        var actualFileExtension = FileExtension.from(fileExtension);

        // then
        assertThat(actualFileExtension).isEqualTo(expectedFileExtension);
    }

    @Test
    @DisplayName("String형의 파일 확장자를 Enum으로 변환시 값이 존재하지 않으면 예외를 발생한다")
    void throws_IllegalArgumentException_when_converting_stringToEnum() {
        // given
        var fileExtension = "notexist";

        // when, then
        assertThatThrownBy(() -> FileExtension.from(fileExtension))
                .isInstanceOf(IllegalArgumentException.class);
    }
    

}
