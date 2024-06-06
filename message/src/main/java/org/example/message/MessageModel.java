package org.example.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageModel implements Serializable  {
    @Id
    @GeneratedValue
    private Long id;
    private MessageType type;
    private Long senderId;
    private Long toUserId;
    private String text;
    private String detail;
    private Long timestamp;
    private boolean read;
}

