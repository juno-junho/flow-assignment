package com.junho.flow.extensionblock.controller.response;

import com.junho.flow.extensionblock.service.response.ExtensionResult;

import java.util.List;

public record FixedExtensionResponse(
        String extension,
        boolean isChecked
){

    public static List<FixedExtensionResponse> from(List<ExtensionResult> fixedExtensions) {
        return fixedExtensions.stream()
                .map(fixedExtension -> new FixedExtensionResponse(fixedExtension.extension(), fixedExtension.isChecked()))
                .toList();
    }

}
