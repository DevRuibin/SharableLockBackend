package org.example.message;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageModel {
    @Id
    @GeneratedValue
    private Long id;
    private Long senderId;
    private Long toUserId;
    private String text;
    private Long timestamp;
    private boolean read;
}
