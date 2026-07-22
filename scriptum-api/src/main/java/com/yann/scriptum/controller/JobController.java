package com.yann.scriptum.controller;

import com.yann.scriptum.dto.JobResponse;
import com.yann.scriptum.dto.JobStartRequest;
import com.yann.scriptum.model.MediaFile;
import com.yann.scriptum.model.TranscriptionJob;
import com.yann.scriptum.service.JobOrchestratorService;
import com.yann.scriptum.service.MediaUploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobOrchestratorService jobOrchestratorService;
    private final MediaUploadService mediaUploadService;

    @PostMapping
    public ResponseEntity<JobResponse> startJob(@Valid @RequestBody JobStartRequest request) {
        MediaFile mediaFile = mediaUploadService.getById(request.mediaFileId());
        TranscriptionJob job = jobOrchestratorService.startJob(mediaFile, request.targetLanguage());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(job));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable UUID id) {
        TranscriptionJob job = jobOrchestratorService.getById(id);
        return ResponseEntity.ok(toResponse(job));
    }

    private JobResponse toResponse(TranscriptionJob job) {
        return new JobResponse(
                job.getId(),
                job.getMediaFile().getId(),
                job.getStatus(),
                job.getProgress(),
                job.getSourceLanguage(),
                job.getTargetLanguage(),
                job.getRawText(),
                job.getTranslatedText(),
                job.getErrorMessage(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
}