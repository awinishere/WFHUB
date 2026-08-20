package com.codes.wfhub.features.job;

import com.codes.wfhub.domain.repositories.JobsRepository;
import com.codes.wfhub.infrastructure.client.remotive.RemotiveClient;
import com.codes.wfhub.infrastructure.client.remotive.RemotiveService;
import com.codes.wfhub.mapper.RemotiveJobMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
    public void syncAllSource(){
        remotiveService.syncRemotive();
    }
}
