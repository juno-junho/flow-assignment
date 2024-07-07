package com.junho.flow.extensionblock.domain.repository;

import com.junho.flow.extensionblock.domain.CustomFileExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomFileExtensionRepository extends JpaRepository<CustomFileExtension, Long>{

    @Transactional(readOnly = true)
    List<CustomFileExtension> findByUserId(long userId);

    @Modifying(clearAutomatically = true)
    void deleteByUserIdAndFileExtensionIn(long userId, List<String> extension);

    boolean existsByUserIdAndFileExtension(long userId, String extension);

    int countByUserId(long userId);

}
