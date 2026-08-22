package com.codes.wfhub.features.job;

import com.codes.wfhub.domain.entities.Jobs;
import com.codes.wfhub.domain.repositories.JobsRepository;
import com.codes.wfhub.features.job.dto.JobResponse;
import com.codes.wfhub.features.job.dto.JobSearchRequest;
import com.codes.wfhub.features.job.exception.JobNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobService {

    private final JobsRepository jobsRepository;

    public JobService(JobsRepository jobsRepository){
        this.jobsRepository = jobsRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "jobSearch", key = "#request")
    public Page<JobResponse> search(JobSearchRequest request){
        Page<Jobs> jobs = jobsRepository.search(
                request.keyword(),
                request.category(),
                request.jobType(),
                PageRequest.of(request.page(), request.size())
        );
        return jobs.map(JobResponse::fromEntity);
    }

    @Transactional
    public JobResponse findById(UUID id){
        Jobs job = jobsRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
        return JobResponse.fromEntity(job);
    }
}

