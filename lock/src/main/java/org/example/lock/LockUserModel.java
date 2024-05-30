package org.example.lock;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    @ManyToMany
    private Set<LockModel> lock;
    private Role accessLevel;
    private Long expiration;
    private boolean revoked;
}
