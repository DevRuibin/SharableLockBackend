package org.example.lock;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LockUserRepository extends JpaRepository<LockUserModel, Long> {
    @Query("SELECT l FROM LockUserModel l WHERE l.userId = :userId")
    Optional<LockUserModel> findByUserId(Long userId);

    @Query("SELECT l FROM LockUserModel l JOIN l.lock lock WHERE l.userId = :userId AND lock.id = :lockId")
    Optional<LockUserModel> findByUserIdAndLockId(@Param("userId") Long userId, @Param("lockId") Long lockId);

    @Query("SELECT l FROM LockUserModel l JOIN l.lock lock WHERE lock.id = :lockId AND l.revoked = false")
    Optional<List<LockUserModel>> findByLockIdAndRevokedFalse(@Param("lockId") Long lockId);
}