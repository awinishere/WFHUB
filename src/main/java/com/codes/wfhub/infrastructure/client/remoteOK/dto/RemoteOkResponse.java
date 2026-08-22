package com.codes.wfhub.infrastructure.client.remoteOK.dto;

import java.util.List;

public record RemoteOkResponse(
        List<RemoteOkJobRaw> jobs
) {
}
