package com.junho.flow.global;

import com.junho.flow.extensionblock.domain.FileEntity;
import com.junho.flow.extensionblock.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploader {

    private final FileRepository fileRepository;

    public void validateFileExtension(MultipartFile file, List<String> customFileExtensionsToBlock, List<String> fixedFileExtensionsToBlock) {
        String fileExtension = getFileExtension(file);
        if (customFileExtensionsToBlock.contains(fileExtension) || fixedFileExtensionsToBlock.contains(fileExtension)) {
            throw new IllegalArgumentException("업로드 불가!!");
        }
    }

    public String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("파일 이름이 존재하지 않습니다.");
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    @Transactional
    public void upload(MultipartFile file) {
        // s3 저장
        String originalName = file.getOriginalFilename();
        String generatedName = UUID.randomUUID().toString();

        // DB 저장
        FileEntity fileToUpload = new FileEntity(originalName, generatedName, getFileExtension(file));
        fileRepository.save(fileToUpload);
    }

}
