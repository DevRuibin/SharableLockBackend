package org.example.logging;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @Operation(summary = "Log a message")
    @PostMapping
    public ResponseEntity<LogMessage> log(@RequestBody LogMessage logMessage) {
        return ResponseEntity.ok(logService.processLogMessage(logMessage));
    }
}

