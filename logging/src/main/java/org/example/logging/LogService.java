package org.example.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogMessageRepository logMessageRepository;


    public LogMessage processLogMessage(LogMessage logMessage) {
        logMessage.setTimestamp(LocalDateTime.now());
        return logMessageRepository.save(logMessage);
    }
}

