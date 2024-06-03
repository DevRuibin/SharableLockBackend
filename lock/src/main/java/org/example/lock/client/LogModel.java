package org.example.lock.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Builder
@Setter
@Getter
public class LogModel implements Serializable {
    private String service;
    private String level;
    private String message;
}
