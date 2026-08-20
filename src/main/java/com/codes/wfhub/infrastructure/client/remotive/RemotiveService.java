package com.codes.wfhub.infrastructure.client.remotive;

import com.codes.wfhub.domain.entities.Jobs;
import com.codes.wfhub.domain.entities.extension.SourceType;
import com.codes.wfhub.domain.repositories.JobsRepository;
import com.codes.wfhub.mapper.RemotiveJobMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RemotiveService {

    private final RemotiveClient remotiveClient;
    private final RemotiveJobMapper remotiveJobMapper;
    private final JobsRepository jobsRepository;

    public RemotiveService(
            RemotiveClient remotiveClient,
            RemotiveJobMapper remotiveJobMapper,
            JobsRepository jobsRepository) {
        this.remotiveJobMapper = remotiveJobMapper;
        this.remotiveClient = remotiveClient;
        this.jobsRepository = jobsRepository;
    }

    @Transactional
    public void syncRemotive(){
        List<Jobs> rawJobs = remotiveClient.fetchJobs()
                .block()
                .stream()
                .map(remotiveJobMapper::toEntity)
                .toList();

        int inserted = 0, updated = 0;
        for (Jobs job : rawJobs){
            var existing = jobsRepository.findByExternalIdAndSource(job.getExternalId(), SourceType.REMOTIVE);
            if (existing.isPresent()){
                Jobs toUpdate = existing.get();
                toUpdate.setTitle(job.getTitle());
                toUpdate.setDescription(job.getDescription());
                toUpdate.setApplyUrl(job.getApplyUrl());
                toUpdate.setUpdatedAt(job.getUpdatedAt());
                jobsRepository.save(toUpdate);
                updated++;
            } else {
                jobsRepository.save(job);
                inserted++;
            }
        }
        log.info("Remotive sync done: {} inserted, {} updated", inserted, updated);
    }

}
