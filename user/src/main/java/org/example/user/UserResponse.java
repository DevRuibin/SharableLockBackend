package org.example.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private boolean admin;
}
