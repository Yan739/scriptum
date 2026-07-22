package com.yann.scriptum.service;

import com.yann.scriptum.dto.MediaFileResponse;
import com.yann.scriptum.exception.UnsupportedMediaFormatException;
import com.yann.scriptum.model.MediaFile;
import com.yann.scriptum.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaUploadService {

    private static final List<String> ALLOWED_MIME_TYPES = List.of(
            "video/mp4", "audio/mpeg", "audio/wav", "audio/x-wav", "audio/mp4"
    );

    private static final long MAX_SIZE_BYTES = 500L * 1024 * 1024;

    private final MediaFileRepository mediaFileRepository;

    @Value("${scriptum.storage.path:/tmp/scriptum}")
    private String storagePath;

    @Transactional
    public MediaFileResponse upload(MultipartFile file) {
        validateFormat(file);

        String storedPath = storeFile(file);

        MediaFile media = new MediaFile();
        media.setOriginalFilename(file.getOriginalFilename());
        media.setStoredPath(storedPath);
        media.setMimeType(file.getContentType());
        media.setSizeBytes(file.getSize());

        MediaFile saved = mediaFileRepository.save(media);
        return toResponse(saved);
    }

    public void validateFormat(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new UnsupportedMediaFormatException("Fichier vide ou absent");
        }
        if (file.getSize() > MAX_SIZE_BYTES) {
            throw new UnsupportedMediaFormatException("Fichier trop volumineux (max 500 Mo)");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new UnsupportedMediaFormatException("Format non supporte : " + contentType);
        }
    }

    private String storeFile(MultipartFile file) {
        try {
            Path targetDir = Path.of(storagePath);
            Files.createDirectories(targetDir);

            String extension = getExtension(file.getOriginalFilename());
            String storedFilename = UUID.randomUUID() + extension;
            Path targetPath = targetDir.resolve(storedFilename);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier", e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    public MediaFile getById(UUID id) {
        return mediaFileRepository.findById(id)
                .orElseThrow(() -> new com.yann.scriptum.exception.ResourceNotFoundException("MediaFile", id));
    }

    private MediaFileResponse toResponse(MediaFile media) {
        return new MediaFileResponse(
                media.getId(),
                media.getOriginalFilename(),
                media.getMimeType(),
                media.getSizeBytes(),
                media.getDurationSeconds(),
                media.getUploadedAt()
        );
    }
}