package com.yann.scriptum.model;

import com.yann.scriptum.enums.JobStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transcription_jobs")
@Getter
@Setter
public class TranscriptionJob {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "media_file_id", nullable = false)
    private MediaFile mediaFile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @Column(nullable = false)
    private int progress;

    private String sourceLanguage;

    private String targetLanguage;

    @Column(columnDefinition = "TEXT")
    private String rawText;

    @Column(columnDefinition = "TEXT")
    private String translatedText;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TranscriptionChunk> chunks = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        if (this.status == null) {
            this.status = JobStatus.PENDING;
        }
        if (this.progress == 0) {
            this.progress = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void markProcessing(JobStatus status) {
        this.status = status;
    }

    public void markDone(String text) {
        this.status = JobStatus.DONE;
        this.rawText = text;
        this.progress = 100;
    }

    public void markFailed(String reason) {
        this.status = JobStatus.FAILED;
        this.errorMessage = reason;
    }

    public void updateProgress(int progress) {
        this.progress = progress;
    }
}