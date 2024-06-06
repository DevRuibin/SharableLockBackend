package org.example.lock;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locks")
@RequiredArgsConstructor
public class LockController {
    private final LockService lockService;

    @PostMapping
    public ResponseEntity<LockModel> createLock(LockModel lockModel) throws JsonProcessingException {
        return ResponseEntity.ok(lockService.createLock(lockModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LockModel> getLock(@PathVariable Long id) {
        return ResponseEntity.ok(lockService.getLockById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<LockModel>> getLocksByUserID(@PathVariable Long id) {
        return ResponseEntity.ok(lockService.getLocksByUserOrOwnerOrManager(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LockModel> deleteLock(@PathVariable Long id) throws JsonProcessingException {
        LockModel lockModel = lockService.deleteLock(id);
        return ResponseEntity.ok(lockModel);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LockModel> updateLock(@PathVariable Long id, PatchRequest body) throws JsonProcessingException {
        System.out.println("Updating lock with id: " + id + " and body: " + body);
        LockModel lockModel = lockService.updateLock(id, body);
        return ResponseEntity.ok(lockModel);
    }







}
