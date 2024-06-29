package com.junho.flow.extensionblock.service;

import com.junho.flow.extensionblock.domain.CustomFileExtension;
import com.junho.flow.extensionblock.domain.FileExtension;
import com.junho.flow.extensionblock.domain.FixedFileExtension;
import com.junho.flow.extensionblock.domain.repository.CustomFileExtensionRepository;
import com.junho.flow.extensionblock.domain.repository.FixedFileExtensionRepository;
import com.junho.flow.extensionblock.service.request.FixedExtensionInfo;
import com.junho.flow.extensionblock.service.response.ExtensionResult;
import com.junho.flow.global.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtensionBlockService {

    private final FixedFileExtensionRepository fixedFileExtensionRepository;
    private final CustomFileExtensionRepository customFileExtensionRepository;
    private final FileUploader fileUploader;

    public List<ExtensionResult> getFixedExtensions(Long userId) {
        return fixedFileExtensionRepository.findByUserId(userId).stream()
                .map(ExtensionResult::of)
                .toList();
    }

    public List<ExtensionResult> getCustomExtensions(long userId) {
        return customFileExtensionRepository.findByUserId(userId).stream()
                .map(ExtensionResult::of)
                .toList();
    }

    public void uploadFile(long userId, MultipartFile file) {
        validateFileToBlock(userId, file);

        // 업로드
        fileUploader.upload(file);
    }

    private void validateFileToBlock(long userId, MultipartFile file) {
        List<String> customFileExtensionsToBlock = customFileExtensionRepository.findByUserId(userId).stream()
                .map(CustomFileExtension::getFileExtension)
                .toList();

        List<String> fixedFileExtensionsToBlock = fixedFileExtensionRepository.checkedExtensions(userId).stream()
                .map(FixedFileExtension::getFileExtension)
                .toList();

        // 확장자 검증
        fileUploader.validateFileExtension(file, customFileExtensionsToBlock, fixedFileExtensionsToBlock);

        // 파일 시그니처 검증
        try (InputStream inputStream = file.getInputStream()) {
            FileExtension.validateSignature(inputStream, customFileExtensionsToBlock, fixedFileExtensionsToBlock);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ExtensionResult addCustomExtension(long userId, String extension) {
        CustomFileExtension savedCustomExtension = customFileExtensionRepository.save(new CustomFileExtension(extension, userId));
        return ExtensionResult.of(savedCustomExtension);
    }

    @Transactional
    public void deleteCustomExtension(long userId, List<String> extension) {
        customFileExtensionRepository.deleteByUserIdAndFileExtensionIn(userId, extension);
    }

    @Transactional
    public void checkFixedExtension(long userId, FixedExtensionInfo extensionInfo) {
        String extension = extensionInfo.extension();

        FixedFileExtension fixedFileExtension = fixedFileExtensionRepository.findByFileExtensionAndUserId(FileExtension.from(extension), userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 확장자입니다."));

        fixedFileExtension.check(extensionInfo.isChecked()); // dirty checking
    }

}
