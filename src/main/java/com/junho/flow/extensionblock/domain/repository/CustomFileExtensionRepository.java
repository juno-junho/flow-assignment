package com.junho.flow.extensionblock.domain.repository;

import com.junho.flow.extensionblock.domain.CustomFileExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CustomFileExtensionRepository extends JpaRepository<CustomFileExtension, Long>{

    List<CustomFileExtension> findByUserId(long userId);

    @Modifying(clearAutomatically = true)
    void deleteByUserIdAndExtensionIn(long userId, List<String> extension);

}
