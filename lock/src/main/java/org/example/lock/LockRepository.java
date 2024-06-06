package org.example.lock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LockRepository extends JpaRepository<LockModel, Long> {
}
