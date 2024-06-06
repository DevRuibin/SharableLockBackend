package org.example.lock;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locks")
@RequiredArgsConstructor
public class LockController {
    private final LockService lockService;

    @Operation(summary = "Create a new lock")
    @PostMapping
    public ResponseEntity<LockModel> createLock(LockModel lockModel) throws JsonProcessingException {
        return ResponseEntity.ok(lockService.createLock(lockModel));
    }

    @Operation(summary = "Get a lock by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LockModel> getLock(@PathVariable Long id) {
        return ResponseEntity.ok(lockService.getLockById(id));
    }

    @Operation(summary = "Get all locks by user ID")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<LockModel>> getLocksByUserID(@PathVariable Long id) {
        return ResponseEntity.ok(lockService.getLocksByUserOrOwnerOrManager(id));
    }
    @Operation(summary = "Delete a lock by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<LockModel> deleteLock(@PathVariable Long id) throws JsonProcessingException {
        LockModel lockModel = lockService.deleteLock(id);
        return ResponseEntity.ok(lockModel);
    }

    @Operation(summary = "Update a lock by ID", description = "Update a lock's specific field by ID." +
            "it can be used to update the name, description, owner, users, managers, and status of the lock.")
    @PatchMapping("/{id}")
    public ResponseEntity<LockModel> updateLock(@PathVariable Long id, PatchRequest body) throws JsonProcessingException {
        System.out.println("Updating lock with id: " + id + " and body: " + body);
        LockModel lockModel = lockService.updateLock(id, body);
        return ResponseEntity.ok(lockModel);
    }







}
