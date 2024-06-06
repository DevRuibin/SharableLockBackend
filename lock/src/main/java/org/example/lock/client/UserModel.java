package org.example.lock.client;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@Data
public class UserModel implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String gender;
    private boolean admin;

}