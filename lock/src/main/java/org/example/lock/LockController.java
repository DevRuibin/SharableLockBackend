package org.example.lock;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/locks")
@RequiredArgsConstructor
public class LockController {
    private final LockService service;

    @Operation(summary="Modify the gps report interval")
    @PatchMapping("/{lockId}/report-gps-interval")
    public ResponseEntity<LockModel> ModifyReportGPSInterval(
        @PathVariable("lockId") Long lockId,
        @RequestBody int interval
    ) {
        return ResponseEntity.ok(service.ModifyReportGPSInterval(lockId, interval));
    }

    @Operation(summary = "Modify the battery report interval")
    @PatchMapping("/{lockId}/report-battery-interval")
    public ResponseEntity<LockModel> ModifyReportBatteryInterval(
        @PathVariable("lockId") Long lockId,
        @RequestBody int interval
    ) {
        return ResponseEntity.ok(service.ModifyReportBatteryInterval(lockId, interval));
    }

    @Operation(summary = "change lock status(lock/unlock)")
    @PatchMapping("/{lockId}/status")
    public ResponseEntity<LockModel> UpdateLockStatus(
        @PathVariable("lockId") Long lockId,
        @RequestBody boolean status
    ) {
        return ResponseEntity.ok(service.UpdateLockStatus(lockId, status));
    }

    @Operation(summary = "Change the battery status")
    @Deprecated
    @PatchMapping("/{lockId}/battery")
    public ResponseEntity<LockModel> UpdateLockBattery(
        @PathVariable("lockId") Long lockId,
        @RequestBody float battery
    ) {
        return ResponseEntity.ok(service.UpdateLockBattery(lockId, battery));
    }

    @Operation(summary = "Change the location of the lock")
    @PatchMapping("/{lockId}/location")
    public ResponseEntity<LockModel> UpdateLockLocation(
        @PathVariable("lockId") Long lockId,
        @RequestBody float latitude,
        @RequestBody float longitude
    ) {
        return ResponseEntity.ok(service.UpdateLockLocation(lockId, latitude, longitude));
    }

    @Operation(summary = "Shift between online/offline")
    @PatchMapping("/{lockId}/online-status")
    public ResponseEntity<LockModel> UpdateLockOnlineStatus(
        @PathVariable("lockId") Long lockId,
        @RequestBody boolean status
    ) {
        return ResponseEntity.ok(service.UpdateLockOnlineStatus(lockId, status));
    }

    @Operation(summary = "Modify all field of a lock")
    @PutMapping
    public ResponseEntity<LockModel> updateLock(@RequestBody LockModel lock) {
        return ResponseEntity.ok(service.updateLock(lock));
    }


    @Operation(summary = "Delete a lock")
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
