package com.junho.flow.extensionblock.controller.request;

import com.junho.flow.extensionblock.service.request.FixedExtensionInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FixedExtensionRequest(
        @NotNull @NotBlank String extension,
        boolean isChecked
) {

    public FixedExtensionInfo toInfo() {
        return new FixedExtensionInfo(extension, isChecked);
    }

}
