package com.codes.wfhub.features.job.dto;

import com.codes.wfhub.domain.entities.extension.CategoriesType;
import com.codes.wfhub.domain.entities.extension.JobType;

public record JobSearchRequest(
        String keyword,
        CategoriesType category,
        JobType jobType,
        int page,
        int size
) {
    public JobSearchRequest {
        if (page < 0) page = 0;
        if (size <= 0 || size > 50) size = 20;
    }
}
