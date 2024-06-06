package org.example.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LockEvent {
    private final EventType eventType;
    private final Long senderId;
    private final Long receiverId;
    private final LockModel lock;

}
