package com.junho.flow.global;

import com.junho.flow.extensionblock.domain.FixedFileExtension;
import com.junho.flow.extensionblock.domain.repository.FixedFileExtensionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FixedFileExtensionLoaderTest {

    @Autowired
    private FixedFileExtensionRepository fixedFileExtensionRepository;

    @Test
    @DisplayName("고정 확장자인 bat, cmd, com, cpl, exe, scr, js를 서버 로드시 DB에 저장한다.")
    void uploadFixedFileExtension() {
        // when
        List<FixedFileExtension> fixedFileExtensions = fixedFileExtensionRepository.findAll();

        // then
        assertThat(fixedFileExtensions.size()).isEqualTo(7);
    }

}
