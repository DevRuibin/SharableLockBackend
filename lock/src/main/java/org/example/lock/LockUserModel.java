package org.example.lock;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockUserModel {
    @Id
    private Long id;
    private Long userId;
    private Long lockId;
    private Role accessLevel;
    private Long expiration;
    private boolean revoked;
}
