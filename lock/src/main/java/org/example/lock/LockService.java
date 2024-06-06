package org.example.lock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.lock.client.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.lock.EventType.*;

@Service
@RequiredArgsConstructor
public class LockService {
    private final LockRepository lockRepository;
    private final UserClient userClient;
    private final LockEventManager lockEventManager;

    public LockModel createLock(LockModel lockModel) throws JsonProcessingException {
        Long UserID = lockModel.getOwnerId();
        lockEventManager.notifyObservers(new LockEvent(EventType.CREATE_LOCK, 0L, UserID, lockModel));
        return lockRepository.save(lockModel);
    }


    public LockModel addUserToLock(Long lockId, Long userId) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(lockId).orElse(null);
        assert lock != null;
        if(lock.getUsers().contains(userId)) {
            return null;
        }
        UserModel user = userClient.getUserById(userId);
        assert user != null;
        lock.getUsers().add(userId);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.ADD_USER, 0L, userId, lock));
        return lock;
    }

    public LockModel removeUserFromLock(Long lockId, Long userId) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(lockId).orElse(null);
        assert lock != null;
        if(!lock.getUsers().contains(userId)) {
            return null;
        }
        lock.getUsers().remove(userId);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.REMOVE_USER, 0L, userId, lock));
        return lock;
    }

    public LockModel addManagerToLock(Long lockId, Long managerId) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(lockId).orElse(null);
        assert lock != null;
        if(lock.getManagers().contains(managerId)) {
            return null;
        }
        UserModel manager = userClient.getUserById(managerId);
        assert manager != null;
        lock.getManagers().add(managerId);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.ADD_MANAGER, 0L, managerId, lock));
        return lock;
    }

    public LockModel removeManagerFromLock(Long lockId, Long managerId) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(lockId).orElse(null);
        assert lock != null;
        if(!lock.getManagers().contains(managerId)) {
            return null;
        }
        lock.getManagers().remove(managerId);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.REMOVE_MANAGER, 0L, managerId, lock));
        return lock;
    }

    public List<LockModel> getLocksByUserOrOwnerOrManager(Long userId) {
        return lockRepository.findAll()
                .stream()
                .filter(lock -> lock.getUsers().contains(userId) || lock.getOwnerId().equals(userId) || lock.getManagers().contains(userId))
                .collect(Collectors.toList());
    }

    public LockModel getLockById(Long id) {
        return lockRepository.findById(id).orElse(null);
    }

    public LockModel deleteLock(Long id) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lockEventManager.notifyObservers(new LockEvent(EventType.DELETE_LOCK, 0L, 0L, lock));
        lockRepository.delete(lock);
        return lock;
    }

    public LockModel changeName(Long id, String name) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lock.setName(name);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(CHANGE_NAME, 0L, 0L, lock));
        return lock;
    }

    public LockModel changePicture(Long id, String picture) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lock.setPicture(picture);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(CHANGE_PICTURE, 0L, 0L, lock));
        return lock;
    }

    public LockModel changePower(Long id, float power) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lock.setPower(power);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(CHANGE_POWER, 0L, 0L, lock));
        return lock;
    }

    public LockModel changeOnline(Long id, boolean online) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lock.setOnline(online);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.CHANGE_ONLINE, 0L, 0L, lock));
        return lock;
    }

    public LockModel changeLocked(Long id, boolean locked) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lock.setLocked(locked);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.CHANGE_LOCKED, 0L, 0L, lock));
        return lock;
    }

    public LockModel changeLocation(Long id, float latitude, float longitude) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lock.setLatitude(latitude);
        lock.setLongitude(longitude);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.CHANGE_LOCATION, 0L, 0L, lock));
        return lock;
    }

    public LockModel changeReportBattery(Long id, int reportBattery) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lock.setReportBattery(reportBattery);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.CHANGE_REPORT_BATTERY, 0L, 0L, lock));
        return lock;
    }

    public LockModel changeReportLocation(Long id, int reportLocation) throws JsonProcessingException {
        LockModel lock = lockRepository.findById(id).orElse(null);
        assert lock != null;
        lock.setReportLocation(reportLocation);
        lockRepository.save(lock);
        lockEventManager.notifyObservers(new LockEvent(EventType.CHANGE_REPORT_LOCATION, 0L, 0L, lock));
        return lock;
    }

    public LockModel updateLock(Long id, PatchRequest body) throws JsonProcessingException {
        LockModel lockModel;
        switch (body.getEventType()) {
            case REMOVE_MANAGER -> lockModel = removeManagerFromLock(id, body.getManagerId());
            case REMOVE_USER -> lockModel = removeUserFromLock(id, body.getUserId());
            case ADD_MANAGER -> lockModel = addManagerToLock(id, body.getManagerId());
            case ADD_USER -> lockModel = addUserToLock(id, body.getUserId());
            case CHANGE_NAME -> lockModel= changeName(id, body.getName());
            case CHANGE_PICTURE -> lockModel = changePicture(id, body.getPicture());
            case CHANGE_POWER -> lockModel = changePower(id, body.getPower());
            case CHANGE_ONLINE -> lockModel = changeOnline(id, body.getOnline());
            case CHANGE_LOCKED -> lockModel = changeLocked(id, body.getLocked());
            case CHANGE_LOCATION -> lockModel = changeLocation(id, body.getLatitude(), body.getLongitude());
            case CHANGE_REPORT_BATTERY -> lockModel = changeReportBattery(id, body.getReportBattery());
            case CHANGE_REPORT_LOCATION -> lockModel = changeReportLocation(id, body.getReportLocation());
            default -> throw new IllegalArgumentException("Invalid event type: " + body);
        }
        return lockModel;
    }
}
