package org.example.lock;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LockEventManager {
    private final List<LockObserver> observers = new ArrayList<>();

    @Autowired
    public LockEventManager(MessageObserver messageObserver) {
        observers.add(messageObserver);
    }

    public void addObserver(LockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LockObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(LockEvent event) throws JsonProcessingException {
        for (LockObserver observer : observers) {
            System.out.println("Notifying observer: " + observer.getClass().getSimpleName());
            observer.onLockEvent(event);
        }
    }
}
