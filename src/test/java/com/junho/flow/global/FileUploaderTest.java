package com.junho.flow.global;

import com.junho.flow.extensionblock.domain.repository.FileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileUploaderTest {

    @Test
    @DisplayName("파일 확장자가 차단 목록에 존재한다면 예외를 발생한다")
    void throw_IllegalArgumentException_when_fileExtensionExistsInBlockingList() {
        // given
        var fileUploader = new FileUploader(Mockito.mock(FileRepository.class));
        var file = new MockMultipartFile("file", "test.png", "image/png", "test data".getBytes());
        var customFileExtensionsToBlock = List.of("png");
        var fixedFileExtensionsToBlock = List.of("bat", "cmd", "com", "cpl", "exe", "scr", "js");

        // when, then
        assertThatThrownBy(() -> fileUploader.validateFileExtension(file, customFileExtensionsToBlock, fixedFileExtensionsToBlock))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("파일 확장자가 차단 목록에 존재하지 않는다면 예외를 발생하지 않는다")
    void doesNotThrow_IllegalArgumentException_when_fileExtensionIsNotInBlockingList() {
        // given
        var fileUploader = new FileUploader(Mockito.mock(FileRepository.class));
        var file = new MockMultipartFile("file", "test.png", "image/png", "test data".getBytes());
        var customFileExtensionsToBlock = List.of("csv");
        var fixedFileExtensionsToBlock = List.of("bat", "cmd", "com", "cpl", "exe", "scr", "js");

        // when, then
        assertThatNoException()
                .isThrownBy(() -> fileUploader.validateFileExtension(file, customFileExtensionsToBlock, fixedFileExtensionsToBlock));
    }

    @Test
    @DisplayName("파일 확장자를 추출한다")
    void extract_fileExtension() {
        // given
        var fileUploader = new FileUploader(Mockito.mock(FileRepository.class));
        var file = new MockMultipartFile("file", "test.afef.png", "image/png", "test data".getBytes());

        // when
        var fileExtension = fileUploader.getFileExtension(file);

        // then
        assertThat(fileExtension).isEqualTo("png");
    }

}
