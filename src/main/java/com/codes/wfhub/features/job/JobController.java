package com.codes.wfhub.features.job;

import com.codes.wfhub.features.job.dto.JobResponse;
import com.codes.wfhub.features.job.dto.JobSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final JobSyncService jobSyncService;

    @GetMapping
    public ResponseEntity<Page<JobResponse>> getAllJobs(@ModelAttribute JobSearchRequest request){
        return ResponseEntity.ok(jobService.search(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable("id")UUID id){
        return ResponseEntity.ok(jobService.findById(id));
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> triggerAsync(){
        jobSyncService.syncAllSource();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
