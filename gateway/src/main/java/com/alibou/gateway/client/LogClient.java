package com.alibou.gateway.client;

import reactor.core.publisher.Mono;

public interface LogClient {
    Mono<Void> log(LogModel logModel);
}
