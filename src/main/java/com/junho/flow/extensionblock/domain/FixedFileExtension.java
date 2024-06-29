package com.junho.flow.extensionblock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fixed_file_extension")
public class FixedFileExtension {

    protected FixedFileExtension(){}

    public FixedFileExtension(FileExtension fileExtension, Long userId) {
        this.fileExtension = fileExtension;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fixed_file_extension_id")
    private Long id;

    @Column(name = "file_extension", nullable = false)
    private FileExtension fileExtension;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "user_id", nullable = false)
    private Long userId;


}
