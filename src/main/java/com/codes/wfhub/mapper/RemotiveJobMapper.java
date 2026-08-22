package com.codes.wfhub.mapper;

import com.codes.wfhub.domain.entities.Jobs;
import com.codes.wfhub.domain.entities.extension.CategoriesType;
import com.codes.wfhub.domain.entities.extension.SourceType;
import com.codes.wfhub.infrastructure.client.remotive.dto.RemotiveJobRaw;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RemotiveJobMapper {

    public Jobs toEntity(RemotiveJobRaw raw) {
        Jobs job = new Jobs();
        job.setExternalId(String.valueOf(raw.id()));
        job.setSource(SourceType.REMOTIVE);
        job.setTitle(raw.title());
        job.setCompanyName(raw.company_name());
        job.setCompanyLogo(raw.company_logo());
        job.setLocation(raw.candidate_required_location());
        job.setCategories(mapCategory(raw.category()));
        job.setTags(raw.tags() != null ? String.join(",", raw.tags()) : "");
        job.setDescription(raw.description());
        job.setApplyUrl(raw.url());
        job.setPublishedAt(parseDate(raw.publication_date()));
        job.setFetchedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        return job;
    }

    private CategoriesType mapCategory(String rawCategory) {
        if (rawCategory == null || rawCategory.isBlank()) {
            return CategoriesType.OTHER;
        }

        String c = rawCategory.toLowerCase();

        if (c.contains("develop") || c.contains("program") || c.contains("software") ||
                c.contains("tech") || c.contains("data") || c.contains("code") || c.contains("it")) {
            return CategoriesType.TECHNOLOGY_AND_IT;
        }

        if (c.contains("engineer") || c.contains("mechanic") || c.contains("civil")) {
            return CategoriesType.ENGINEERING;
        }

        if (c.contains("design") || c.contains("creative") || c.contains("art") ||
                c.contains("ux") || c.contains("ui") || c.contains("writer") || c.contains("copywriter")) {
            return CategoriesType.CREATIVE_AND_DESIGN;
        }

        if (c.contains("market") || c.contains("seo") || c.contains("media") || c.contains("growth")) {
            return CategoriesType.MARKETING_AND_COMMUNICATION;
        }

        if (c.contains("sales") || c.contains("bizdev") || c.contains("account manager") || c.contains("business development")) {
            return CategoriesType.SALES_AND_BIZDEV;
        }

        if (c.contains("business") || c.contains("operation") || c.contains("admin") ||
                c.contains("hr") || c.contains("recruit") || c.contains("product") || c.contains("project")) {
            return CategoriesType.BUSINESS_AND_OPERATIONS;
        }

        if (c.contains("support") || c.contains("customer") || c.contains("service") || c.contains("helpdesk")) {
            return CategoriesType.CUSTOMER_SUPPORT;
        }

        if (c.contains("instruct") || c.contains("teach") || c.contains("tutor") ||
                c.contains("educat") || c.contains("academic") || c.contains("train")) {
            return CategoriesType.EDUCATION_AND_INSTRUCTION;
        }

        if (c.contains("finance") || c.contains("account") || c.contains("audit") || c.contains("tax")) {
            return CategoriesType.FINANCE_AND_ACCOUNTING;
        }

        return CategoriesType.OTHER;
    }

    private LocalDateTime parseDate(String raw) {
        try {
            return LocalDateTime.parse(raw);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }
}