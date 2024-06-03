package com.alibou.gateway;

import com.alibou.gateway.client.LogEvent;
import com.alibou.gateway.client.LogModel;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements GlobalFilter, Ordered {

    private final WebClient.Builder webClientBuilder;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Publish event for incoming request
        eventPublisher.publishEvent(new LogEvent(incomingRequestLogModel(request)));

        String url = request.getURI().toString();
        String method = request.getMethod().name();
        String token = request.getHeaders().getFirst("Authorization");

        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8800/api/v1/auth/authorize")
                .bodyValue(new AuthorizationModel(url, method, token))
                .retrieve()
                .bodyToMono(Boolean.class)
                .flatMap(isAuthorized -> {
                    if (Boolean.TRUE.equals(isAuthorized)) {
                        // Publish event for authorized request
                        eventPublisher.publishEvent(new LogEvent(incomingApprovedLogModel(request)));
                        return chain.filter(exchange).doOnSuccess(aVoid -> {
                            // Publish event for successful outgoing response
                            eventPublisher.publishEvent(new LogEvent(outgoingSuccessLogModel(exchange)));
                        });
                    } else {
                        // Publish event for denied request
                        eventPublisher.publishEvent(new LogEvent(incomingDeniedLogModel(request)));
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete().doOnTerminate(() -> {
                            // Publish event for failed outgoing response
                            eventPublisher.publishEvent(new LogEvent(outgoingFailureLogModel(exchange)));
                        });
                    }
                })
                .doOnError(throwable -> {
                    // Publish event for error in outgoing response
                    eventPublisher.publishEvent(new LogEvent(outgoingFailureLogModel(exchange)));
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * Get the incoming information including url, method, token, ip, etc.
     */
    private String incomingRequest(ServerHttpRequest request) {
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        return String.format("URL: %s, Method: %s, Token: %s, IP: %s",
                request.getURI(),
                request.getMethod(),
                request.getHeaders().getFirst("Authorization"),
                remoteAddress != null ? remoteAddress.getAddress().getHostAddress() : "Unknown");
    }

    /**
     * Get the outgoing information including response status, etc.
     */
    private String outgoingResponse(ServerWebExchange exchange) {
        return String.format("Response Status: %s", exchange.getResponse().getStatusCode());
    }

    private LogModel incomingRequestLogModel(ServerHttpRequest request) {
        String message = "Incoming request: " + incomingRequest(request);
        return LogModel.builder()
                .message(message)
                .level("INFO")
                .service("gateway-service")
                .build();
    }

    private LogModel incomingApprovedLogModel(ServerHttpRequest request) {
        String message = "Request authorized: " + incomingRequest(request);
        return LogModel.builder()
                .message(message)
                .level("INFO")
                .service("gateway-service")
                .build();
    }

    private LogModel incomingDeniedLogModel(ServerHttpRequest request) {
        String message = "Request denied: " + incomingRequest(request);
        return LogModel.builder()
                .message(message)
                .level("WARNING")
                .service("gateway-service")
                .build();
    }

    private LogModel outgoingSuccessLogModel(ServerWebExchange exchange) {
        String message = "Response success: " + outgoingResponse(exchange);
        if(!Objects.requireNonNull(exchange.getResponse().getStatusCode()).is2xxSuccessful()) {
            return LogModel.builder()
                    .message(message)
                    .level("ERROR")
                    .service("gateway-service")
                    .build();
        }
        return LogModel.builder()
                .message(message)
                .level("INFO")
                .service("gateway-service")
                .build();
    }

    private LogModel outgoingFailureLogModel(ServerWebExchange exchange) {
        String message = "Response failure: " + outgoingResponse(exchange);
        return LogModel.builder()
                .message(message)
                .level("ERROR")
                .service("gateway-service")
                .build();
    }
}
