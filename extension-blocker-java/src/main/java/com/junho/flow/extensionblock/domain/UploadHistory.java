package com.junho.flow.extensionblock.domain;

import com.junho.flow.global.jpa.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 확장자 명을 바꿔 차단된 파일 확장자의 시그니처를 업로드 시도한 사용자의 IP와 사용자 ID를 저장하는 엔티티
 */
@Entity
@Table(name = "upload_history")
public class UploadHistory extends BaseTimeEntity {

    protected UploadHistory() {}

    public UploadHistory(String ipAddress, Long userId) {
        this.ipAddress = ipAddress;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private Long userId;

}
