package com.junho.flow.extensionblock.controller;

import com.junho.flow.extensionblock.controller.request.FixedExtensionRequest;
import com.junho.flow.extensionblock.controller.response.CustomExtensionResponse;
import com.junho.flow.extensionblock.controller.response.FixedExtensionResponse;
import com.junho.flow.extensionblock.service.ExtensionBlockService;
import com.junho.flow.extensionblock.service.response.ExtensionResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/extension-blocks")
public class ExtensionBlockController {

    private final ExtensionBlockService extensionBlockService;

    @GetMapping("/fixed-extensions/{userId}")
    public List<FixedExtensionResponse> getFixedExtensions(
            @PathVariable("userId") @Positive @NotNull long userId
    ) {
        return FixedExtensionResponse.from(extensionBlockService.getFixedExtensions(userId));
    }

    @GetMapping("/custom-extensions/{userId}")
    public List<CustomExtensionResponse> getCustomExtensions(
            @PathVariable("userId") @Positive @NotNull long userId
    ) {
        return CustomExtensionResponse.from(extensionBlockService.getCustomExtensions(userId));
    }

    @PostMapping("/custom-extensions/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomExtensionResponse addCustomExtension(
            @PathVariable("userId") @Positive @NotNull long userId,
            @RequestParam(name = "extension") String extension
    ) {
        return CustomExtensionResponse.from(extensionBlockService.addCustomExtension(userId, extension));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload/{userId}")
    public void uploadFile(
            @PathVariable("userId") @Positive @NotNull long userId,
            @RequestPart(name = "file") MultipartFile file
    ) {
        extensionBlockService.uploadFile(userId, file);
    }

    @PatchMapping("/custom-extensions/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFixedExtensionStatus(
            @PathVariable("userId") @Positive @NotNull long userId,
            @Valid @RequestBody FixedExtensionRequest request
    ) {
        extensionBlockService.checkFixedExtension(userId, request.toInfo());
    }

    @DeleteMapping("/custom-extensions/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomExtension(
            @PathVariable("userId") @Positive @NotNull long userId,
            @RequestParam(name = "extension") List<String> extensions
    ) {
        extensionBlockService.deleteCustomExtension(userId, extensions);
    }

}
