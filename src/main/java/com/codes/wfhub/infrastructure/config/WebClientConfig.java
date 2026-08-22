package com.codes.wfhub.infrastructure.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    private static final int CONNECT_TIMEOUT_MS = 5000;
    private static final int READ_TIMEOUT_SEC = 10;
    private static final int WRITE_TIMEOUT_SEC = 10;

    private HttpClient baseHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MS)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT_SEC, TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(WRITE_TIMEOUT_SEC, TimeUnit.SECONDS)));
    }

    @Bean
    public WebClient remotiveWebClient() {
        return WebClient.builder()
                .baseUrl("https://remotive.com/api")
                .clientConnector(new ReactorClientHttpConnector(baseHttpClient()))
                .build();
    }

    @Bean
    public WebClient remoteOkWebClient() {
        return WebClient.builder()
                .baseUrl("https://remoteok.com/api")
                .clientConnector(new ReactorClientHttpConnector(baseHttpClient()))
                .defaultHeader("User-Agent", "WFHub/1.0")
                .build();
    }

}
