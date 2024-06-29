package com.junho.flow.extensionblock.domain;

import com.junho.flow.global.jpa.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "custom_file_extension")
public class CustomFileExtension extends BaseTimeEntity {

    protected CustomFileExtension() {}

    public CustomFileExtension(String fileExtension, Long userId) {
        validateFileExtension(fileExtension);
        this.fileExtension = fileExtension.toLowerCase();
        this.userId = userId;
    }

    private void validateFileExtension(String fileExtension) {
        if (fileExtension == null || fileExtension.isBlank()) {
            throw new IllegalArgumentException("파일의 확장자는 null이 될 수 없습니다.");
        }
        if (fileExtension.length() > 20) {
            throw new IllegalArgumentException("파일의 확장자는 20자를 넘을 수 없습니다.");
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_file_extension_id")
    private Long id;

    @Getter
    @Column(name = "file_extension", nullable = false, length = 20)
    private String fileExtension;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public boolean isRestricted() {
        return true;
    }

    public String getExtension() {
        return fileExtension.toLowerCase();
    }

}
