package com.yann.scriptum.service;

import com.yann.scriptum.enums.JobStatus;
import com.yann.scriptum.model.MediaFile;
import com.yann.scriptum.model.TranscriptionJob;
import com.yann.scriptum.repository.TranscriptionJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobOrchestratorService {

    private final TranscriptionJobRepository jobRepository;
    private final FfmpegService ffmpegService;
    private final TranscriptionService transcriptionService;
    private final TranslationService translationService;
    private final NotificationService notificationService;

    /** Cree un nouveau job en statut PENDING et retourne immediatement son id, le traitement demarre en asynchrone. */
    @Transactional
    public TranscriptionJob startJob(MediaFile mediaFile, String targetLanguage) {
        TranscriptionJob job = new TranscriptionJob();
        job.setMediaFile(mediaFile);
        job.setStatus(JobStatus.PENDING);
        job.setTargetLanguage(targetLanguage);

        TranscriptionJob saved = jobRepository.save(job);
        processJob(saved.getId());
        return saved;
    }

    /** Traite un job de bout en bout : extraction audio, transcription, traduction optionnelle. */
    @Async
    public void processJob(UUID jobId) {
        TranscriptionJob job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalStateException("Job introuvable : " + jobId));

        try {
            job.setStatus(JobStatus.EXTRACTING_AUDIO);
            jobRepository.save(job);
            notificationService.publishProgress(job.getId(), JobStatus.EXTRACTING_AUDIO, 0);

            Path sourceFile = Path.of(job.getMediaFile().getStoredPath());
            Path audioFile = ffmpegService.extractAudio(sourceFile);

            job.setStatus(JobStatus.TRANSCRIBING);
            jobRepository.save(job);

            String rawText = transcriptionService.transcribe(job, audioFile);
            job.setRawText(rawText);

            if (job.getTargetLanguage() != null && !job.getTargetLanguage().isBlank()) {
                job.setStatus(JobStatus.TRANSLATING);
                jobRepository.save(job);
                notificationService.publishProgress(job.getId(), JobStatus.TRANSLATING, 100);

                String translated = translationService.translate(rawText, job.getTargetLanguage());
                job.setTranslatedText(translated);
            }

            job.markDone(rawText);
            jobRepository.save(job);
            notificationService.publishProgress(job.getId(), JobStatus.DONE, 100);

        } catch (Exception e) {
            log.error("Echec du traitement du job {} : {}", jobId, e.getMessage(), e);
            job.markFailed(e.getMessage());
            jobRepository.save(job);
            notificationService.publishProgress(job.getId(), JobStatus.FAILED, job.getProgress());
        }
    }

    public TranscriptionJob getById(UUID id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new com.yann.scriptum.exception.ResourceNotFoundException("TranscriptionJob", id));
    }
}