package com.yann.scriptum.model;

import com.yann.scriptum.enums.ChunkStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "transcription_chunks")
@Getter
@Setter
public class TranscriptionChunk {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private TranscriptionJob job;

    @Column(nullable = false)
    private int chunkIndex;

    private double startTimeSeconds;

    private double endTimeSeconds;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChunkStatus status;
}