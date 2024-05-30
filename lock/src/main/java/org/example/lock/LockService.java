package org.example.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LockService {
    private final LockRepository repository;
    public void createLock(Set<LockModel> lock) {
        if(lock.size() != 1){
            throw new IllegalArgumentException("Lock size must be 1");
        }
        repository.saveAll(lock);
    }

    public LockModel ModifyReportGPSInterval(Long lockId, int interval) {
        LockModel lock = repository.findById(lockId).orElseThrow();
        lock.setReport_location(interval);
        return repository.save(lock);
    }

    public LockModel ModifyReportBatteryInterval(Long lockId, int interval) {
        LockModel lock = repository.findById(lockId).orElseThrow();
        lock.setReport_battery(interval);
        return repository.save(lock);
    }

    public LockModel UpdateLockStatus(Long lockId, boolean status) {
        LockModel lock = repository.findById(lockId).orElseThrow();
        lock.setLocked(status);
        return repository.save(lock);
    }

    public LockModel UpdateLockBattery(Long lockId, float battery) {
        LockModel lock = repository.findById(lockId).orElseThrow();
        lock.setPower(battery);
        return repository.save(lock);
    }

    public LockModel UpdateLockLocation(Long lockId, float latitude, float longitude) {
        LockModel lock = repository.findById(lockId).orElseThrow();
        lock.setLatitude(latitude);
        lock.setLongitude(longitude);
        return repository.save(lock);
    }

    public LockModel UpdateLockOnlineStatus(Long lockId, boolean status) {
        LockModel lock = repository.findById(lockId).orElseThrow();
        lock.setOnline(status);
        return repository.save(lock);
    }

    public LockModel updateLock(LockModel lock) {
        return repository.save(lock);
    }

    public void deleteLock(Long lockId, Long ownerId) {
        LockModel lock = repository.findById(lockId).orElseThrow();
        if(!Objects.equals(lock.getOwnerId(), ownerId)){
            throw new IllegalArgumentException("Owner id does not match");
        }
        repository.deleteById(lockId);
    }
}
