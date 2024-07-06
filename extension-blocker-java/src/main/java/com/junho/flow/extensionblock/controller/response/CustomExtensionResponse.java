package com.junho.flow.extensionblock.controller.response;

import com.junho.flow.extensionblock.service.response.ExtensionResult;

import java.util.List;

public record CustomExtensionResponse(
        String extension
) {

    public static List<CustomExtensionResponse> from(List<ExtensionResult> customExtensions) {
        return customExtensions.stream()
                .map(customExtension -> new CustomExtensionResponse(customExtension.extension()))
                .toList();
    }

    public static CustomExtensionResponse from(ExtensionResult customExtension) {
        return new CustomExtensionResponse(customExtension.extension());
    }

}
