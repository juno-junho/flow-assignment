package com.junho.flow.global;

import com.junho.flow.extensionblock.domain.FileEntity;
import com.junho.flow.extensionblock.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.junho.flow.global.advice.ExceptionCode.EXTENSION_BLOCKED;
import static com.junho.flow.global.advice.ExceptionCode.FILE_NAME_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class FileUploader {

    private final FileRepository fileRepository;

    public void validateFileExtension(MultipartFile file, List<String> customFileExtensionsToBlock, List<String> fixedFileExtensionsToBlock) {
        String fileExtension = getFileExtension(file);
        if (customFileExtensionsToBlock.contains(fileExtension) || fixedFileExtensionsToBlock.contains(fileExtension)) {
            throw new IllegalArgumentException(EXTENSION_BLOCKED.getMessage());
        }
    }

    public String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException(FILE_NAME_NOT_FOUND.getMessage());
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    public void upload(Long userId, MultipartFile file) {
        // s3 저장 - 추후 저장 예정
        String originalName = file.getOriginalFilename();
        String generatedName = UUID.randomUUID().toString();

        // DB 저장
        FileEntity fileToUpload = new FileEntity(originalName, generatedName, getFileExtension(file), userId);
        fileRepository.save(fileToUpload);
    }

}
