package org.example.lock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "logs-service", url = "${application.config.logs-url}")
public interface LogClient {
    @PostMapping("/logs")
    ResponseEntity<LogModel> log(@RequestBody LogModel logModel);
}
