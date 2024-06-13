package org.example.lock.client;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
public class MessageModel implements Serializable {
    private Long id;
    private MessageType type;
    private Long senderId;
    private Long toUserId;
    private String text;
    private String detail;
    private Long timestamp;
    private boolean read;
}



