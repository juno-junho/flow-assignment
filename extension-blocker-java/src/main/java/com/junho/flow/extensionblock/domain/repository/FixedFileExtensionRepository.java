package com.junho.flow.extensionblock.domain.repository;

import com.junho.flow.extensionblock.domain.FileExtension;
import com.junho.flow.extensionblock.domain.FixedFileExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FixedFileExtensionRepository extends JpaRepository<FixedFileExtension, Long> {

    List<FixedFileExtension> findByUserId(Long userId);

    Optional<FixedFileExtension> findByFileExtensionAndUserId(FileExtension fileExtension, Long userId);

    @Transactional(readOnly = true)
    @Query("select f from FixedFileExtension f where f.userId = :userId and f.isRestricted = true")
    List<FixedFileExtension> checkedExtensions(@Param("userId") long userId);

}
