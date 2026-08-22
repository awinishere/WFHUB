package com.codes.wfhub.features.job.dto;

import com.codes.wfhub.domain.entities.extension.CategoriesType;
import com.codes.wfhub.domain.entities.extension.JobType;

public record JobSearchRequest(
        String keyword,
        CategoriesType category,
        JobType jobType,
        Integer page,
        Integer size
) {
    public JobSearchRequest {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size <= 0 || size > 50) {
            size = 20;
        }
    }
}
