package com.junho.flow.extensionblock.service.response;

import com.junho.flow.extensionblock.domain.CustomFileExtension;
import com.junho.flow.extensionblock.domain.FixedFileExtension;

public record ExtensionResult(
        String extension,
        boolean isChecked
) {

    public static ExtensionResult of(FixedFileExtension fixedFileExtension) {
        return new ExtensionResult(fixedFileExtension.getFileExtension(), fixedFileExtension.isRestricted());
    }

    public static ExtensionResult of(CustomFileExtension customFileExtension) {
        return new ExtensionResult(customFileExtension.getFileExtension(), customFileExtension.isRestricted());
    }

}
