package org.example.lock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.example.lock.EventType.*;

@Component
@RequiredArgsConstructor
public class MessageObserver implements LockObserver {
    private final ObjectMapper objectMapper;
    private final NotificationUtil n;

    @Override
    public void onLockEvent(LockEvent event) throws JsonProcessingException {
        Long senderId = event.getSenderId();
        Long receiverId = event.getReceiverId();
        LockModel lock = event.getLock();

        switch (event.getEventType()) {
            case CREATE_LOCK -> lockCreated(senderId, receiverId, lock);
            case DELETE_LOCK -> lockDeleted(senderId, receiverId, lock);
            case ADD_USER -> addUserToLock(senderId, receiverId, lock);
            case REMOVE_USER -> removeUserFromLock(senderId, receiverId, lock);
            case ADD_MANAGER -> addManagerToLock(senderId, receiverId, lock);
            case REMOVE_MANAGER -> removeManagerFromLock(senderId, receiverId, lock);
            case CHANGE_PICTURE -> changePicture(senderId, receiverId, lock);
            case CHANGE_NAME -> changeName(senderId, receiverId, lock);
            case CHANGE_POWER -> changePower(senderId, receiverId, lock);
            case CHANGE_LOCATION -> changeLocation(senderId, receiverId, lock);
            case CHANGE_REPORT_BATTERY -> changeReportBattery(senderId, receiverId, lock);
            case CHANGE_REPORT_LOCATION -> changeReportLocation(senderId, receiverId, lock);
            default -> throw new IllegalArgumentException("Invalid event type: " + event.getEventType());
        }
    }


    private void lockCreated(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.notifyOwner(lockInfo, CREATE_LOCK.getValue(), "You just created a lock: " + lockInfo.getName());
        n.statusChanged(lockInfo, CREATE_LOCK.getValue(), detail);
    }

    private void lockDeleted(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.notifyAll(lockInfo, DELETE_LOCK.getValue(), "Lock " + lockInfo.getName() + " has been deleted");
        n.statusChanged(lockInfo, DELETE_LOCK.getValue(), detail);

    }

    private void addUserToLock(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.notifyAllManagersAndOwner(lockInfo, ADD_USER.getValue(), "A new user has been added to lock " + lockInfo.getName());
        n.statusChanged(lockInfo, ADD_USER.getValue(), detail);
        n.sendStatusChangeToUser(lockInfo, receiverId, ADD_USER.getValue(), detail);

    }

    private void removeUserFromLock(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.notifyUser(lockInfo, receiverId, REMOVE_USER.getValue(), "You have been removed from lock " + lockInfo.getName());
        n.notifyAllManagersAndOwner(lockInfo, REMOVE_USER.getValue(), "A user has been removed from lock " + lockInfo.getName());
        n.statusChanged(lockInfo, REMOVE_USER.getValue(), detail);

    }

    private void addManagerToLock(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.notifyAllManagersExceptOne(lockInfo, receiverId, "Manager added", "A new manager has been added to lock " + lockInfo.getName());
        n.notifyOwner(lockInfo, ADD_MANAGER.getValue(), "A new manager has been added to lock " + lockInfo.getName());
        n.statusChanged(lockInfo, ADD_MANAGER.getValue(), detail);
   }

    private void removeManagerFromLock(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.notifyUser(lockInfo, receiverId, REMOVE_MANAGER.getValue(), "You have been removed from manager list of lock " + lockInfo.getName());
        n.notifyAllManagersAndOwner(lockInfo, REMOVE_MANAGER.getValue(), "A manager has been removed from lock " + lockInfo.getName());
        n.statusChanged(lockInfo, REMOVE_MANAGER.getValue(), detail);
    }

    private void changePicture(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.notifyAllManagersAndOwner(lockInfo, CHANGE_PICTURE.getValue(), "The picture of lock " + lockInfo.getName() + " has been changed");
        n.statusChanged(lockInfo, CHANGE_PICTURE.getValue(), detail);

    }

    private void changeName(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.notifyAllManagersAndOwner(lockInfo, CHANGE_NAME.getValue(), "The name of lock " + lockInfo.getName() + " has been changed");
        n.statusChanged(lockInfo, CHANGE_NAME.getValue(), detail);


    }

    private void changePower(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.statusChanged(lockInfo, CHANGE_POWER.getValue(), detail);

    }

    private void changeLocation(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.statusChanged(lockInfo, CHANGE_LOCATION.getValue(), detail);

    }

    private void changeReportBattery(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.statusChanged(lockInfo, CHANGE_REPORT_BATTERY.getValue(), detail);
        n.notifyAllManagersAndOwner(lockInfo, CHANGE_REPORT_BATTERY.getValue(), "The report battery of lock " + lockInfo.getName() + " has been changed");

    }

    private void changeReportLocation(Long senderId, Long receiverId, LockModel lockInfo) throws JsonProcessingException {
        String detail = objectMapper.writeValueAsString(lockInfo);
        n.statusChanged(lockInfo, CHANGE_REPORT_LOCATION.getValue(), detail);
        n.notifyAllManagersAndOwner(lockInfo, CHANGE_REPORT_LOCATION.getValue(), "The report location of lock " + lockInfo.getName() + " has been changed");

    }
}
