package com.junho.flow.extensionblock.domain;

import com.junho.flow.global.jpa.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import static com.junho.flow.global.advice.ExceptionCode.EXTENSION_NOT_ALPHANUMERIC;
import static com.junho.flow.global.advice.ExceptionCode.EXTENSION_MAX_LENGTH;
import static com.junho.flow.global.advice.ExceptionCode.EXTENSION_NULL_OR_EMPTY;

@Entity
@Table(name = "custom_file_extension")
public class CustomFileExtension extends BaseTimeEntity {

    private static final int MAX_EXTENSION_LENGTH = 20;
    private static final String ONLY_ALPHANUMERIC = "^[a-zA-Z0-9]+$";

    protected CustomFileExtension() {}

    public CustomFileExtension(String fileExtension, Long userId) {
        validateFileExtension(fileExtension);
        this.fileExtension = fileExtension.trim().toLowerCase();
        this.userId = userId;
    }

    private void validateFileExtension(String fileExtension) {
        if (fileExtension == null || fileExtension.isBlank()) {
            throw new IllegalArgumentException(EXTENSION_NULL_OR_EMPTY.getMessage());
        }
        if (fileExtension.trim().length() > MAX_EXTENSION_LENGTH) {
            throw new IllegalArgumentException(EXTENSION_MAX_LENGTH.getMessage());
        }
        if (fileExtension.contains(" ")) {
            throw new IllegalArgumentException(EXTENSION_NOT_ALPHANUMERIC.getMessage());
        }
        if (!fileExtension.matches(ONLY_ALPHANUMERIC)) {
            throw new IllegalArgumentException(EXTENSION_NOT_ALPHANUMERIC.getMessage());
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, length = 20, unique = true)
    private String fileExtension;

    @Column(nullable = false)
    private Long userId;

    public boolean isRestricted() {
        return true;
    }

    public String getExtension() {
        return fileExtension.toLowerCase();
    }

}
