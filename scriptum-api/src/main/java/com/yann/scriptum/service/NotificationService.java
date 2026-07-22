package com.yann.scriptum.service;

import com.yann.scriptum.dto.ProgressMessage;
import com.yann.scriptum.enums.JobStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    /** Publie la progression d'un job sur son topic WebSocket dedie. */
    public void publishProgress(UUID jobId, JobStatus status, int progress) {
        ProgressMessage message = new ProgressMessage(jobId, status, progress);
        messagingTemplate.convertAndSend("/topic/job/" + jobId, message);
        log.info("Job {} - statut : {} - progression : {}%", jobId, status, progress);
    }
}