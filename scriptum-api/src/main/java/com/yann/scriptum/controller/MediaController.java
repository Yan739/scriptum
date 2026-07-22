package com.yann.scriptum.controller;

import com.yann.scriptum.dto.MediaFileResponse;
import com.yann.scriptum.service.MediaUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaUploadService mediaUploadService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<MediaFileResponse> upload(@RequestParam("file") MultipartFile file) {
        MediaFileResponse response = mediaUploadService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}