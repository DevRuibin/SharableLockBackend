package org.andy.chatfybackend.auth.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    private ResponseEntity<Boolean> authorize(AuthorizationModel model) {
        return ResponseEntity.ok(authorizationService.authorize(model));
    }

}
