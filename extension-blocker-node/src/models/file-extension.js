const fileExtension = {
    BAT: {signature: [], isFixed: true},
    CMD: {signature: [], isFixed: true},
    COM: {signature: [0xC9], isFixed: true},
    CPL: {signature: [0x4D, 0x5A], isFixed: true},
    EXE: {signature: [0x4D, 0x5A], isFixed: true},
    SCR: {signature: [0x4D, 0x5A], isFixed: true},
    JS: {signature: [], isFixed: true},

    // 프로그램 관련 확장자, 실행 가능 파일
    PIF: {signature: [0x00], isFixed: false},
    APPLICATION: {signature: [], isFixed: false},
    GADGET: {signature: [], isFixed: false},
    MSI: {signature: [], isFixed: false},
    MSP: {signature: [0x44, 0x61, 0x6E, 0x4D], isFixed: false}, // 44 61 6E },
    HTA: {signature: [], isFixed: false},
    MSC: {signature: [0X3C, 0X3F, 0X78, 0X6D, 0X6C, 0X20, 0X76, 0X65], isFixed: false}, // 3C, 3F, 78, 6D, 6C, 20, 76, },
    JAR: {signature: [0x50, 0x4B, 0x03, 0x04], isFixed: false}, // 50 4B 03 ,
    DLL: {signature: [0x4D, 0x5A], isFixed: false}, // 4D

// 스크립트 관련 확장자
    VB: {signature: [], isFixed: false},
    VBS: {signature: [], isFixed: false},
    VBE: {signature: [0x23, 0x40, 0x7E, 0x5E], isFixed: false}, // 23 40 7E 5E
    JSE: {signature: [], isFixed: false},
    WS: {signature: [0x1D, 0x7D], isFixed: false}, //  1D 7D
    WSF: {signature: [], isFixed: false},
    WSC: {signature: [], isFixed: false},
    WSH: {signature: [], isFixed: false},
    PS1: {signature: [], isFixed: false},
    PS1XML: {signature: [], isFixed: false},
    PS2: {signature: [], isFixed: false},
    PS2XML: {signature: [], isFixed: false},
    PSC1: {signature: [], isFixed: false},
    PSC2: {signature: [], isFixed: false},
    MSH: {signature: [], isFixed: false},
    MSH1: {signature: [], isFixed: false},
    MSH2: {signature: [], isFixed: false},
    MSHXML: {signature: [], isFixed: false},
    MSH1XML: {signature: [], isFixed: false},
    MSH2XML: {signature: [], isFixed: false},

// 오피스 매크로
    DOC: {signature: [0xD0, 0xCF, 0x11, 0xE0, 0xA1, 0xB1, 0x1A, 0xE1], isFixed: false}, // D0 CF 11 E0 A1 B1 1A E1
    XLS: {signature: [0xD0, 0xCF, 0x11, 0xE0, 0xA1, 0xB1, 0x1A, 0xE1], isFixed: false}, // D0 CF 11 E0 A1 B1 1A E1
    PPT: {signature: [0xD0, 0xCF, 0x11, 0xE0, 0xA1, 0xB1, 0x1A, 0xE1], isFixed: false}, // D0 CF 11 E0 A1 B1 1A E1
    DOCM: {signature: [], isFixed: false},
    DOTM: {signature: [], isFixed: false},
    XLSM: {signature: [], isFixed: false},
    XLTM: {signature: [], isFixed: false},
    XLAM: {signature: [], isFixed: false},
    PPTM: {signature: [], isFixed: false},
    POTM: {signature: [], isFixed: false},
    PPAM: {signature: [], isFixed: false},
    PPSM: {signature: [], isFixed: false},
    SLDM: {signature: [], isFixed: false},

// 기타 (바로가기, 압축파일 등)
    LNK: {signature: [0x4C, 0x00, 0x00, 0x00], isFixed: false}, //  4C 00 00 00
    SCF: {signature: [], isFixed: false},
    INF: {signature: [], isFixed: false},
    REG: {signature: [0x52, 0x45, 0x47, 0x45, 0x44, 0x49, 0x54], isFixed: false}, //  52 45 47 45 44 49 54
    ZIP: {signature: [0x50, 0x4B, 0x03, 0x04], isFixed: false}, //50 4B 03 04
    RAR: {signature: [0x52, 0x61, 0x72, 0x21, 0x1A, 0x07], isFixed: false}, //  52 61 72 21 1A 07
    TAR: {signature: [0x75, 0x73, 0x74, 0x61, 0x72], isFixed: false}, //  75 73 74 61 72
    GZ: {signature: [0x1F, 0x8B], isFixed: false} // 1F 8B
}

module.exports = fileExtension;
