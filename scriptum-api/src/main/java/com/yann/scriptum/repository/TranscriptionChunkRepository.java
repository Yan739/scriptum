package com.yann.scriptum.repository;

import com.yann.scriptum.model.TranscriptionChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TranscriptionChunkRepository extends JpaRepository<TranscriptionChunk, UUID> {

    List<TranscriptionChunk> findByJobIdOrderByChunkIndexAsc(UUID jobId);
}