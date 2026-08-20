package com.codes.wfhub.features.job;

import com.codes.wfhub.domain.entities.Jobs;
import com.codes.wfhub.domain.repositories.JobsRepository;
import com.codes.wfhub.features.job.dto.JobResponse;
import com.codes.wfhub.features.job.dto.JobSearchRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobsRepository jobsRepository;

    public JobService(JobsRepository jobsRepository){
        this.jobsRepository = jobsRepository;
    }

    @Transactional
    public Page<JobResponse> search(JobSearchRequest request){
        Page<Jobs> jobs = jobsRepository.search(
                request.keyword(),
                request.category(),
                request.jobType(),
                PageRequest.of(request.page(), request.size())
        );
        return jobs.map(JobResponse::fromEntity);
    }
}

