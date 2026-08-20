package com.codes.wfhub.features.job.dto;

import com.codes.wfhub.domain.entities.extension.CategoriesType;
import com.codes.wfhub.domain.entities.extension.JobType;
import com.codes.wfhub.domain.entities.extension.SourceType;
import com.codes.wfhub.domain.entities.Jobs;
import java.time.LocalDateTime;
import java.util.UUID;

public record JobResponse(
        UUID id,
        String title,
        String companyName,
        String companyLogo,
        String location,
        JobType jobType,
        CategoriesType categories,
        String tags,
        String description,
        String applyUrl,
        Integer salaryMinimum,
        Integer salaryMaximum,
        SourceType source,
        LocalDateTime publishedAt
) {
    public static JobResponse fromEntity(Jobs job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getCompanyName(),
                job.getCompanyLogo(),
                job.getLocation(),
                job.getJobType(),
                job.getCategories(),
                job.getTags(),
                job.getDescription(),
                job.getApplyUrl(),
                job.getSalaryMinimum(),
                job.getSalaryMaximum(),
                job.getSource(),
                job.getPublishedAt()
        );
    }
}