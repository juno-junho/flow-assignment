package com.junho.flow.extensionblock.controller.request;

import com.junho.flow.extensionblock.service.request.FixedExtensionInfo;

public record FixedExtensionRequest(
        String extension,
        boolean isChecked
) {

    public FixedExtensionInfo toInfo() {
        return new FixedExtensionInfo(extension, isChecked);
    }

}
