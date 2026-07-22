package com.yann.scriptum.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FfmpegService {

    private static final double DEFAULT_CHUNK_DURATION_SECONDS = 600;

    /** Extrait l'audio d'un fichier (video ou audio) et le convertit en wav 16kHz mono, format optimal pour Whisper. */
    public Path extractAudio(Path sourceFile) {
        Path outputDir = sourceFile.getParent();
        Path outputFile = outputDir.resolve(UUID.randomUUID() + ".wav");

        List<String> command = List.of(
                "ffmpeg",
                "-i", sourceFile.toString(),
                "-ar", "16000",
                "-ac", "1",
                "-c:a", "pcm_s16le",
                "-y",
                outputFile.toString()
        );

        runCommand(command);
        return outputFile;
    }

    /** Recupere la duree totale du fichier audio/video en secondes. */
    public double getDurationSeconds(Path mediaFile) {
        List<String> command = List.of(
                "ffprobe",
                "-v", "error",
                "-show_entries", "format=duration",
                "-of", "default=noprint_wrappers=1:nokey=1",
                mediaFile.toString()
        );

        String output = runCommandAndCaptureOutput(command);
        try {
            return Double.parseDouble(output.trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Impossible de determiner la duree du fichier", e);
        }
    }

    /** Decoupe un fichier audio en segments de duree fixe si le fichier depasse la duree maximale autorisee par l'API de transcription. */
    public List<Path> splitIntoChunks(Path audioFile, double maxDurationSeconds) {
        double totalDuration = getDurationSeconds(audioFile);

        if (totalDuration <= maxDurationSeconds) {
            return List.of(audioFile);
        }

        List<Path> chunks = new ArrayList<>();
        Path outputDir = audioFile.getParent();
        int chunkCount = (int) Math.ceil(totalDuration / maxDurationSeconds);

        for (int i = 0; i < chunkCount; i++) {
            double startTime = i * maxDurationSeconds;
            Path chunkFile = outputDir.resolve(UUID.randomUUID() + "_chunk" + i + ".wav");

            List<String> command = List.of(
                    "ffmpeg",
                    "-i", audioFile.toString(),
                    "-ss", String.valueOf(startTime),
                    "-t", String.valueOf(maxDurationSeconds),
                    "-ar", "16000",
                    "-ac", "1",
                    "-c:a", "pcm_s16le",
                    "-y",
                    chunkFile.toString()
            );

            runCommand(command);
            chunks.add(chunkFile);
        }

        return chunks;
    }

    private void runCommand(List<String> command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            boolean finished = process.waitFor(5, TimeUnit.MINUTES);
            if (!finished) {
                process.destroyForcibly();
                throw new RuntimeException("Timeout lors de l'execution de ffmpeg");
            }

            if (process.exitValue() != 0) {
                String errorOutput = new String(process.getInputStream().readAllBytes());
                throw new RuntimeException("Erreur ffmpeg : " + errorOutput);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erreur lors de l'execution de ffmpeg", e);
        }
    }

    private String runCommandAndCaptureOutput(List<String> command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();

            String output = new String(process.getInputStream().readAllBytes());
            boolean finished = process.waitFor(1, TimeUnit.MINUTES);

            if (!finished) {
                process.destroyForcibly();
                throw new RuntimeException("Timeout lors de l'execution de ffprobe");
            }

            return output;
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erreur lors de l'execution de ffprobe", e);
        }
    }
}