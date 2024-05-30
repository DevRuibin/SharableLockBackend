package org.example.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/locks")
@RequiredArgsConstructor
public class LockController {
    private final LockService service;

    @PatchMapping("/{lockId}/report-gps-interval")
    public ResponseEntity<LockModel> ModifyReportGPSInterval(
        @PathVariable("lockId") Long lockId,
        @RequestBody int interval
    ) {
        return ResponseEntity.ok(service.ModifyReportGPSInterval(lockId, interval));
    }

    @PatchMapping("/{lockId}/report-battery-interval")
    public ResponseEntity<LockModel> ModifyReportBatteryInterval(
        @PathVariable("lockId") Long lockId,
        @RequestBody int interval
    ) {
        return ResponseEntity.ok(service.ModifyReportBatteryInterval(lockId, interval));
    }

    @PatchMapping("/{lockId}/status")
    public ResponseEntity<LockModel> UpdateLockStatus(
        @PathVariable("lockId") Long lockId,
        @RequestBody boolean status
    ) {
        return ResponseEntity.ok(service.UpdateLockStatus(lockId, status));
    }

    @PatchMapping("/{lockId}/battery")
    public ResponseEntity<LockModel> UpdateLockBattery(
        @PathVariable("lockId") Long lockId,
        @RequestBody float battery
    ) {
        return ResponseEntity.ok(service.UpdateLockBattery(lockId, battery));
    }

    @PatchMapping("/{lockId}/location")
    public ResponseEntity<LockModel> UpdateLockLocation(
        @PathVariable("lockId") Long lockId,
        @RequestBody float latitude,
        @RequestBody float longitude
    ) {
        return ResponseEntity.ok(service.UpdateLockLocation(lockId, latitude, longitude));
    }

    @PatchMapping("/{lockId}/online-status")
    public ResponseEntity<LockModel> UpdateLockOnlineStatus(
        @PathVariable("lockId") Long lockId,
        @RequestBody boolean status
    ) {
        return ResponseEntity.ok(service.UpdateLockOnlineStatus(lockId, status));
    }

    @PutMapping
    public ResponseEntity<LockModel> updateLock(@RequestBody LockModel lock) {
        return ResponseEntity.ok(service.updateLock(lock));
    }


    @DeleteMapping("/{lockId}")
    public ResponseEntity<Void> deleteLock(@PathVariable("lockId") Long lockId,
                                           @RequestBody Long ownerId) {
        try {
            service.deleteLock(lockId, ownerId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
