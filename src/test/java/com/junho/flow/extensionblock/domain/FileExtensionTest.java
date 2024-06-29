package com.junho.flow.extensionblock.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    
    @Test
    @DisplayName("차단하는 확장자의 파일 시그니처를 포함한다면 예외를 발생한다")
    void throws_securityException_when_fileSignatureMatches() {
        // given
        byte[] exeFileSignature = {0x4D, 0x5A};
        byte[] docFileSignatue = {(byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0, (byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1};
        List<String> fixedExtensionsToBlock = List.of("exe");
        List<String> customFileExtensionsToBlock = List.of("doc");

        // when
        InputStream inputStream1 = new ByteArrayInputStream(exeFileSignature);
        InputStream inputStream2 = new ByteArrayInputStream(docFileSignatue);

        // then
        assertAll(
                () -> assertThatThrownBy(() -> FileExtension.validateSignature(inputStream1, customFileExtensionsToBlock, fixedExtensionsToBlock))
                .isInstanceOf(SecurityException.class),
                () -> assertThatThrownBy(() -> FileExtension.validateSignature(inputStream2, customFileExtensionsToBlock, fixedExtensionsToBlock))
                .isInstanceOf(SecurityException.class)
        );
    }

    @Test
    @DisplayName("차단하는 확장자의 파일 시그니처를 포함하지 않는다면 예외를 발생하지 않는다")
    void doesNotThrow_securityException_when_fileSignature_doesNotMatches() {
        // given
        byte[] pngFileSignature = {(byte) 0x89, 0x50, 0x4E, 0x47};
        byte[] emptyFileSignature = {};
        List<String> fixedExtensionsToBlock = List.of("exe");
        List<String> customFileExtensionsToBlock = List.of("doc");

        // when
        InputStream inputStream1 = new ByteArrayInputStream(pngFileSignature);
        InputStream inputStream2 = new ByteArrayInputStream(emptyFileSignature);

        // then
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> FileExtension.validateSignature(inputStream1, customFileExtensionsToBlock, fixedExtensionsToBlock)),
                () -> assertThatNoException().isThrownBy(() -> FileExtension.validateSignature(inputStream2, customFileExtensionsToBlock, fixedExtensionsToBlock))
        );
    }

}
