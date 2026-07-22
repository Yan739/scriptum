package com.yann.scriptum.service;

import com.yann.scriptum.enums.ChunkStatus;
import com.yann.scriptum.enums.JobStatus;
import com.yann.scriptum.model.TranscriptionChunk;
import com.yann.scriptum.model.TranscriptionJob;
import com.yann.scriptum.repository.TranscriptionChunkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TranscriptionService {

    private static final double MAX_CHUNK_DURATION_SECONDS = 600; // limite Groq, marge de securite
    private static final int MAX_RETRIES = 3;

    private final FfmpegService ffmpegService;
    private final TranscriptionChunkRepository chunkRepository;
    private final NotificationService notificationService;
    private final WebClient groqWebClient;
    private final WebClient whisperLocalWebClient;

    /** Transcrit l'audio d'un job en decoupant si necessaire et en reconstruisant le texte final. */
    public String transcribe(TranscriptionJob job, Path audioFile) {
        List<Path> chunkFiles = ffmpegService.splitIntoChunks(audioFile, MAX_CHUNK_DURATION_SECONDS);

        StringBuilder fullText = new StringBuilder();

        for (int i = 0; i < chunkFiles.size(); i++) {
            Path chunkFile = chunkFiles.get(i);

            TranscriptionChunk chunk = new TranscriptionChunk();
            chunk.setJob(job);
            chunk.setChunkIndex(i);
            chunk.setStartTimeSeconds(i * MAX_CHUNK_DURATION_SECONDS);
            chunk.setStatus(ChunkStatus.PROCESSING);
            chunkRepository.save(chunk);

            String text = transcribeChunk(chunkFile);

            chunk.setText(text);
            chunk.setStatus(ChunkStatus.DONE);
            chunkRepository.save(chunk);

            fullText.append(text).append(" ");

            int progress = (int) (((i + 1) / (double) chunkFiles.size()) * 100);
            notificationService.publishProgress(job.getId(), JobStatus.TRANSCRIBING, progress);
        }

        return fullText.toString().trim();
    }

    private String transcribeChunk(Path chunkFile) {
        try {
            return callGroqApi(chunkFile);
        } catch (Exception e) {
            log.warn("Echec de l'appel Groq, bascule vers whisper local : {}", e.getMessage());
            return callLocalFallback(chunkFile);
        }
    }

    private String callGroqApi(Path chunkFile) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(chunkFile));
        builder.part("model", "whisper-large-v3");

        GroqTranscriptionResponse response = groqWebClient.post()
                .uri("/audio/transcriptions")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(org.springframework.web.reactive.function.BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(GroqTranscriptionResponse.class)
                .retryWhen(Retry.backoff(MAX_RETRIES, Duration.ofSeconds(2))
                        .filter(this::isRetryable))
                .block();

        return response != null ? response.text() : "";
    }

    private String callLocalFallback(Path chunkFile) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(chunkFile));

        GroqTranscriptionResponse response = whisperLocalWebClient.post()
                .uri("/transcribe")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(org.springframework.web.reactive.function.BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(GroqTranscriptionResponse.class)
                .block();

        return response != null ? response.text() : "";
    }

    private boolean isRetryable(Throwable throwable) {
        if (throwable instanceof WebClientResponseException ex) {
            return ex.getStatusCode().is5xxServerError() || ex.getStatusCode().value() == 429;
        }
        return false;
    }

    private record GroqTranscriptionResponse(String text) {}
}