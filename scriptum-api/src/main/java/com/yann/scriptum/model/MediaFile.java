package com.yann.scriptum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "media_files")
@Getter
@Setter
public class MediaFile {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String storedPath;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private long sizeBytes;

    private Integer durationSeconds;

    @Column(nullable = false, updatable = false)
    private Instant uploadedAt;

    @PrePersist
    protected void onCreate() {
        this.uploadedAt = Instant.now();
    }
}