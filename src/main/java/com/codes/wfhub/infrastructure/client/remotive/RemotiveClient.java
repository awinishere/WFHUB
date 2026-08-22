package com.codes.wfhub.infrastructure.client.remotive;

import com.codes.wfhub.infrastructure.client.remotive.dto.RemotiveJobRaw;
import com.codes.wfhub.infrastructure.client.remotive.dto.RemotiveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class RemotiveClient {

    private final WebClient webClient;

    public RemotiveClient(@Qualifier("remotiveWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<RemotiveJobRaw>> fetchJobs(){
        return webClient.get()
                .uri("https://remotive.com/api/remote-jobs")
                .retrieve()
                .bodyToMono(RemotiveResponse.class)
                .map(response -> response.jobs() != null ? response.jobs() : List.<RemotiveJobRaw>of())
                .doOnError(e -> log.error("Gagal mengambil data dari Remotive API: {}", e.getMessage()))
                .onErrorResume(e -> {
                    return Mono.just(List.of());
                });
    }
}
