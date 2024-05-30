package org.example.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequest {
    private String email;
    private String password;
}
