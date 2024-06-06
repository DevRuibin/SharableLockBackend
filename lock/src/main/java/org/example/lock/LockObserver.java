package org.example.lock;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface LockObserver {
    void onLockEvent(LockEvent event) throws JsonProcessingException;
}
