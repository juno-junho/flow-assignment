package com.junho.flow.extensionblock.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class FileExtensionConverter implements AttributeConverter<FileExtension, String> {

    @Override
    public String convertToDatabaseColumn(FileExtension fileExtension) {
        return fileExtension == null ? null : fileExtension.getExtension();
    }

    @Override
    public FileExtension convertToEntityAttribute(String databaseData) {
        return databaseData == null ? null : FileExtension.from(databaseData);
    }

}
