package com.yann.scriptum.service;

import com.yann.scriptum.enums.JobStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class NotificationService {

    /** Publie la progression d'un job, pour l'instant seulement en log en attendant le WebSocket. */
    public void publishProgress(UUID jobId, JobStatus status, int progress) {
        log.info("Job {} - statut : {} - progression : {}%", jobId, status, progress);
    }
}