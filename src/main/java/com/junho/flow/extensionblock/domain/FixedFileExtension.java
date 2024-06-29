package com.junho.flow.extensionblock.domain;

import com.junho.flow.global.jpa.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fixed_file_extension")
public class FixedFileExtension extends BaseTimeEntity {

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
    @Convert(converter = FileExtensionConverter.class)
    private FileExtension fileExtension;

    @Column(name = "restricted", nullable = false)
    private boolean isRestricted;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public String getFileExtension() {
        return fileExtension.getExtension();
    }

    public boolean isRestricted() {
        return isRestricted;
    }

    public void check(boolean checked) {
        this.isRestricted = checked;
    }

}
