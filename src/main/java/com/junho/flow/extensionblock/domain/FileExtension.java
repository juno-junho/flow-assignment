package com.junho.flow.extensionblock.domain;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static com.junho.flow.global.advice.ExceptionCode.EXTENSION_NOT_FOUND;
import static com.junho.flow.global.advice.ExceptionCode.EXTENSION_VALIDATION_FAILED;
import static com.junho.flow.global.advice.ExceptionCode.FILE_UPLOAD_FAILED;

@Getter
public enum FileExtension {

    // 고정 확장자
    BAT(new byte[]{}, true),
    CMD(new byte[]{}, true),
    COM(new byte[]{(byte) 0xC9}, true),
    CPL(new byte[]{(byte) 0x4D, (byte) 0x5A}, true),
    EXE(new byte[]{(byte) 0x4D, (byte) 0x5A}, true),
    SCR(new byte[]{(byte) 0x4D, (byte) 0x5A}, true),
    JS(new byte[]{}, true),

    // 보안상으로 위험한 확장자 - 파일시그니처 출처: https://en.wikipedia.org/wiki/List_of_file_signatures
    // 프로그램 관련 확장자, 실행 가능 파일
    PIF(new byte[]{(byte) 0x00}, false),
    APPLICATION(new byte[]{}, false),
    GADGET(new byte[]{}, false),
    MSI(new byte[]{}, false),
    MSP(new byte[]{0x44, 0x61, 0x6E, 0x4D}, false), // 44 61 6E 4D
    HTA(new byte[]{}, false),
    MSC(new byte[]{0X3C, 0X3F, 0X78, 0X6D, 0X6C, 0X20, 0X76, 0X65}, false), // 3C, 3F, 78, 6D, 6C, 20, 76, 65
    JAR(new byte[]{0x50, 0x4B, 0x03, 0x04}, false), // 50 4B 03 04
    DLL(new byte[]{0x4D, 0x5A}, false), // 4D 5A

    // 스크립트 관련 확장자
    VB(new byte[]{}, false),
    VBS(new byte[]{}, false),
    VBE(new byte[]{0x23, 0x40, 0x7E, 0x5E}, false), // 23 40 7E 5E
    JSE(new byte[]{}, false),
    WS(new byte[]{0x1D, 0x7D}, false), //  1D 7D
    WSF(new byte[]{}, false),
    WSC(new byte[]{}, false),
    WSH(new byte[]{}, false),
    PS1(new byte[]{}, false),
    PS1XML(new byte[]{}, false),
    PS2(new byte[]{}, false),
    PS2XML(new byte[]{}, false),
    PSC1(new byte[]{}, false),
    PSC2(new byte[]{}, false),
    MSH(new byte[]{}, false),
    MSH1(new byte[]{}, false),
    MSH2(new byte[]{}, false),
    MSHXML(new byte[]{}, false),
    MSH1XML(new byte[]{}, false),
    MSH2XML(new byte[]{}, false),

    // 오피스 매크로
    DOC(new byte[]{(byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0, (byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1}, false), // D0 CF 11 E0 A1 B1 1A E1
    XLS(new byte[]{(byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0, (byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1}, false), // D0 CF 11 E0 A1 B1 1A E1
    PPT(new byte[]{(byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0, (byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1}, false), // D0 CF 11 E0 A1 B1 1A E1
    DOCM(new byte[]{}, false),
    DOTM(new byte[]{}, false),
    XLSM(new byte[]{}, false),
    XLTM(new byte[]{}, false),
    XLAM(new byte[]{}, false),
    PPTM(new byte[]{}, false),
    POTM(new byte[]{}, false),
    PPAM(new byte[]{}, false),
    PPSM(new byte[]{}, false),
    SLDM(new byte[]{}, false),

    // 기타 (바로가기, 압축파일 등)
    LNK(new byte[]{0x4C, 0x00, 0x00, 0x00}, false), //  4C 00 00 00
    SCF(new byte[]{}, false),
    INF(new byte[]{}, false),
    REG(new byte[]{0x52, 0x45, 0x47, 0x45, 0x44, 0x49, 0x54}, false), //  52 45 47 45 44 49 54
    ZIP(new byte[]{0x50, 0x4B, 0x03, 0x04}, false), //50 4B 03 04
    RAR(new byte[]{0x52, 0x61, 0x72, 0x21, 0x1A, 0x07}, false), //  52 61 72 21 1A 07
    TAR(new byte[]{0x75, 0x73, 0x74, 0x61, 0x72}, false), //  75 73 74 61 72
    GZ(new byte[]{0x1F, (byte) 0x8B}, false), // 1F 8B
    ;

    private final byte[] signature;
    private final boolean isFixed;

    FileExtension(byte[] signature, boolean isFixed) {
        this.signature = signature;
        this.isFixed = isFixed;
    }

    public static List<FileExtension> getFixedExtensions() {
        return Arrays.stream(values())
                .filter(FileExtension::isFixed)
                .toList();
    }

    public static FileExtension from(String extension) {
        return Arrays.stream(values())
                .filter(fileExtension -> fileExtension.name().equalsIgnoreCase(extension))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(EXTENSION_NOT_FOUND.getMessage()));
    }

    public static void validateSignature(
            InputStream inputStream,
            List<String> customFileExtensionsToBlock,
            List<String> fixedFileExtensionsToBlock
    ) {
        inputStream.mark(0);
        Arrays.stream(values())
                .filter(fileExtension -> customFileExtensionsToBlock.contains(fileExtension.getExtension()) || fixedFileExtensionsToBlock.contains(fileExtension.getExtension()))
                .filter(fileExtension -> fileExtension.signature.length > 0)
                .forEach(fileExtension -> {
                    try {
                        inputStream.reset();
                        byte[] signatures = inputStream.readNBytes(fileExtension.signature.length);
                        if (Arrays.equals(signatures, fileExtension.signature)) {
                            throw new SecurityException(EXTENSION_VALIDATION_FAILED.getMessage());
                        }
                    } catch (IOException e) {
                        throw new IllegalArgumentException(FILE_UPLOAD_FAILED.getMessage());
                    }
                });
    }

    public String getExtension() {
        return this.name().toLowerCase();
    }

}
