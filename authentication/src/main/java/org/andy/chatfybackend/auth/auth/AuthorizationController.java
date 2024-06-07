package org.andy.chatfybackend.auth.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final MyJwtService myJwtService;

    @PostMapping("/authorize")
    public ResponseEntity<Boolean> authorize(@RequestBody AuthorizationModel model) {
        return ResponseEntity.ok(authorizationService.authorize(model));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User user) {
        String token = myJwtService.generateToken(user);
        return ResponseEntity.ok(JwtResponse.builder().token(token).build());
    }

    @PostMapping("/validate")
    public ResponseEntity<User> validate(@RequestBody JwtResponse token) {
        return ResponseEntity.ok(myJwtService.parseToken(token.token()));
    }
}
