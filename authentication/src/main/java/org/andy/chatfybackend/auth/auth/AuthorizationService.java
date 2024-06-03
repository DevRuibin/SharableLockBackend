package org.authentication.auth.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public Boolean authorize(AuthorizationModel model) {
        return true;
    }
}
