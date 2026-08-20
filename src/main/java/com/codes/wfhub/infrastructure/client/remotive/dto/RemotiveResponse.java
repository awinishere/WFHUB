package com.codes.wfhub.infrastructure.client.remotive.dto;

import java.util.List;

public record RemotiveResponse(
        List<RemotiveJobRaw> jobs
) {
}
