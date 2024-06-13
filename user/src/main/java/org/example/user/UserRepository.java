package org.example.user;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmailAndPassword(String email, String password);

    Optional<UserModel> findByEmail(String email);
}
