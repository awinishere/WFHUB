package com.codes.wfhub.infrastructure.client.remotive;

import com.codes.wfhub.infrastructure.client.remotive.dto.RemotiveJobRaw;
import com.codes.wfhub.infrastructure.client.remotive.dto.RemotiveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RemotiveClient {

    private final WebClient webClient;

    public Mono<List<RemotiveJobRaw>> fetchJobs(){
        return webClient.get()
                .uri("http://remotive.com/api/remote-jobs")
                .retrieve()
                .bodyToMono(RemotiveResponse.class)
                .map(RemotiveResponse::jobs)
                .onErrorResume(e -> {
                    return Mono.just(List.of());
                });
    }
}
