package org.example.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LockUserService {
    private final LockUserRepository repository;
    private final LockService lockService;
    public LockUserModel getLockByUserIdAndLockId(Long userId, Long lockId) {
        return repository.findByUserIdAndLockId(userId, lockId).orElseThrow();
    }

    public LockUserModel getLockByUserId(Long userId) {
        System.out.printf("userId: %d\n", userId);
        System.out.println(repository.findByUserId(userId));
        return repository.findByUserId(userId).orElseThrow();
    }

    public LockUserModel createLock(LockUserModel lockUserModel) {
        lockService.createLock(lockUserModel.getLock());
        return repository.save(lockUserModel);
    }

    public Optional<List<LockUserModel>> getUserByLockIdAndNotRevoked(Long lockId) {
        return repository.findByLockIdAndRevokedFalse(lockId);
    }
}
