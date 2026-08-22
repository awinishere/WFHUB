package com.codes.wfhub.infrastructure.client.remoteOK;

import com.codes.wfhub.domain.entities.Jobs;
import com.codes.wfhub.domain.entities.extension.SourceType;
import com.codes.wfhub.domain.repositories.JobsRepository;
import com.codes.wfhub.infrastructure.client.remoteOK.dto.RemoteOkJobRaw;
import com.codes.wfhub.mapper.RemoteOkJobMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteOkService {

    private final RemoteOkClient remoteOkClient;
    private final JobsRepository jobsRepository;
    private final RemoteOkJobMapper remoteOkJobMapper;

    @Transactional
    public void syncRemoteOk() {
        List<RemoteOkJobRaw> rawJobDtos = remoteOkClient.fetchJobs().block();

        if (rawJobDtos == null || rawJobDtos.isEmpty()) {
            log.info("No job data was retrieved from Remote OK.");
            return;
        }

        List<Jobs> incomingJobs = rawJobDtos.stream()
                .map(remoteOkJobMapper::toEntity)
                .filter(Objects::nonNull)
                .toList();

        int inserted = 0;
        int updated = 0;

        for (Jobs job : incomingJobs) {
            var existingOpt = jobsRepository.findByExternalIdAndSource(
                    job.getExternalId(),
                    SourceType.REMOTEOK
            );

            if (existingOpt.isPresent()) {
                Jobs toUpdate = existingOpt.get();
                toUpdate.setTitle(job.getTitle());
                toUpdate.setCompanyName(job.getCompanyName());
                toUpdate.setCompanyLogo(job.getCompanyLogo());
                toUpdate.setLocation(job.getLocation());
                toUpdate.setJobType(job.getJobType());
                toUpdate.setCategories(job.getCategories());
                toUpdate.setTags(job.getTags());
                toUpdate.setDescription(job.getDescription());
                toUpdate.setApplyUrl(job.getApplyUrl());
                toUpdate.setSalaryMinimum(job.getSalaryMinimum());
                toUpdate.setSalaryMaximum(job.getSalaryMaximum());
                toUpdate.setUpdatedAt(LocalDateTime.now());

                jobsRepository.save(toUpdate);
                updated++;
            } else {
                job.setSource(SourceType.REMOTEOK);
                job.setFetchedAt(LocalDateTime.now());
                job.setUpdatedAt(LocalDateTime.now());
                jobsRepository.save(job);
                inserted++;
            }
        }

        log.info("Remote OK sync done: {} inserted, {} updated", inserted, updated);
    }
}