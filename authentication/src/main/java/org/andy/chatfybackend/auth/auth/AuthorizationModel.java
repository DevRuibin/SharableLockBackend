package org.andy.chatfybackend.auth.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizationModel {
    String url;
    String method;
    String Token;
}
