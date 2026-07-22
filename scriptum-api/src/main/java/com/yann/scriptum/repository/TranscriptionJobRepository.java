package com.yann.scriptum.repository;

import com.yann.scriptum.enums.JobStatus;
import com.yann.scriptum.model.TranscriptionJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TranscriptionJobRepository extends JpaRepository<TranscriptionJob, UUID> {

    List<TranscriptionJob> findByStatus(JobStatus status);

    List<TranscriptionJob> findByMediaFileId(UUID mediaFileId);
}