package com.alibou.gateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogEventListener {

    private final LogClient logClient;

    @EventListener
    @Async
    public void handleLogEvent(LogEvent event) {
        logClient.log(event.getLogModel()).subscribe();
    }
}
