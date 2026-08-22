package com.codes.wfhub.infrastructure.client.remoteOK.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RemoteOkJobRaw(
        @JsonProperty("id")
        String id,

        @JsonProperty("slug")
        String slug,

        @JsonProperty("epoch")
        Long epoch,

        @JsonProperty("date")
        String date,

        @JsonProperty("company")
        String company,

        @JsonProperty("company_logo")
        String companyLogo,

        @JsonProperty("position")
        String position,

        @JsonProperty("tags")
        List<String> tags,

        @JsonProperty("description")
        String description,

        @JsonProperty("location")
        String location,

        @JsonProperty("salary_min")
        Integer salaryMin,

        @JsonProperty("salary_max")
        Integer salaryMax,

        @JsonProperty("url")
        String url
) {
}