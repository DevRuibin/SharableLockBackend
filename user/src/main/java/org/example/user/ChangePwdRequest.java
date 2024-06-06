package org.example.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangePwdRequest {
    String oldPassword;
    String newPassword;
}
