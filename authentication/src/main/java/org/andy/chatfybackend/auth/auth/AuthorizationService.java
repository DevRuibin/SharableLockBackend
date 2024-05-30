package org.andy.chatfybackend.auth.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public Boolean authorize(AuthorizationModel model) {
        return true;
    }
}
