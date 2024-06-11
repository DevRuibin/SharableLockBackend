package org.example.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserModel {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String phone;
    private String avatar;
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private boolean admin;

}


