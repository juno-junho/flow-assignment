package com.junho.flow.extensionblock.domain;

import com.junho.flow.global.jpa.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * File 이름 사용 시 충돌 발생으로 FileEntity로 이름 변경
 */
@Entity
@Table(name = "file")
public class FileEntity extends BaseTimeEntity {

    protected FileEntity() {}

    public FileEntity(String uploadFileName, String generatedFileName, String fileExtension, Long userId) {
        this.uploadFileName = uploadFileName;
        this.generatedFileName = generatedFileName;
        this.fileExtension = fileExtension;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uploadFileName;

    @Column(nullable = false)
    private String generatedFileName;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private Long userId;

}
