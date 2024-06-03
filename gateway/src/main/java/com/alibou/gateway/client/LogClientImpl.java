package com.alibou.gateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LogClientImpl implements LogClient {

    private  WebClient.Builder webClientBuilder;
    @Value("${application.config.logs-url}" + "/logs")
    private String logsUrl;

    public LogClientImpl() {
        System.out.println("LogClientImpl created");
        System.out.println("logsUrl: " + logsUrl);
    }

    @Autowired
    public void setWebClientBuilder(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> log(LogModel logModel) {
        System.out.println("LogClientImpl.log is called");
        System.out.println("Log url: " + logsUrl);
        return webClientBuilder.build()
            .post()
            .uri(logsUrl)
            .bodyValue(logModel)
            .retrieve()
            .bodyToMono(Void.class)
            .doOnSuccess(aVoid -> System.out.println("Log sent successfully"))
            .doOnError(throwable -> System.out.println("Error occurred while sending log: " + throwable.getMessage()));
    }
}

