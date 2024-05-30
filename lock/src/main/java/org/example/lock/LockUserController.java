package org.example.lock;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/locks")
@RequiredArgsConstructor
public class LockUserController {
    private final LockUserService service;

    @Operation(summary = "Get the relation data of user and lock")
    @GetMapping("/{userId}/{lockId}")
    public ResponseEntity<LockUserModel> getLockByUserIdAndLockId(
        @PathVariable("userId") Long userId,
        @PathVariable("lockId") Long lockId) {
        try {
            return ResponseEntity.ok(service.getLockByUserIdAndLockId(userId, lockId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all locks by user id")
    @GetMapping("/{userId}")
    public ResponseEntity<LockUserModel> getLockByUserId(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(service.getLockByUserId(userId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a Lock")
    @PostMapping
    public ResponseEntity<LockUserModel> createLock(@RequestBody LockUserModel lockUserModel) {
        return ResponseEntity.ok(service.createLock(lockUserModel));
    }

    @Operation(summary = "Get the users of a lock")
    @GetMapping("/{lockId}/users")
    public ResponseEntity<List<LockUserModel>> getUserByLockIdAndNotRevoked(@PathVariable("lockId") Long lockId) {
        return ResponseEntity.ok(service.getUserByLockIdAndNotRevoked(lockId).orElse(Collections.emptyList()));
    }
}
