package com.junho.flow.global;

import com.junho.flow.extensionblock.domain.FixedFileExtension;
import com.junho.flow.extensionblock.domain.repository.FixedFileExtensionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.junho.flow.extensionblock.domain.FileExtension.BAT;
import static com.junho.flow.extensionblock.domain.FileExtension.CMD;
import static com.junho.flow.extensionblock.domain.FileExtension.COM;
import static com.junho.flow.extensionblock.domain.FileExtension.CPL;
import static com.junho.flow.extensionblock.domain.FileExtension.EXE;
import static com.junho.flow.extensionblock.domain.FileExtension.JS;
import static com.junho.flow.extensionblock.domain.FileExtension.SCR;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixedFileExtensionLoader implements ApplicationRunner {

    private final FixedFileExtensionRepository fixedFileExtensionRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("FixedFileExtensionLoader Started!!");
        Long userId = 1L; // 인증 및 인가 고려 x

        fixedFileExtensionRepository.save(new FixedFileExtension(BAT, userId));
        fixedFileExtensionRepository.save(new FixedFileExtension(CMD, userId));
        fixedFileExtensionRepository.save(new FixedFileExtension(COM, userId));
        fixedFileExtensionRepository.save(new FixedFileExtension(CPL, userId));
        fixedFileExtensionRepository.save(new FixedFileExtension(EXE, userId));
        fixedFileExtensionRepository.save(new FixedFileExtension(SCR, userId));
        fixedFileExtensionRepository.save(new FixedFileExtension(JS, userId));
    }

}
