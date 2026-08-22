package com.codes.wfhub.infrastructure.client.remoteOK;

import com.codes.wfhub.infrastructure.client.remoteOK.dto.RemoteOkJobRaw;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RemoteOkClient {

    private final WebClient webClient;
    private static final String REMOTE_OK_URL = "https://remoteok.com/api";

    public RemoteOkClient(@Qualifier("remoteOkWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<RemoteOkJobRaw>> fetchJobs() {
        return webClient.get()
                .uri(REMOTE_OK_URL)
                .header(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RemoteOkJobRaw>>() {})
                .map(jobs -> {
                    return jobs.stream()
                            .filter(job -> job.id() != null && !job.id().isBlank())
                            .toList();
                })
                .onErrorResume(e -> Mono.just(List.of()));
    }
}