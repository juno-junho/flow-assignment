package com.junho.flow.extensionblock.service;

import com.junho.flow.extensionblock.service.request.FixedExtensionInfo;
import com.junho.flow.extensionblock.service.response.ExtensionResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExtensionBlockService {

    public List<ExtensionResult> getFixedExtensions(Long userId) {
        return null;
    }

    public List<ExtensionResult> getCustomExtensions(long userId) {
        return null;
    }

    public void uploadFile(long userId, MultipartFile file) {

    }

    public ExtensionResult addCustomExtension(long userId, String extension) {
        return null;
    }

    public void deleteCustomExtension(long userId, List<String> extension) {

    }

    public void checkFixedExtension(long userId, FixedExtensionInfo extensionInfo) {

    }

}
