package org.example.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
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


