package com.codes.wfhub.infrastructure.client.remotive.dto;

import java.util.List;

public record RemotiveJobRaw(
        Long id,
        String url,
        String title,
        String company_name,
        String company_logo,
        String category,
        String job_type,
        String publication_date,
        String candidate_required_location,
        String salary,
        String description,
        List<String> tags
) {
}
