package com.codes.wfhub.features.job;

import com.codes.wfhub.domain.repositories.JobsRepository;
import com.codes.wfhub.infrastructure.client.remoteOK.RemoteOkService;
import com.codes.wfhub.infrastructure.client.remotive.RemotiveClient;
import com.codes.wfhub.infrastructure.client.remotive.RemotiveService;
import com.codes.wfhub.mapper.RemotiveJobMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobSyncService {

    private final RemotiveClient remotiveClient;
    private final RemotiveJobMapper remotiveJobMapper;
    private final JobsRepository jobsRepository;
    private final RemotiveService remotiveService;
    private final RemoteOkService remoteOkService;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        log.info("Initial sync on startup");
        syncAllSource();
    }

    @Scheduled(cron = "0 0 6-18/2 * * *")
    public void scheduledSync() {
        log.info("Scheduled sync triggered");
        syncAllSource();
    }
    public void syncAllSource(){
        remotiveService.syncRemotive();
        remoteOkService.syncRemoteOk();
    }
}
