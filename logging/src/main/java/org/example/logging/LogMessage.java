package org.example.logging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(indexName = "logs")
public class LogMessage {

    @Id
    private String id;
    private String service;
    private String level;
    private String message;
    private LocalDateTime timestamp;

    public String toString(){
        return service + "[" + level + "]: " + message + " at " + timestamp;
    }

}
