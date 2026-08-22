package com.codes.wfhub.mapper;

import com.codes.wfhub.domain.entities.Jobs;
import com.codes.wfhub.domain.entities.extension.CategoriesType;
import com.codes.wfhub.domain.entities.extension.JobType;
import com.codes.wfhub.domain.entities.extension.SourceType;
import com.codes.wfhub.infrastructure.client.remoteOK.dto.RemoteOkJobRaw;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class RemoteOkJobMapper {

    public Jobs toEntity(RemoteOkJobRaw raw) {
        if (raw == null) {
            return null;
        }

        Jobs job = new Jobs();
        job.setExternalId(raw.id());
        job.setSource(SourceType.REMOTEOK);
        job.setTitle(raw.position());
        job.setCompanyName(raw.company());
        job.setCompanyLogo(raw.companyLogo());
        job.setLocation(raw.location());
        job.setJobType(JobType.FULL_TIME);
        job.setCategories(mapCategory(raw.tags(), raw.position()));
        job.setTags(mapTagsToString(raw.tags()));
        job.setDescription(raw.description());
        job.setApplyUrl(raw.url());
        job.setSalaryMinimum(raw.salaryMin());
        job.setSalaryMaximum(raw.salaryMax());
        job.setPublishedAt(parseEpochDate(raw.epoch(), raw.date()));
        job.setFetchedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        return job;
    }

    private String mapTagsToString(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        return String.join(",", tags);
    }

    private CategoriesType mapCategory(List<String> tags, String position) {
        String combined = "";
        if (tags != null) {
            combined += String.join(" ", tags).toLowerCase() + " ";
        }
        if (position != null) {
            combined += position.toLowerCase();
        }

        if (combined.isBlank()) {
            return CategoriesType.OTHER;
        }

        if (combined.contains("developer") || combined.contains("program") || combined.contains("software") ||
                combined.contains("tech") || combined.contains("data") || combined.contains("code") || combined.contains("it")) {
            return CategoriesType.TECHNOLOGY_AND_IT;
        }

        if (combined.contains("engineer") || combined.contains("mechanic") || combined.contains("civil")) {
            return CategoriesType.ENGINEERING;
        }

        if (combined.contains("design") || combined.contains("creative") || combined.contains("art") ||
                combined.contains("ux") || combined.contains("ui") || combined.contains("writer") || combined.contains("copywriter")) {
            return CategoriesType.CREATIVE_AND_DESIGN;
        }

        if (combined.contains("market") || combined.contains("seo") || combined.contains("media") || combined.contains("growth")) {
            return CategoriesType.MARKETING_AND_COMMUNICATION;
        }

        if (combined.contains("sales") || combined.contains("bizdev") || combined.contains("account manager") || combined.contains("business development")) {
            return CategoriesType.SALES_AND_BIZDEV;
        }

        if (combined.contains("business") || combined.contains("operation") || combined.contains("admin") ||
                combined.contains("hr") || combined.contains("recruit") || combined.contains("product") || combined.contains("project")) {
            return CategoriesType.BUSINESS_AND_OPERATIONS;
        }

        if (combined.contains("support") || combined.contains("customer") || combined.contains("service") || combined.contains("helpdesk")) {
            return CategoriesType.CUSTOMER_SUPPORT;
        }

        if (combined.contains("instruct") || combined.contains("teach") || combined.contains("tutor") ||
                combined.contains("educat") || combined.contains("academic") || combined.contains("train")) {
            return CategoriesType.EDUCATION_AND_INSTRUCTION;
        }

        if (combined.contains("finance") || combined.contains("account") || combined.contains("audit") || combined.contains("tax")) {
            return CategoriesType.FINANCE_AND_ACCOUNTING;
        }

        return CategoriesType.OTHER;
    }

    private LocalDateTime parseEpochDate(Long epoch, String rawDate) {
        if (epoch != null && epoch > 0) {
            try {
                return LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneId.systemDefault());
            } catch (Exception ignored) {}
        }

        try {
            return LocalDateTime.parse(rawDate);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }
}