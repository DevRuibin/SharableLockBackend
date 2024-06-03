package org.example.lock;

import lombok.RequiredArgsConstructor;
import org.example.lock.client.UserClient;
import org.example.lock.client.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LockUserService {
    private final LockUserRepository repository;
    private final LockService lockService;
    private final UserClient userClient;
    public LockUserModel getLockByUserIdAndLockId(Long userId, Long lockId) {
        return repository.findByUserIdAndLockId(userId, lockId).orElseThrow();
    }

    public List<LockUserModel> getLockByUserId(Long userId) {
        UserModel user = userClient.getUserById(userId);
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        System.out.println(repository.findByUserId(userId));

        return repository.findByUserId(userId).orElseThrow();
    }

    public LockUserModel createLock(LockUserModel lockUserModel) {
        UserModel user = userClient.getUserById(lockUserModel.getUserId());
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        lockService.createLock(lockUserModel.getLock());
        return repository.save(lockUserModel);
    }

    public Optional<List<LockUserModel>> getUserByLockIdAndNotRevoked(Long lockId) {
        return repository.findByLockIdAndRevokedFalse(lockId);
    }
}
