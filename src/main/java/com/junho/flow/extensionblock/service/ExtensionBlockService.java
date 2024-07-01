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

import static com.junho.flow.global.advice.ExceptionCode.CUSTOM_EXTENSION_COUNT_EXCEEDED;
import static com.junho.flow.global.advice.ExceptionCode.EXTENSION_ALREADY_EXISTS;
import static com.junho.flow.global.advice.ExceptionCode.EXTENSION_NOT_FOUND;
import static com.junho.flow.global.advice.ExceptionCode.FAILED_TO_READ_FILE;

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
        fileUploader.upload(userId, file);
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
            byte[] bytes = inputStream.readNBytes(10); // 최대 10바이트까지만 읽어서 검증
            FileExtension.validateSignature(bytes, customFileExtensionsToBlock, fixedFileExtensionsToBlock);
        } catch (IOException e) {
            throw new IllegalArgumentException(FAILED_TO_READ_FILE.toString());
        }
    }

    @Transactional
    public ExtensionResult addCustomExtension(long userId, String extension) {
        String extensionToSave = extension.trim().toLowerCase();
        validateCustomExtension(userId, extensionToSave);
        CustomFileExtension savedCustomExtension = customFileExtensionRepository.save(new CustomFileExtension(extension, userId));
        return ExtensionResult.of(savedCustomExtension);
    }

    private void validateCustomExtension(long userId, String extensionToSave) {
        if (customFileExtensionRepository.countByUserId(userId) >= CustomFileExtension.MAX_COUNT) {
            throw new IllegalArgumentException(CUSTOM_EXTENSION_COUNT_EXCEEDED.getMessage());
        }
        if (customFileExtensionRepository.existsByUserIdAndFileExtension(userId, extensionToSave)) {
            throw new IllegalArgumentException(EXTENSION_ALREADY_EXISTS.getMessage());
        }
    }

    @Transactional
    public void deleteCustomExtension(long userId, List<String> extension) {
        customFileExtensionRepository.deleteByUserIdAndFileExtensionIn(userId, extension);
    }

    @Transactional
    public void checkFixedExtension(long userId, FixedExtensionInfo extensionInfo) {
        String extension = extensionInfo.extension();

        FixedFileExtension fixedFileExtension = fixedFileExtensionRepository.findByFileExtensionAndUserId(FileExtension.from(extension), userId)
                .orElseThrow(() -> new IllegalArgumentException(EXTENSION_NOT_FOUND.getMessage()));

        fixedFileExtension.check(extensionInfo.isChecked()); // dirty checking
    }

}
