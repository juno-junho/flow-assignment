package com.junho.flow.extensionblock.domain;

import com.junho.flow.global.jpa.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "file")
public class FileEntity extends BaseTimeEntity {

    protected FileEntity() {}

    public FileEntity(String uploadFileName, String generatedFileName, String fileExtension) {
        this.uploadFileName = uploadFileName;
        this.generatedFileName = generatedFileName;
        this.fileExtension = fileExtension;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "upload_file_name", nullable = false)
    private String uploadFileName;

    @Column(name = "generated_file_name", nullable = false)
    private String generatedFileName;

    @Column(name = "file_extension", nullable = false)
    private String fileExtension;

    @Column(name = "user_id", nullable = false)
    private Long userId;

}
