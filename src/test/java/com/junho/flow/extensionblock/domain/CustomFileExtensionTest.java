package com.junho.flow.extensionblock.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomFileExtensionTest {
    
    @Test
    @DisplayName("커스텀 파일 확장자 저장 시 소문자로 저장한다")
    void customFileExtensions_are_savedLowerCase() {
        // given
        var customFileExtension = "MD";
        var expectedCustomFileExtension = "md";
        var userId = 1L;

        // when
        var customFileExtensionEntity = new CustomFileExtension(customFileExtension, userId);

        // then
        assertThat(customFileExtensionEntity.getExtension()).isEqualTo(expectedCustomFileExtension);
        
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"   ", " ", "    "})
    @NullSource
    @DisplayName("커스텀 파일 확장자를 null이나 빈값으로 설졍하면 예외가 발생한다")
    void throwsIllegalArgumentException_when_customFileExtensionIsBlankOrNull(String customFileExtension) {
        // given
        var userId = 1L;

        // when, then
        assertThatThrownBy(() -> new CustomFileExtension(customFileExtension, userId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ab", "abcdefghijklmnopqrst"})
    @DisplayName("커스텀 파일 확장자는 20자까지 등록 가능하다")
    void customFileExtension_canBeRegistered_upTo20Letters(String customFileExtension) {
        // given
        var userId = 1L;

        // when, then
        assertThatNoException().isThrownBy(() -> new CustomFileExtension(customFileExtension, userId));
    }

    @Test
    @DisplayName("커스텀 파일 확장자는 20자를 넘을 수 없다")
    void throwsIllegalArgumentException_when_customFileExtensionIsOver20Letters() {
        // given
        var customFileExtension = "a".repeat(21);
        var userId = 1L;

        // when, then
        assertThatThrownBy(() -> new CustomFileExtension(customFileExtension, userId))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("커스텀 파일 확장자에 등록되어 있는 것은 모두 확장자 제한 대상이다")
    void all_CustomFileExtensions_are_restricted() {
        // given
        var customFileExtension = new CustomFileExtension("test", 1L);

        // when
        var isRestricted = customFileExtension.isRestricted();

        // then
        assertThat(isRestricted).isTrue();
    }

}
